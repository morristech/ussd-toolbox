package com.efemoney.ussdtoolbox.base;

import android.support.v4.app.Fragment;

import com.efemoney.ussdtoolbox.App;

public class BaseFragment extends Fragment {

    public final App getApp() {

        return getBaseActivity().getApp();
    }

    public BaseActivity getBaseActivity() {

        return (BaseActivity) getActivity();
    }
}
