package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.databinding.ItemActionBinding;
import co.sigmoidlabs.bankussdtoolbox.util.DrawableUtils;

/**
 * Adapter for list of actions
 */
class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionViewHolder> {

    private int bankColor;
    private Context mContext;
    private List<Action> mActions;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public ActionsAdapter(Context context) {
        this.mContext = context;
        this.mInflater = (LayoutInflater.from(mContext));
    }

    public void setBankColor(int bankColor) {
        this.bankColor = bankColor;
        notifyDataSetChanged();
    }

    public void setItems(List<Action> actions) {
        mActions = actions;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {

        return mActions.size();
    }

    public Action getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mActions.get(position);
        }
        return null;
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemActionBinding binding = ItemActionBinding.inflate(mInflater, parent, false);

        return new ActionViewHolder(binding, bankColor);
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {

        holder.bind(getItem(position));
    }

    interface ItemClickListener {
        void onItemClick(int position, Action action);
    }

    class ActionViewHolder extends RecyclerView.ViewHolder {

        ItemActionBinding binding;

        ActionViewHolder(ItemActionBinding binding, @ColorInt int bankColor) {
            super(binding.getRoot());

            TextView root = (TextView) binding.getRoot();
            Drawable[] drawables = root.getCompoundDrawables();
            Drawable tintedLeft = DrawableUtils.tintDrawable(drawables[0], bankColor);
            root.setCompoundDrawables(tintedLeft, drawables[1], drawables[2], drawables[3]);

            this.binding = binding;
            this.binding.executePendingBindings();
        }

        void bind(Action action) {

            binding.setAction(action);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(position, getItem(position));
                    }
                }
            });

            binding.executePendingBindings();
        }
    }
}
