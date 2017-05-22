package com.ahamed.multiviewadapter;

import com.ahamed.multiviewadapter.util.ItemDecorator;
import java.util.List;

@Deprecated public abstract class SelectableBinder<M, VH extends BaseViewHolder<M>>
    extends ItemBinder<M, VH> {

  public SelectableBinder() {
    super();
  }

  public SelectableBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override void bindViewHolder(VH holder, M item, boolean isSelected) {
    bind(holder, item, holder.isItemSelected());
  }

  @Override void bindViewHolder(VH holder, M item, boolean isSelected, List payloads) {
    bind(holder, item, isSelected);
  }

  ////////////////////////////////////////
  ///////// Public Methods ///////////////
  ////////////////////////////////////////

  /**
   * @param holder holder The ViewHolder which should be updated to represent the contents of the
   * item at the given position in the data set.
   * @param item The object which holds the data
   * @param payloads A non-null list of merged payloads. Can be empty list if requires full
   * update.
   * @param isSelected Denotes whether the item is selected
   * @see #bind(BaseViewHolder, Object, boolean)
   */
  public void bind(VH holder, M item, List payloads, boolean isSelected) {
    bind(holder, item, isSelected);
  }

  /**
   * @param holder holder The ViewHolder which should be updated to represent the contents of the
   * item at the given position in the data set.
   * @param item The object which holds the data
   * @param isSelected Denotes whether the item is selected
   * @see #bind(BaseViewHolder, Object, List, boolean)
   */
  public abstract void bind(VH holder, M item, boolean isSelected);
}