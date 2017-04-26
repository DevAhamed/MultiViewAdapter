package com.ahamed.multiviewadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

public abstract class SelectableBinder<M, VH extends SelectableViewHolder<M>>
    extends ItemBinder<M, VH> {

  private SelectableViewHolder.OnItemSelectedListener listener;

  public SelectableBinder() {
    super();
  }

  public SelectableBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  void setListener(SelectableViewHolder.OnItemSelectedListener listener) {
    this.listener = listener;
  }

  @Override VH createViewHolder(LayoutInflater inflater, ViewGroup parent) {
    VH vh = create(inflater, parent);
    vh.setItemSelectedListener(listener);
    return vh;
  }

  @Override void bindViewHolder(VH holder, M item, boolean isSelected) {
    bind(holder, item, isSelected);
  }

  @Override void bindViewHolder(VH holder, M item, boolean isSelected, List payloads) {
    bind(holder, item, isSelected);
  }

  @Override public final void bind(VH holder, M item) {
    // No-op - Only to finalize the method
  }

  @Override public final void bind(VH holder, M item, List payloads) {
    // No-op - Only to finalize the method
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
   * @see #bind(SelectableViewHolder, Object, boolean)
   */
  public void bind(VH holder, M item, List payloads, boolean isSelected) {
    bind(holder, item, isSelected);
  }

  /**
   * @param holder holder The ViewHolder which should be updated to represent the contents of the
   * item at the given position in the data set.
   * @param item The object which holds the data
   * @param isSelected Denotes whether the item is selected
   * @see #bind(SelectableViewHolder, Object, List, boolean)
   */
  public abstract void bind(VH holder, M item, boolean isSelected);
}