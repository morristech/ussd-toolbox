package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.R;
import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;
import co.sigmoidlabs.bankussdtoolbox.databinding.ActivityBankActionsBinding;
import co.sigmoidlabs.bankussdtoolbox.widget.decoration.DividerItemDecoration;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class BankActionActivity extends AppCompatActivity
        implements BankActionContract.View {

    private static final String EXTRA_BANK = "extra_bank";
    private static final String BUNDLE_ACTIONS = "bundle_actions";

    private ActivityBankActionsBinding mBinding;

    private BankActionsPresenter mPresenter;
    private ActionsAdapter mAdapter;
    private ArrayList<Action> mActions;

    public static Intent createLaunchIntent(Context context, Bank bank) {

        Intent intent = new Intent(context, BankActionActivity.class);
        intent.putExtra(EXTRA_BANK, bank);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bank_actions);

        if (savedInstanceState == null) {
            mActions = new ArrayList<>();
        } else {
            mActions = savedInstanceState.getParcelableArrayList(BUNDLE_ACTIONS);
        }

        Bank bank = getIntent().getParcelableExtra(EXTRA_BANK);

        mPresenter = new BankActionsPresenter(bank, this);
        mAdapter = new ActionsAdapter(this, mActions);
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
    public void showActions(List<Action> actions) {

        mActions.clear();
        mActions.addAll(actions);

        mAdapter.setItems(mActions);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this);

        mBinding.content.setLayoutManager(layoutManager);
        mBinding.content.addItemDecoration(divider);
        mBinding.content.setAdapter(mAdapter);
    }

    @Override
    public void showActionFields(Action action) {
        // TODO: 13/08/2016 start the action fields activity
    }

    @Override
    public void setPresenter(BankActionContract.Presenter presenter) {
        mPresenter = (BankActionsPresenter) presenter;
    }
}
