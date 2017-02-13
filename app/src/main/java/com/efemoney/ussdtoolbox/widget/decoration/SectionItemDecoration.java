package com.efemoney.ussdtoolbox.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.efemoney.ussdtoolbox.ui.selectbanks.adapter.BankViewHolder;
import com.efemoney.ussdtoolbox.ui.selectbanks.adapter.BanksAdapter;

public class SectionItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public SectionItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);


        if (viewHolder instanceof BankViewHolder) {
            int pos = position;
            if (parent.getAdapter() instanceof BanksAdapter) {
                BanksAdapter banksAdapter = ((BanksAdapter)parent.getAdapter());
                boolean hasFavouriteBanks = banksAdapter.getFavouriteBanksCount() > 0;
                boolean hasAllBanks = banksAdapter.getAllBanksCount() > 0;

                if (hasFavouriteBanks && position <= banksAdapter.getFavouriteBanksCount()) {
                    pos = pos - 1;
                } else if (hasFavouriteBanks && hasAllBanks &&
                        position > banksAdapter.getFavouriteBanksCount()) {
                    pos = pos - 2 - banksAdapter.getFavouriteBanksCount();
                } else if (!hasFavouriteBanks && hasAllBanks) {
                    pos = pos - 1;
                }
            }

            int column = pos % spanCount; // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (pos < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (pos >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}

