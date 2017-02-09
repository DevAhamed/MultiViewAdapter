package com.ahamed.multiviewadapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import java.util.List;

abstract class DiffUtilCallback<M> extends DiffUtil.Callback {

  private final List<M> oldList;
  private final List<M> newList;

  DiffUtilCallback(List<M> oldList, List<M> newList) {
    this.oldList = oldList;
    this.newList = newList;
  }

  @Override public int getOldListSize() {
    return oldList.size();
  }

  @Override public int getNewListSize() {
    return newList.size();
  }

  @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
  }

  @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
  }

  @Nullable @Override public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    Object payload = getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
    return payload != null ? payload : super.getChangePayload(oldItemPosition, newItemPosition);
  }

  public abstract boolean areContentsTheSame(M oldItem, M newItem);

  public abstract Object getChangePayload(M oldItem, M newItem);
}
