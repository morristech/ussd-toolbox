package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.databinding.ItemActionBinding;

/**
 * Adapter for list of banks
 */
public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionViewHolder> {

    private Context mContext;
    private List<Action> mActions;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void onItemClick(int position, Action action);
    }

    public ActionsAdapter(Context context, List<Action> actions) {
        this.mContext = context;
        this.mActions = actions;
        this.mInflater = (LayoutInflater.from(mContext));
    }

    public void setItems(List<Action> actions) {
        mActions = actions;
        notifyDataSetChanged();
    }

    public Action getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mActions.get(position);
        }
        return null;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {

        return mActions.size();
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemActionBinding binding = ItemActionBinding.inflate(mInflater);

        return new ActionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {

        holder.bind(getItem(position));
    }



    class ActionViewHolder extends RecyclerView.ViewHolder {

        ItemActionBinding binding;

        ActionViewHolder(ItemActionBinding binding) {
            super(binding.getRoot());

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
