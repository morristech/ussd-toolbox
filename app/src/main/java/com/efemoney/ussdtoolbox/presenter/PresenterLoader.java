package com.efemoney.ussdtoolbox.presenter;

import android.content.Context;
import android.support.v4.content.Loader;

import com.efemoney.ussdtoolbox.base.BasePresenter;

/**
 * Created by Efe on 22/02/2017.
 */

public class PresenterLoader<T extends BasePresenter> extends Loader<T> {

    private final PresenterFactory<T> factory;
    private T presenter;

    /**
     * @param context used to retrieve the application context.
     * @param factory factory to creates instances of {@code T} presenter
     */
    public PresenterLoader(Context context, PresenterFactory<T> factory) {
        super(context);
        this.factory = factory;
    }

    @Override
    protected void onStartLoading() {

        // If we already own an instance, simply deliver it.
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }

        // Otherwise, force a load
        forceLoad();
    }

    @Override
    protected void onForceLoad() {

        // Create the Presenter using the Factory
        presenter = factory.createPresenter();

        // Deliver the result
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {

        presenter = null;
    }
}