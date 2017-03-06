package com.efemoney.ussdtoolbox.ui.selectaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.efemoney.ussdtoolbox.data.model.Service;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class ServiceActionActivity extends AppCompatActivity {

    public static Intent createLaunchIntent(Context context, Service service) {

        Intent intent = new Intent(context, ServiceActionActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
