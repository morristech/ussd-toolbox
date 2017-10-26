package com.efemoney.ussdtoolbox.widget;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.efemoney.ussdtoolbox.util.DiffCb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 29/04/2017.
 */
public abstract class DiffCalculatingAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    @NonNull protected List<T> data;

    public DiffCalculatingAdapter(@NonNull List<T> data) {

        this.data = data;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public void replaceData(List<T> data) {

        replaceInternal(data);
    }

    private void replaceInternal(List<T> newData) {

        // Make a defensive copy of the old data
        List<T> oldData = new ArrayList<>(data);

        // Get Main thread handler
        Handler handler = new Handler(Looper.getMainLooper());

        // Calculate diff in new thread and post to the main thread
        new Thread(() -> {

            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCb<>(oldData, newData));
            handler.post(() -> applyDiffResult(diffResult, newData));

        }).start();
    }

    private void applyDiffResult(DiffUtil.DiffResult result, List<T> data) {
        this.data = new ArrayList<>(data);
        result.dispatchUpdatesTo(this);
    }
}
