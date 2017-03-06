package com.efemoney.ussdtoolbox.ui.services;

import com.efemoney.ussdtoolbox.base.BasePresenter;
import com.efemoney.ussdtoolbox.base.BaseView;
import com.efemoney.ussdtoolbox.data.model.Service;

import java.util.List;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public interface ServicesMvp {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);


        void showGenericError(String errorMessage);

        void showLoadingServicesError();

        void showNoNetwork();

        void showNoServices();


        void showServices(List<Service> services);

        void showActionsScreen(String serviceKey);

        void updateFave(String key);
    }

    interface Presenter extends BasePresenter<View> {

        void onServiceClick(Service service);

        void onServiceFaveClicked(Service service);
    }
}
