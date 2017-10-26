package com.efemoney.ussdtoolbox.ui.services.search;

import com.efemoney.ussdtoolbox.data.source.ServicesRepository;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class SearchPresenter implements SearchServicesMvp.Presenter {

    private final ServicesRepository repository;
    private final SearchServicesMvp.View view;

    public SearchPresenter(ServicesRepository repository, SearchServicesMvp.View view) {
        this.repository = repository;
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
