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

    private final ServicesRepository repository;
    private final ServicesMvp.View view;

    private Disposable disposable;

    public ServicesPresenter(ServicesRepository repository, ServicesMvp.View view) {
        this.repository = repository;
        this.view = view;

        this.view.setPresenter(this);
    }


    @Override
    public void subscribe() {

        load();
    }

    @Override
    public void unsubscribe() {

        disposeIfNecessary();
    }

    @Override
    public void onServiceClick(Service service) {
        repository.clickService(service.getKey());
        load(); // Refresh services

        view.showActionsScreen(service.getKey());
    }

    @Override
    public void onServiceFaveClicked(Service service) {
        repository.favoriteService(service.getKey());
        load(); // Refresh services
    }

    @Override
    public void onActionUpdate() {

    }

    @Override
    public void onActionClearFrequents() {

    }


    private void load() {

        loadServices(!repository.isFromCache());
    }

    private void disposeIfNecessary() {

        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

    private void loadServices(boolean showLoading) {

        disposeIfNecessary();

        disposable = repository.getServices()
                .doOnSubscribe(d -> {
                    if (showLoading) view.setLoadingIndicator(true);
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .doFinally(() -> {
                    if (showLoading) view.setLoadingIndicator(false);
                })
                .subscribe(
                        this::servicesLoaded, // onNext
                        t -> view.showLoadingServicesError() // onError
                )
        ;
    }

    private void servicesLoaded(List<Service> services) {

        if (services.isEmpty()) {
            view.showNoServices();
        } else {
            view.showServices(services);
        }
    }
}
