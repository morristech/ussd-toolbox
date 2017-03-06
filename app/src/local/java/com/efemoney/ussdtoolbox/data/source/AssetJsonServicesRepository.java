package com.efemoney.ussdtoolbox.data.source;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;

import com.efemoney.ussdtoolbox.data.ServiceMetaData;
import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.BooleanField;
import com.efemoney.ussdtoolbox.data.model.Field;
import com.efemoney.ussdtoolbox.data.model.InputField;
import com.efemoney.ussdtoolbox.data.model.Service;
import com.efemoney.ussdtoolbox.data.model.Template;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.functions.Functions;

/**
 * Created by Efe on 26/02/2017.
 */

@Singleton
public class AssetJsonServicesRepository implements ServicesRepository {

    private Context context;
    private ServiceMetaData serviceMetaData;
    private AssetsJsonServiceParser parser;

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

        // returning 0 signals they are equal and calls .compare(obj) on the Service.
        // Lets return -1 to preserve ordering
        return -1;
    };

    private boolean cacheInvalid = true;
    private HashMap<String, Service> cache;


    @Inject public AssetJsonServicesRepository(Context context, ServiceMetaData serviceMetaData) {
        this.serviceMetaData = serviceMetaData;
        this.context = context.getApplicationContext();
        this.parser = new AssetsJsonServiceParser();
    }

    @Override
    public Observable<List<Service>> getServices() {

        // Return the cache if it is still valid
        if (!cacheInvalid && cache != null) {

            return Observable.fromIterable(cache.values()) // Get the cache

                    /*
                        Removing this cos from the source it applies .toList(), .toObservable(), .map()
                        & .flatMapIterable() creating a bunch of unneeded objects along the way
                        We are just interested in .map(). See below.
                    */

                    // .sorted(comparator)

                    .map(service -> {

                        // Update fave status
                        boolean fave = serviceMetaData.getFavorite(service.getKey());
                        service.setFavorite(fave);

                        return service;
                    })
                    .toList() // To list
                    .map(Functions.listSorter(comparator)) // Re sort the list
                    .toObservable(); // To observable

        } else if (cache == null) cache = new HashMap<>();

        Observable<List<Service>> obsv = Observable.create(e -> {

            // There is no special resource cleanup. Set empty disposable
            e.setDisposable(Disposables.empty());

            AssetManager assets = context.getAssets();
            InputStream is = assets.open("services.json");

            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(is)));
            reader.setLenient(true);

            // Lots and lots of cool 'public' adapters in the TypeAdapters class
            JsonElement jsonElement = TypeAdapters.JSON_ELEMENT.read(reader);

            List<Service> services = parser.parseServices(jsonElement);

            if (!e.isDisposed()) {
                e.onNext(services);
                e.onComplete();
            }
        });

        return obsv.flatMap(services -> Observable.fromIterable(services)

                /*
                    Removing this cos from the source it applies .toList(), .toObservable(), .map()
                    & .flatMapIterable() creating a bunch of unneeded objects along the way
                    We are just interested in .map(). See below.
                */

                // .sorted(comparator)

                .map(service -> {

                    // Update fave status
                    boolean fave = serviceMetaData.getFavorite(service.getKey());
                    service.setFavorite(fave);
                    return service;
                })
                .doOnNext(service -> cache.put(service.getKey(), service)) // Cache each one
                .toList() // recombine as list
                .map(Functions.listSorter(comparator)) // Sort it
                .toObservable()) // to observable
                .doOnComplete(() -> cacheInvalid = false); // Un-invalidate cache
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

        // Update cache
        Service service = cache.get(key);
        boolean fave = serviceMetaData.getFavorite(key);
        if (service != null) service.setFavorite(fave);

        // Update network if available
    }

    @Override
    public Observable<List<Action>> getActionsForService(String key) {

        return getService(key).map(Service::getActions);
    }


    private static final class AssetsJsonServiceParser implements ServiceParser<JsonElement> {

        @Override
        public List<Service> parseServices(JsonElement object) {

            JsonArray serviceArray = object.getAsJsonArray();

            int count = serviceArray.size();

            List<Service> list = new ArrayList<>(count);

            for (JsonElement element : serviceArray) {

                JsonObject service = element.getAsJsonObject();

                String key = service.get(KEY).getAsString();
                String name = service.get(NAME).getAsString();

                JsonElement colorElement = service.get(COLOR);
                JsonElement accentElement = service.get(ACCENT_COLOR);

                int color = colorElement != null
                        ? Color.parseColor(colorElement.getAsString())
                        : 0;

                int accentColor = accentElement != null
                        ? Color.parseColor(accentElement.getAsString())
                        : 0;

                List<Action> actions = parseActions(service.get(ACTIONS));

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
        public List<Action> parseActions(JsonElement object) {

            JsonArray serviceArray = object.getAsJsonArray();

            int count = serviceArray.size();

            List<Action> list = new ArrayList<>(count);

            for (JsonElement element : serviceArray) {

                JsonObject action = element.getAsJsonObject();

                String key = action.get(KEY).getAsString();
                String name = action.get(NAME).getAsString();

                List<Field> fields = parseFields(action.get(FIELDS));
                List<Template> templates = parseTemplates(action.get(TEMPLATES));

                ActionBuilder builder = new ActionBuilder(key)
                        .setName(name)
                        .setFields(fields)
                        .setTemplates(templates);

                list.add(builder.build());
            }

            return list;
        }

        @Override
        public List<Template> parseTemplates(JsonElement object) {

            JsonArray serviceArray = object.getAsJsonArray();

            int count = serviceArray.size();

            List<Template> list = new ArrayList<>(count);

            for (JsonElement element : serviceArray) {

                JsonObject action = element.getAsJsonObject();

                String key = action.get(KEY).getAsString();
                String value = action.get(VALUE).getAsString();

                Template template = new TemplateBuilder(key).setValue(value).build();

                list.add(template);
            }

            return list;
        }

        @Override
        public List<Field> parseFields(JsonElement object) {

            JsonArray serviceArray = object.getAsJsonArray();

            int count = serviceArray.size();

            List<Field> list = new ArrayList<>(count);

            for (JsonElement element : serviceArray) {

                JsonObject field = element.getAsJsonObject();

                String type = field.get(TYPE).getAsString();

                list.add(parseField(type, field));
            }

            return list;
        }

        @Override
        public Field parseField(String type, JsonElement element) {

            JsonObject object = element.getAsJsonObject();

            String key = object.get(KEY).getAsString();
            String label = object.get(LABEL).getAsString();

            switch (type) {

                case Field.TYPE_INPUT:

                    String hint = object.get(HINT).getAsString();

                    InputField inputField = new InputField();
                    inputField.setKey(key);
                    inputField.setType(type);
                    inputField.setLabel(label);
                    inputField.setHint(hint);
                    return inputField;

                case Field.TYPE_BOOLEAN:

                    String templateYes = object.get(TEMPLATE_YES).getAsString();
                    String templateNo = object.get(TEMPLATE_NO).getAsString();

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
