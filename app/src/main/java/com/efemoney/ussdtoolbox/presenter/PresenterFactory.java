package com.efemoney.ussdtoolbox.presenter;

import com.efemoney.ussdtoolbox.base.BasePresenter;

/**
 * Created by Efe on 22/02/2017.
 */

public interface PresenterFactory<T extends BasePresenter> {

    T createPresenter();
}
