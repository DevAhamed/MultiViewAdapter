package com.ahamed.multiviewadapter;

public final class DataItemManager<M> extends BaseDataManager<M> {

  public DataItemManager(RecyclerListAdapter baseAdapter) {
    super(baseAdapter);
  }

  public DataItemManager(RecyclerListAdapter baseAdapter, M item) {
    super(baseAdapter);
    getDataList().add(item);
  }

  public void setItem(M item) {
    if (getDataList().size() == 0) {
      getDataList().add(item);
      onInserted(0, 1);
    } else {
      getDataList().set(0, item);
      onChanged(0, 1, null);
    }
  }

  public void removeItem() {
    if (getDataList().size() > 0) {
      getDataList().clear();
      onRemoved(0, 1);
    }
  }
}