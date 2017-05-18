package com.ahamed.multiviewadapter;

import android.databinding.ViewDataBinding;

public abstract class BindingViewHolder<M, VDB extends ViewDataBinding> extends BaseViewHolder<M> {

  private final VDB binding;

  public BindingViewHolder(VDB binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  void bindModel(M item) {
    bind(binding, item);
    binding.executePendingBindings();
  }

  public abstract void bind(VDB binding, M item);
}
