package co.sigmoidlabs.bankussdtoolbox.selectbanks;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.R;
import co.sigmoidlabs.bankussdtoolbox.data.models.Bank;
import co.sigmoidlabs.bankussdtoolbox.databinding.ItemBankBinding;

/**
 * Adapter for list of banks
 */
public class BanksAdapter extends RecyclerView.Adapter<BankViewHolder> {

    private Context mContext;
    private List<Bank> mBanks;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void onItemClick(int position, Bank bank);
    }

    public BanksAdapter(Context context, List<Bank> banks) {
        this.mContext = context;
        this.mBanks = banks;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ItemBankBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_bank, parent,
                false);
        return new BankViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BankViewHolder holder, final int position) {
        final Bank bank = getItem(position);
        if (bank != null) {
            // TODO: 03/08/2016 load bank logo & set bank name
            Glide.with(mContext)
                    .load(bank.bankLogo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.imageBankLogo);

            holder.binding.textBankName.setText(bank.bankName);

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
