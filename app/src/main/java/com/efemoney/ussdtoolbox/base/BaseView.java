package com.efemoney.ussdtoolbox.base;

public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);
}
