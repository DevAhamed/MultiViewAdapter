package com.ahamed.multiviewadapter;

import android.databinding.ViewDataBinding;

/**
 * ViewHolder which supports the DataBinding
 *
 * @param <M> Refers to the model class
 * @param <VDB> Refers to the DataBinding class of the model
 */
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

  /**
   * @param binding Binding for the data model class
   * @param item Data model
   */
  public abstract void bind(VDB binding, M item);
}
