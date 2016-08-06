package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import co.sigmoidlabs.bankussdtoolbox.databinding.ItemBankBinding;


class BankViewHolder extends RecyclerView.ViewHolder{

    public ItemBankBinding binding;

    public BankViewHolder(ItemBankBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.executePendingBindings();
    }
}
