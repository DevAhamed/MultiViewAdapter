package com.ahamed.multiviewadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

public abstract class SelectableBinder<M, VH extends SelectableViewHolder<M>>
    extends BaseBinder<M, VH> {

  SelectableViewHolder.OnItemSelectedListener listener;

  void setListener(SelectableViewHolder.OnItemSelectedListener listener) {
    this.listener = listener;
  }

  @Override VH createViewHolder(LayoutInflater inflater, ViewGroup parent) {
    VH vh = create(inflater, parent);
    vh.setListener(listener);
    return vh;
  }

  public void bindViewHolder(VH holder, M item, boolean isSelected) {
    bind(holder, item, isSelected);
  }

  @Override public void bindViewHolder(VH holder, M item, boolean isSelected, List payloads) {
    bind(holder, item, isSelected);
  }

  @Override public final void bind(VH holder, M item) {
    // No-op - Only to finalize the method
  }

  @Override public final void bind(VH holder, M item, List payloads) {
    // No-op - Only to finalize the method
  }

  public void bind(VH holder, M item, List payloads, boolean isSelected) {
    bind(holder, item, isSelected);
  }

  public abstract void bind(VH holder, M item, boolean isSelected);
}