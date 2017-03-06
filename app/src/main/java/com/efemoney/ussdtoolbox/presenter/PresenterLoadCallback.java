package com.efemoney.ussdtoolbox.presenter;

import android.support.v4.app.LoaderManager;

/**
 * Primarily for shortening the name in extending classes
 */
public interface PresenterLoadCallback<T> extends LoaderManager.LoaderCallbacks<T> {}
