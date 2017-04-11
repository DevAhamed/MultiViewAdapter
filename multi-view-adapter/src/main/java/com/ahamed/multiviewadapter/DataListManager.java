package com.ahamed.multiviewadapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import java.util.Collection;
import java.util.List;

public class DataListManager<M> extends BaseDataManager<M> {

  public DataListManager(RecyclerListAdapter baseAdapter) {
    super(baseAdapter);
  }

  public final boolean add(M item) {
    boolean result = getDataList().add(item);
    if (result) {
      onInserted(getDataList().size() - 1, 1);
    }
    return result;
  }

  public final boolean addAll(@NonNull Collection<? extends M> items) {
    return addAll(getDataList().size(), items);
  }

  public final boolean addAll(int index, @NonNull Collection<? extends M> items) {
    boolean result = getDataList().addAll(index, items);
    if (result) {
      onInserted(index, items.size());
    }
    return result;
  }

  public final void add(int index, M item) {
    getDataList().add(index, item);
    onInserted(index, 1);
  }

  public final void set(int position, M item) {
    M oldItem = getDataList().get(position);
    getDataList().set(position, item);
    onChanged(position, 1, getChangePayload(oldItem, item));
  }

  public final void set(List<M> dataList) {
    DiffUtil.DiffResult result =
        DiffUtil.calculateDiff(new DiffUtilCallback<M>(this.getDataList(), dataList) {
          @Override public boolean areContentsTheSame(M oldItem, M newItem) {
            return DataListManager.this.areContentsTheSame(oldItem, newItem);
          }

          @Override public Object getChangePayload(M oldItem, M newItem) {
            return DataListManager.this.getChangePayload(oldItem, newItem);
          }
        });
    setDataList(dataList);
    result.dispatchUpdatesTo(this);
  }

  public final void remove(M item) {
    int index = getDataList().indexOf(item);
    boolean result = getDataList().remove(item);
    if (result) {
      onRemoved(index, 1);
    }
  }

  public final void remove(int index) {
    if (index >= size()) {
      throw new IndexOutOfBoundsException();
    }
    getDataList().remove(index);
    onRemoved(index, 1);
  }

  public void clear() {
    if (size() <= 0) {
      return;
    }
    int oldSize = getDataList().size();
    getDataList().clear();
    onRemoved(0, oldSize);
  }
}