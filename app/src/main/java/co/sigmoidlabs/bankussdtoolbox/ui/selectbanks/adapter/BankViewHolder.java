package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks.adapter;

import android.support.v7.widget.RecyclerView;

import co.sigmoidlabs.bankussdtoolbox.databinding.ItemBankBinding;


public class BankViewHolder extends RecyclerView.ViewHolder{

    public ItemBankBinding binding;
    public BankViewHolder(ItemBankBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.executePendingBindings();
    }
}
