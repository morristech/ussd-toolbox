package com.efemoney.ussdtoolbox.base;

public interface BasePresenter<V extends BaseView> {

    void bindView(V view);

    void unbindView();

    void subscribe();

    void unsubscribe();
}
