package co.sigmoidlabs.bankussdtoolbox.selectbanks;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.R;
import co.sigmoidlabs.bankussdtoolbox.base.BaseActivity;
import co.sigmoidlabs.bankussdtoolbox.data.BanksRepository;
import co.sigmoidlabs.bankussdtoolbox.data.models.Bank;
import co.sigmoidlabs.bankussdtoolbox.databinding.ActivitySelectBanksBinding;
import co.sigmoidlabs.bankussdtoolbox.widget.decoration.SpaceItemDecoration;

public class SelectBanksActivity extends BaseActivity implements SelectBanksContract.View,
        BanksAdapter.ItemClickListener {
    private static final String BUNDLE_BANKS = "bundle_banks";
    private static final int COLUMN_COUNT = 3;

    private ActivitySelectBanksBinding mBinding;
    private SelectBanksPresenter mPresenter;

    private BanksRepository mBankRepository;
    private BanksAdapter mAdapter;

    private ArrayList<Bank> mBanks;
    private float mGridSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_banks);

        mGridSpacing = getResources().getDimension(R.dimen.grid_spacing);

        mBankRepository = new BanksRepository();
        mPresenter = new SelectBanksPresenter(this, mBankRepository);

        if (savedInstanceState == null) {
            mBanks = new ArrayList<>();
        } else {
            mBanks = savedInstanceState.getParcelableArrayList(BUNDLE_BANKS);
        }

        mAdapter = new BanksAdapter(this, mBanks);
        mPresenter.loadBanks();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_BANKS, mBanks);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showLoading(boolean visible) {
        mBinding.progressLoading.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showBanks(List<Bank> bankList) {
        mBanks = new ArrayList<>(bankList);
        mAdapter.setItems(bankList);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMN_COUNT));
        mBinding.recyclerView.addItemDecoration(
                new SpaceItemDecoration(COLUMN_COUNT, (int) mGridSpacing, true));
        mAdapter.setItemClickListener(this);
    }

    @Override
    public void showBankActions(Bank bank) {
        // TODO: 04/08/2016 start the bank action activity
    }

    @Override
    public void setPresenter(SelectBanksContract.Presenter presenter) {
        mPresenter = (SelectBanksPresenter) presenter;
    }

    @Override
    public void onItemClick(int position, Bank bank) {
        showBankActions(bank);
    }
}
