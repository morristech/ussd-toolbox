package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Outline;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.R;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;
import co.sigmoidlabs.bankussdtoolbox.databinding.ItemBankBinding;

/**
 * Adapter for list of banks
 */
public class BanksAdapter extends RecyclerView.Adapter<BankViewHolder> {

    private Context mContext;
    private List<Bank> mBanks;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    private int mTileSize;
    private float mTileCornerRadius;

    public interface ItemClickListener {
        void onItemClick(int position, Bank bank);
    }

    public BanksAdapter(Context context, List<Bank> banks) {
        this.mContext = context;
        this.mBanks = banks;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTileCornerRadius = mContext.getResources().getDimension(R.dimen.bank_item_curve);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setItems(List<Bank> banks) {
        mBanks = banks;
        notifyDataSetChanged();
    }

    @Override
    public BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ItemBankBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_bank, parent,
                false);
        final BankViewHolder holder = new BankViewHolder(binding);
        holder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mTileSize = binding.bankBackground.getWidth();
                        holder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        holder.itemView.setOutlineProvider(new ViewOutlineProvider() {
                            @Override
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, mTileSize, mTileSize, mTileCornerRadius);
                                holder.itemView.setClipToOutline(true);
                            }
                        });
                    }
                });
        return new BankViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BankViewHolder holder, final int position) {
        final Bank bank = getItem(position);
        if (bank != null) {

            holder.binding.textBankName.setText(bank.getName());
            holder.binding.bankBackground.setBackgroundColor(bank.getColor());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(position, bank);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBanks.size();
    }

    public Bank getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mBanks.get(position);
        }
        return null;
    }

}
