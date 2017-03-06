package com.efemoney.ussdtoolbox.util;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Efe on 01/03/2017.
 */

public class DiffCb<T> extends DiffUtil.Callback {

    private final List<T> oldT;
    private final List<T> newT;

    public DiffCb(List<T> oldT, List<T> newT) {

        this.oldT = oldT;
        this.newT = newT;
    }

    @Override
    public int getOldListSize() {

        return oldT.size();
    }

    @Override
    public int getNewListSize() {

        return newT.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        T ol = oldT.get(oldItemPosition);
        T nw = newT.get(newItemPosition);

        return ol != null && nw != null && ol.equals(nw);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return false;
    }
}
