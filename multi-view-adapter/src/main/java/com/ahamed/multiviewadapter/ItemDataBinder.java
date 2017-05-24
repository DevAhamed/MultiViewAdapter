package com.ahamed.multiviewadapter;

import android.databinding.ViewDataBinding;

/**
 * {@link ItemBinder} which supports the DataBinding
 *
 * @param <M> Refers to the model class
 * @param <VDB> Refers to the view binding of the model class
 * @param <VH> Refers to the view holder which supports the data binding
 */
public abstract class ItemDataBinder<M, VDB extends ViewDataBinding, VH extends BindingViewHolder<M, VDB>>
    extends ItemBinder<M, VH> {

  @Override public final void bind(VH holder, M item) {
    holder.bindModel(item);
  }
}
