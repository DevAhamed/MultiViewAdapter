package com.ahamed.multiviewadapter;

import android.databinding.ViewDataBinding;

public abstract class DataBinder<M, VDB extends ViewDataBinding, VH extends BindingViewHolder<M, VDB>>
    extends ItemBinder<M, VH> {

  @Override public final void bind(VH holder, M item) {
    holder.bindModel(item);
  }
}
