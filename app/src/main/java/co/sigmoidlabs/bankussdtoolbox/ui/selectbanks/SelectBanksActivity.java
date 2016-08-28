package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.R;
import co.sigmoidlabs.bankussdtoolbox.base.BaseActivity;
import co.sigmoidlabs.bankussdtoolbox.data.BanksRepository;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;
import co.sigmoidlabs.bankussdtoolbox.databinding.ActivitySelectBanksBinding;
import co.sigmoidlabs.bankussdtoolbox.ui.selectbanks.adapter.BanksAdapter;
import co.sigmoidlabs.bankussdtoolbox.ui.selectbanks.adapter.SectionItem;
import co.sigmoidlabs.bankussdtoolbox.widget.decoration.SectionItemDecoration;
import co.sigmoidlabs.bankussdtoolbox.widget.decoration.SpaceItemDecoration;

public class SelectBanksActivity extends BaseActivity implements
        SelectBanksContract.View, BanksAdapter.ItemClickListener {

    private static final String BUNDLE_BANKS = "bundle_banks";
    private static final int COLUMN_COUNT = 3;

    private ActivitySelectBanksBinding mBinding;
    private SelectBanksPresenter mPresenter;

    private BanksRepository mBankRepository;
    private BanksAdapter mAdapter;

    private ArrayList<Bank> mBanks;
    private float mGridSpacing;
    private int mColumnCount = COLUMN_COUNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_banks);

        mGridSpacing = getResources().getDimension(R.dimen.grid_spacing);
        mColumnCount = getResources().getInteger(R.integer.grid_column);

        mBankRepository = new BanksRepository();
        mPresenter = new SelectBanksPresenter(this, mBankRepository);

        if (savedInstanceState == null) {
            mBanks = new ArrayList<>();
        } else {
            mBanks = savedInstanceState.getParcelableArrayList(BUNDLE_BANKS);
        }

        mBinding.toolbar.toolbar.setTitle(R.string.title_select_bank);
        mAdapter = new BanksAdapter(this, null, mBanks);
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
        // TODO: 13/08/2016 implement showing error with a refresh button
    }

    @Override
    public void showBanks(List<Bank> bankList) {
        mBanks = new ArrayList<>(bankList);
        mAdapter.setAllBanks(bankList);
        mAdapter.setFavouriteBanks(bankList.subList(0,2));
        mBinding.recyclerView.setAdapter(mAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, mColumnCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int DEFAULT_SIZE = 1;
                Object item = mAdapter.getItem(position);
                if (item != null) {
                    if (item instanceof SectionItem) {
                        return 3;
                    } else {
                        return DEFAULT_SIZE;
                    }
                } else {
                    return DEFAULT_SIZE;
                }
            }
        });
        mBinding.recyclerView.setLayoutManager(gridLayoutManager);
        mBinding.recyclerView.addItemDecoration(
                new SectionItemDecoration(mColumnCount, (int) mGridSpacing, true));

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
    public void showUssdCode(int color, String action, String ussdCode) {

    }

    @Override
    public void onItemClick(int position, Bank bank) {
        showBankActions(bank);
    }
}
