package com.efemoney.ussdtoolbox.ui.selectaction;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.efemoney.ussdtoolbox.R;
import com.efemoney.ussdtoolbox.databinding.ActivityActionsBinding;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class ServiceActionActivity extends AppCompatActivity {

    private static final String EXTRA_KEY = "service_key";

    private ActivityActionsBinding binding;

    public static Intent createLaunchIntent(Context context, String serviceKey) {

        Intent intent = new Intent(context, ServiceActionActivity.class);
        intent.putExtra(EXTRA_KEY, serviceKey);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_actions);
    }
}
