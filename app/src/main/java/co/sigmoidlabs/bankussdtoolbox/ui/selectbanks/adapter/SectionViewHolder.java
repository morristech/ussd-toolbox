package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks.adapter;

import android.support.v7.widget.RecyclerView;

import co.sigmoidlabs.bankussdtoolbox.databinding.ItemSectionHeaderBinding;

public class SectionViewHolder extends RecyclerView.ViewHolder {
    ItemSectionHeaderBinding binding;
    public SectionViewHolder(ItemSectionHeaderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.executePendingBindings();
    }
}
