package com.ahamed.multiviewadapter;

import android.support.v7.widget.RecyclerView;

public final class DataItemManager<M> extends BaseDataManager<M> {

  public DataItemManager(RecyclerListAdapter baseAdapter) {
    super(baseAdapter);
  }

  public DataItemManager(RecyclerListAdapter baseAdapter, M item) {
    super(baseAdapter);
    getDataList().add(item);
  }

  /**
   * Adds item to the {@link DataItemManager}. This will call the necessary {@link
   * RecyclerView.ItemAnimator}'s animation.
   *
   * @param item item to be added to the
   */
  public void setItem(M item) {
    if (getDataList().size() == 0) {
      getDataList().add(item);
      onInserted(0, 1);
    } else {
      getDataList().set(0, item);
      onChanged(0, 1, null);
    }
  }

  /**
   * Removes the item from {@link DataItemManager}. This will call the {@link
   * RecyclerView.ItemAnimator}'s remove animation.
   */
  public void removeItem() {
    if (getDataList().size() > 0) {
      getDataList().clear();
      onRemoved(0, 1);
    }
  }
}