package com.efemoney.ussdtoolbox.ui.services.search;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efemoney.ussdtoolbox.base.BaseFragment;
import com.efemoney.ussdtoolbox.databinding.FragmentSarchServicesBinding;
import com.efemoney.ussdtoolbox.util.Preconditions;
import com.efemoney.ussdtoolbox.widget.DiffCalculatingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 28/04/2017.
 */

public class SearchListFragment extends BaseFragment implements SearchServicesMvp.View {

    private SearchServicesMvp.Presenter presenter;
    private FragmentSarchServicesBinding binding;
    private SearchAdapter adapter;

    public static SearchListFragment newInstance() {

        Bundle args = new Bundle();

        SearchListFragment fragment = new SearchListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(SearchServicesMvp.Presenter presenter) {

        this.presenter = Preconditions.checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding = FragmentSarchServicesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        adapter = new SearchAdapter(getContext(), new ArrayList<>(0));
        binding.list.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    private class SearchAdapter extends DiffCalculatingAdapter<SearchResult, SearchAdapter.ViewHolder> {

        private final LayoutInflater inflater;

        SearchAdapter(Context context, @NonNull List<SearchResult> data) {
            super(data);
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ViewHolder(ViewDataBinding binding) {

                super(binding.getRoot());
            }
        }
    }

    public class SearchResult{

    }
}
