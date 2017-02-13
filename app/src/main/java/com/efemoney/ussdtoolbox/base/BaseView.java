package com.efemoney.ussdtoolbox.base;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showUssdCode(int color, String action, String ussdCode);
}
