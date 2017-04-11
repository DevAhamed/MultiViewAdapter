package com.ahamed.multiviewadapter;

import android.support.v7.util.ListUpdateCallback;
import java.util.ArrayList;
import java.util.List;

class BaseDataManager<M> implements ListUpdateCallback {

  private final RecyclerListAdapter listAdapter;
  private List<M> dataList = new ArrayList<>();

  BaseDataManager(RecyclerListAdapter baseAdapter) {
    this.listAdapter = baseAdapter;
  }

  int getCount() {
    return size();
  }

  M getItem(int position) {
    return dataList.get(position);
  }

  @Override public final void onInserted(int position, int count) {
    listAdapter.notifyBinderItemRangeInserted(this, position, count);
  }

  @Override public final void onRemoved(int position, int count) {
    listAdapter.notifyBinderItemRangeRemoved(this, position, count);
  }

  @Override public final void onMoved(int fromPosition, int toPosition) {
    listAdapter.notifyBinderItemMoved(this, fromPosition, toPosition);
  }

  @Override public final void onChanged(int position, int count, Object payload) {
    listAdapter.notifyBinderItemRangeChanged(this, position, count, payload);
  }

  public boolean areContentsTheSame(M oldItem, M newItem) {
    return oldItem.equals(newItem);
  }

  @SuppressWarnings("UnusedParameters") public Object getChangePayload(M oldItem, M newItem) {
    return null;
  }

  List<M> getDataList() {
    return dataList;
  }

  void setDataList(List<M> dataList) {
    this.dataList = dataList;
  }

  public final int size() {
    return dataList.size();
  }

  public final boolean isEmpty() {
    return dataList.isEmpty();
  }

  public final boolean contains(M item) {
    return dataList.contains(item);
  }

  public final M get(int index) {
    return dataList.get(index);
  }

  public final int indexOf(M item) {
    return dataList.indexOf(item);
  }

  public final int lastIndexOf(M item) {
    return dataList.lastIndexOf(item);
  }
}