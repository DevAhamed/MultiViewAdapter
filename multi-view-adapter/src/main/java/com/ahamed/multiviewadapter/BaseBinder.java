package com.ahamed.multiviewadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

public abstract class BaseBinder<M, VH extends BaseViewHolder<M>> {

  public abstract VH create(LayoutInflater layoutInflater, ViewGroup parent);

  public abstract void bind(VH holder, M item);

  public abstract boolean canBindData(Object item);

  public void bind(VH holder, M item, List payloads) {
    bind(holder, item);
  }

  public int getSpanSize(int maxSpanCount) {
    return 1;
  }
}
