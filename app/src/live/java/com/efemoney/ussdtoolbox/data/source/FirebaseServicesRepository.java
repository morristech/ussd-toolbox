package com.efemoney.ussdtoolbox.data.source;

import com.efemoney.ussdtoolbox.BuildConfig;
import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.BooleanField;
import com.efemoney.ussdtoolbox.data.model.Field;
import com.efemoney.ussdtoolbox.data.model.InputField;
import com.efemoney.ussdtoolbox.data.model.Service;
import com.efemoney.ussdtoolbox.data.model.Template;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Single source of truth for all data
 */
@Singleton
public class FirebaseServicesRepository implements ServicesRepository {

    private static final String SERVICES_NODE = "services";

    private final ServiceMetaData serviceMetaData;
    private final FirebaseServiceParser parser;

    private Comparator<Service> comparator = (o1, o2) -> {

        // Services are compared by favorite
        // then by number of times clicked
        // then by chronological order

        // Remember returning -ve means the object is *lower*
        // in the list and therefore visible *first*

        // 1 is lhs and 2 is rhs

        String o1k = o1.getKey();
        String o2k = o2.getKey();

        boolean is1Fave = serviceMetaData.getFavorite(o1k);
        boolean is2Fave = serviceMetaData.getFavorite(o2k);
        int num1Clicks = serviceMetaData.getClicks(o1k);
        int num2Clicks = serviceMetaData.getClicks(o2k);

        if (is1Fave && !is2Fave) return -1;
        if (!is1Fave && is2Fave) return 1;

        if (num1Clicks > num2Clicks) return -1;
        if (num2Clicks > num1Clicks) return 1;

        // Not sorting by chronological order anymore.
        return 0;
    };

    private final FirebaseDatabase db;

    @Inject public FirebaseServicesRepository(ServiceMetaData serviceMetaData) {
        this.serviceMetaData = serviceMetaData;

        this.db = FirebaseDatabase.getInstance();
        this.db.setPersistenceEnabled(true);
        this.db.setLogLevel(BuildConfig.DEBUG ? Logger.Level.DEBUG : Logger.Level.NONE);

        this.parser = new FirebaseServiceParser();
    }

    @Override
    public Observable<List<Service>> getServices() {

        Observable<List<Service>> servicesObsvb = Observable.create(e -> {

            DatabaseReference ref = db.getReference(SERVICES_NODE);
            ref.keepSynced(true); // Keep the services data even when app stopped/restarted

            final ValueEventListener listener = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Parsing manually as opposed to using dataSnapshot.getValue(List<Service>.class)
                    // Allows for custom deserialization of Fields based on Field.types

                    List<Service> services = parser.parseServices(dataSnapshot);

                    if (!e.isDisposed()) {
                        e.onNext(services);
                        e.onComplete();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    e.onError(databaseError.toException());
                }
            };

            ref.addValueEventListener(listener);

            e.setCancellable(() -> ref.removeEventListener(listener));

        });

        return servicesObsvb.flatMap(
                services -> Observable.fromIterable(services) // Break down data
                        .sorted(comparator) // Sort it
                        .map(service -> {

                            // Update fave status
                            boolean fave = serviceMetaData.getFavorite(service.getKey());
                            service.setFavorite(fave);

                            return service;
                        })
                        .toList() // recombine as list
                        .toObservable()  // to observable
        );
    }

    @Override
    public Observable<Service> getService(String key) {

        return getServices()
                .flatMap(Observable::fromIterable)
                .filter(service -> service.getKey().equals(key));
    }

    @Override
    public void clickService(String key) {

        serviceMetaData.incrementClick(key);
    }

    @Override
    public void favoriteService(String key) {

        serviceMetaData.toggleFavorite(key);
    }

    @Override
    public Observable<List<Action>> getActionsForService(String key) {

        return getService(key).map(Service::getActions);
    }


    private static final class FirebaseServiceParser implements ServiceParser<DataSnapshot> {

        @Override
        public List<Service> parseServices(DataSnapshot object) {

            int count = (int) object.getChildrenCount();

            List<Service> list = new ArrayList<>(count);

            for (DataSnapshot snapshot : object.getChildren()) {

                String key = snapshot.child(KEY).getValue(String.class);
                String name = snapshot.child(NAME).getValue(String.class);

                DataSnapshot colorSnapshot = snapshot.child(COLOR);
                DataSnapshot accentColorSnapshot = snapshot.child(ACCENT_COLOR);

                int color = colorSnapshot.exists()
                        ? Color.parseColor(colorSnapshot.getValue(String.class))
                        : 0;

                int accentColor = accentColorSnapshot.exists()
                        ? Color.parseColor(accentColorSnapshot.getValue(String.class))
                        : 0;

                List<Action> actions = parseActions(snapshot.child(ACTIONS));

                ServiceBuilder builder = new ServiceBuilder(key)
                        .setName(name)
                        .setColor(color)
                        .setAccent(accentColor)
                        .setActions(actions);

                list.add(builder.build());
            }

            return list;
        }

        @Override
        public List<Action> parseActions(DataSnapshot object) {

            int count = (int) object.getChildrenCount();

            List<Action> list = new ArrayList<>(count);

            for (DataSnapshot snapshot : object.getChildren()) {

                String key = snapshot.child(KEY).getValue(String.class);
                String name = snapshot.child(NAME).getValue(String.class);

                List<Field> fields= parseFields(snapshot.child(FIELDS));
                List<Template> templates = parseTemplates(snapshot.child(TEMPLATES));

                ActionBuilder builder = new ActionBuilder(key)
                        .setName(name)
                        .setFields(fields)
                        .setTemplates(templates);

                list.add(builder.build());
            }

            return list;
        }

        @Override
        public List<Template> parseTemplates(DataSnapshot object) {

            int count = (int) object.getChildrenCount();

            List<Template> list = new ArrayList<>(count);

            for (DataSnapshot snapshot : object.getChildren()) {

                String key = snapshot.child(KEY).getValue(String.class);
                String value = snapshot.child(VALUE).getValue(String.class);

                Template template = new TemplateBuilder(key).setValue(value).build();

                list.add(template);
            }

            return list;
        }

        @Override
        public List<Field> parseFields(DataSnapshot object) {

            int count = (int) object.getChildrenCount();

            List<Field> list = new ArrayList<>(count);

            for (DataSnapshot snapshot : object.getChildren()) {

                String type = snapshot.child(TYPE).getValue(String.class);

                list.add(parseField(type, snapshot));
            }

            return list;
        }

        @Override
        public Field parseField(String type, DataSnapshot object) {

            String key = object.child(KEY).getValue(String.class);
            String label = object.child(LABEL).getValue(String.class);

            switch (type) {

                case Field.TYPE_INPUT:

                    String hint = object.child(HINT).getValue(String.class);

                    InputField inputField = new InputField();
                    inputField.setKey(key);
                    inputField.setType(type);
                    inputField.setLabel(label);
                    inputField.setHint(hint);
                    return inputField;

                case Field.TYPE_BOOLEAN:

                    String templateYes = object.child(TEMPLATE_YES).getValue(String.class);
                    String templateNo = object.child(TEMPLATE_NO).getValue(String.class);

                    BooleanField booleanField = new BooleanField();
                    booleanField.setKey(key);
                    booleanField.setType(type);
                    booleanField.setLabel(label);
                    booleanField.setTemplateNo(templateNo);
                    booleanField.setTemplateYes(templateYes);
                    return booleanField;

                default: return null;
            }
        }
    }
}