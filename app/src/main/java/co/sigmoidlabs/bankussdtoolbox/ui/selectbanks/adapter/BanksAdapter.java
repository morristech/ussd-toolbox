package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.parser.BindingExpressionParser;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.R;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;
import co.sigmoidlabs.bankussdtoolbox.databinding.ItemBankBinding;
import co.sigmoidlabs.bankussdtoolbox.databinding.ItemSectionHeaderBinding;

/**
 * Adapter for list of banks
 */
public class BanksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Bank> mFavouriteBanks;
    private List<Bank> mAllBanks;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    private int mTileSize;
    private float mTileCornerRadius;

    public interface ItemClickListener {
        void onItemClick(int position, Bank bank);
    }

    public BanksAdapter(Context context, List<Bank> favouriteBanks, List<Bank> allBanks) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mTileCornerRadius = mContext.getResources().getDimension(R.dimen.bank_item_curve);

        mAllBanks = allBanks != null ? allBanks : new ArrayList<Bank>();
        mFavouriteBanks = favouriteBanks != null ? favouriteBanks : new ArrayList<Bank>();
        if (mFavouriteBanks.size() > 0) {
            setFavouriteBanks(mFavouriteBanks);
        }
        if (mAllBanks.size() > 0) {
            setAllBanks(mAllBanks);
        }
    }

    private boolean hasFavouriteBanks() {
        return mFavouriteBanks.size() > 0;
    }

    private boolean hasAllBanks() {
        return mAllBanks.size() > 0;
    }

    public int getFavouriteBanksCount() {
        return mFavouriteBanks.size();
    }

    public int getAllBanksCount() {
        return mAllBanks.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return R.layout.item_section_header;
        } else if (position == mFavouriteBanks.size() + 1 && hasFavouriteBanks()) {
            return R.layout.item_section_header;
        } else {
            return R.layout.item_bank;
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void addFavouriteBank(@NonNull Bank bank) {
        if (!mFavouriteBanks.contains(bank)) {
            mFavouriteBanks.add(bank);
            notifyDataSetChanged();
        }
    }

    public void setFavouriteBanks(@NonNull List<Bank> favouriteBanks) {
        for (Bank bank : favouriteBanks) {
            if (!mFavouriteBanks.contains(bank)) {
                mFavouriteBanks.add(bank);
            }
        }
        notifyDataSetChanged();
    }

    public void setAllBanks(@NonNull List<Bank> banks) {
        if (! mAllBanks.containsAll(banks)) {
            mAllBanks.addAll(banks);
            notifyDataSetChanged();
        }
    }

    public void addBank(@NonNull Bank bank) {
        if (!mAllBanks.contains(bank)) {
            mAllBanks.add(bank);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_bank:
                return createBankViewHolder(parent, viewType);
            case R.layout.item_section_header:
                return createSectionViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case R.layout.item_bank:
                bindBankViewHolder((BankViewHolder) holder, position);
                break;
            case R.layout.item_section_header:
                bindSectionViewHolder((SectionViewHolder) holder, position);
                break;
        }
    }

    private void bindSectionViewHolder(final SectionViewHolder holder, int position) {
        SectionItem sectionItem = getSectionItem(position);
        if (sectionItem != null) {
            holder.binding.sectionTitle.setText(sectionItem.getTitle());
        }
    }

    private void bindBankViewHolder(final BankViewHolder holder, int position) {
        final Bank bank = getBankItem(position);
        if (bank != null) {
            holder.binding.textBankName.setText(bank.getName());
            holder.binding.bankBackground.setBackgroundColor(bank.getColor());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(holder.getAdapterPosition(), bank);
                    }
                }
            });
        }
    }

    /**
     * Creates a ViewHolder for the section
     * @param parent
     * @param layoutId
     * @return
     */
    private SectionViewHolder createSectionViewHolder(ViewGroup parent, int layoutId) {
        final ItemSectionHeaderBinding binding = DataBindingUtil.inflate(mInflater, layoutId, parent,
                false);
        return new SectionViewHolder(binding);
    }

    /**
     * Creates a VieHolder for the bank item
     * @param parent
     * @param layoutId
     * @return
     */
    private BankViewHolder createBankViewHolder(ViewGroup parent, int layoutId) {
        final ItemBankBinding binding = DataBindingUtil.inflate(mInflater, layoutId, parent,
                false);
        final BankViewHolder holder = new BankViewHolder(binding);
        holder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mTileSize = binding.bankBackground.getWidth();
                        holder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            holder.itemView.setOutlineProvider(new ViewOutlineProvider() {
                                @Override
                                public void getOutline(View view, Outline outline) {
                                    outline.setRoundRect(0, 0, mTileSize, mTileSize, mTileCornerRadius);
                                    holder.itemView.setClipToOutline(true);
                                }
                            });
                        }
                    }
                });

        return new BankViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        if (hasAllBanks() && !hasFavouriteBanks()) {
            return mAllBanks.size() + 1;
        } else if (hasAllBanks() && hasFavouriteBanks()) {
            return mAllBanks.size() + mFavouriteBanks.size() + 2;
        } else if (hasFavouriteBanks() && !hasAllBanks()) {
            return mFavouriteBanks.size() + 1;
        } else {
            return 0;
        }
    }

    public Object getItem(int position) {
        if (getItemViewType(position) == R.layout.item_bank) {
            return getBankItem(position);
        } else {
            return getSectionItem(position);
        }
    }

    private Bank getBankItem(int position) {
        if (hasFavouriteBanks() && position >= 0 && position <= mFavouriteBanks.size()) {
            return mFavouriteBanks.get(position - 1);
        } else if (hasFavouriteBanks() && hasAllBanks() && position >= mFavouriteBanks.size() + 1
                && position <= mFavouriteBanks.size() + mAllBanks.size() + 1) {
            return mAllBanks.get(position - 2 - mFavouriteBanks.size());
        } else if (!hasFavouriteBanks() && hasAllBanks() && position >= mFavouriteBanks.size() + 1
                && position <= mFavouriteBanks.size() + mAllBanks.size()) {
            return mAllBanks.get(position - 1);
        }
        return null;
    }

    private SectionItem getSectionItem(int position) {
        if (hasFavouriteBanks() && position == 0) {
            return new SectionItem("Favourite Banks");
        } else if (!hasFavouriteBanks() && hasAllBanks() && position == 0) {
            return new SectionItem("All Banks");
        } else if (hasFavouriteBanks() && hasAllBanks() && position == mFavouriteBanks.size() + 1) {
            return new SectionItem("All Banks");
        }
        return null;
    }

}
