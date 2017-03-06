package com.efemoney.ussdtoolbox.ui.services;

import com.efemoney.ussdtoolbox.data.source.ServicesRepository;
import com.efemoney.ussdtoolbox.data.model.Service;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class ServicesPresenter implements ServicesMvp.Presenter {

    private final ServicesRepository servicesRepository;

    private Disposable disposable;
    private ServicesMvp.View servicesView;

    public ServicesPresenter(ServicesRepository servicesRepository) {

        this.servicesRepository = servicesRepository;
    }

    @Override
    public void bindView(ServicesMvp.View view) {
        servicesView = view;
        servicesView.setPresenter(this);
    }

    @Override
    public void unbindView() {
        servicesView.setPresenter(null);
        servicesView = null;
    }

    @Override
    public void subscribe() {

        loadServices(false);
    }

    private void loadServices(boolean showLoading) {

        if (disposable != null && !disposable.isDisposed()) disposable.dispose();

        disposable = servicesRepository.getServices()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .doOnSubscribe(d -> {
                    if (showLoading) servicesView.setLoadingIndicator(true);
                })
                .doFinally(() -> {
                    if (showLoading) servicesView.setLoadingIndicator(false);
                })

                .subscribe(
                        this::servicesLoaded, // onNext
                        throwable -> servicesView.showLoadingServicesError() // onError
                )
        ;
    }

    @Override
    public void unsubscribe() {

        disposable.dispose();
    }

    @Override
    public void onServiceClick(Service service) {
        servicesRepository.clickService(service.getKey());
        loadServices(false);
    }

    @Override
    public void onServiceFaveClicked(Service service) {
        servicesRepository.favoriteService(service.getKey());
        loadServices(false);
    }


    private void servicesLoaded(List<Service> services) {

        if (services.isEmpty()) {
            servicesView.showNoServices();
        } else {
            servicesView.showServices(services);
        }
    }
}
