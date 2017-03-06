package com.efemoney.ussdtoolbox.ui.selectaction;

import android.support.annotation.NonNull;

import com.efemoney.ussdtoolbox.data.source.ServicesRepository;
import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.util.CollectionsUtil;
import com.efemoney.ussdtoolbox.util.UssdCodeGenerator;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class ServiceActionsPresenter implements ServiceActionsMvp.Presenter {

    private final String serviceKey;
    private final ServicesRepository repository;

    private ServiceActionsMvp.View serviceActionsView;

    public ServiceActionsPresenter(String serviceKey, ServicesRepository repository) {
        this.serviceKey = serviceKey;
        this.repository = repository;
    }

    @Override
    public void bindView(ServiceActionsMvp.View view) {
        serviceActionsView = view;
        serviceActionsView.setPresenter(this);
    }

    @Override
    public void unbindView() {
        serviceActionsView.setPresenter(null);
        serviceActionsView = null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void onActionSelected(Action action) {

        if (action == null) throw new NullPointerException("The selected action cannot be null");

        if (shouldShowActionDetails(action)) {

            serviceActionsView.showActionFields(action);
        }

        if (action.getFields() == null || action.getFields().size() == 0) {

            String ussd = UssdCodeGenerator.generate(action.getTemplates().get(0));
            // serviceActionsView.showUssdCode(service.getColor(), action.getName(), ussd);

        } else {

        }
    }

    private boolean shouldShowActionDetails(@NonNull Action action) {

        return !CollectionsUtil.isEmpty(action.getTemplates());
    }
}
