package com.efemoney.ussdtoolbox.ui.bankaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.List;

import com.efemoney.ussdtoolbox.R;
import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.Bank;
import com.efemoney.ussdtoolbox.databinding.ActivityBankActionsBinding;
import com.efemoney.ussdtoolbox.databinding.DialogUssdCodeBinding;
import com.efemoney.ussdtoolbox.util.ColorUtils;
import com.efemoney.ussdtoolbox.util.DrawableUtils;
import com.efemoney.ussdtoolbox.widget.decoration.DividerItemDecoration;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class BankActionActivity extends AppCompatActivity
        implements BankActionContract.View, ActionsAdapter.ItemClickListener {

    private static final String EXTRA_BANK = "extra_bank";
    private static final String BUNDLE_ACTIONS = "bundle_actions";

    private ActivityBankActionsBinding mBinding;

    private BankActionsPresenter mPresenter;
    private ActionsAdapter mAdapter;

    public static Intent createLaunchIntent(Context context, Bank bank) {

        Intent intent = new Intent(context, BankActionActivity.class);
        intent.putExtra(EXTRA_BANK, bank);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bank_actions);

        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this);
        mAdapter = new ActionsAdapter(this);
        mAdapter.setItemClickListener(this);

        mBinding.content.setLayoutManager(layoutManager);
        mBinding.content.addItemDecoration(divider);
        mBinding.content.swapAdapter(mAdapter, false);

        Bank bank = getIntent().getParcelableExtra(EXTRA_BANK);
        mPresenter = new BankActionsPresenter(bank, this);
        mPresenter.loadActions();
    }

    @Override
    public void showLoading(boolean visible) {
        // TODO: 13/08/2016 Add loading view
    }

    @Override
    public void showError(String message) {
        // TODO: 13/08/2016 Add error view
    }

    @Override
    public void showTitle(Bank bank) {
        mBinding.setBank(bank);
        mAdapter.setBankColor(bank.getColor());

        // Use 87% black for a light shade color else use full white
        int tint = ColorUtils.isDark(bank.getColor()) ? 0xFFFFFFFF : 0xDE000000;

        mBinding.collapser.setExpandedTitleColor(tint);
        mBinding.collapser.setCollapsedTitleTextColor(tint);

        Drawable navIcon = mBinding.toolbar.getNavigationIcon();
        if (navIcon != null) {
            Drawable tintedNavIcon = DrawableUtils.tintDrawable(navIcon, tint);
            mBinding.toolbar.setNavigationIcon(tintedNavIcon);
        }
    }

    @Override
    public void showActions(List<Action> actions) {

        mAdapter.setItems(actions);
        mBinding.content.swapAdapter(mAdapter, false);
    }

    @Override
    public void showActionFields(Action action) {
        // TODO: 13/08/2016 start the action fields activity
    }

    @Override
    public void setPresenter(BankActionContract.Presenter presenter) {
        mPresenter = (BankActionsPresenter) presenter;
    }

    @Override
    public void showUssdCode(final int color, String action, String ussdCode) {

        DialogUssdCodeBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.dialog_ussd_code, null, false);

        binding.setColor(color);
        binding.setLabel(action);
        binding.setUssdCode(ussdCode);

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(binding.getRoot());
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout)
                        d.findViewById(android.support.design.R.id.design_bottom_sheet);
                if (bottomSheet != null) BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        dialog.show();
    }

    @Override
    public void onItemClick(int position, Action action) {
        mPresenter.onActionSelected(action);
    }
}
