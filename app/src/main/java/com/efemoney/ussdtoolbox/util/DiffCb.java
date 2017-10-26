package com.efemoney.ussdtoolbox.util;

import android.support.v7.util.DiffUtil;

import java.util.Comparator;
import java.util.List;

/**
 * Simple {@link DiffUtil.Callback} implementation that assumes that the
 * content of two items being compared, are never the same
 */
@SuppressWarnings("SimplifiableIfStatement")
public class DiffCb<T> extends DiffUtil.Callback {

    private final List<T> oldT;
    private final List<T> newT;

    private final Comparator<T> comparator;

    public DiffCb(List<T> oldT, List<T> newT) {

        this (oldT, newT, null);
    }

    public DiffCb(List<T> oldT, List<T> newT, Comparator<T> comparator) {

        this.oldT = oldT;
        this.newT = newT;
        this.comparator = comparator;
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

        if (ol == null || nw == null) return false;

        return ol.equals(nw) || comparator != null && comparator.compare(ol, nw) == 0;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return false;
    }
}
