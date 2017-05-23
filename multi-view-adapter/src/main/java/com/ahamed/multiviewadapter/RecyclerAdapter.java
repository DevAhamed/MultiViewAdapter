package com.ahamed.multiviewadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.ahamed.multiviewadapter.annotation.ExpandableMode;
import com.ahamed.multiviewadapter.util.InfiniteLoadingHelper;

public class RecyclerAdapter extends CoreRecyclerAdapter {

  public ItemTouchHelper getItemTouchHelper() {
    if (null == itemTouchHelper) {
      ItemBinderTouchCallback itemBinderTouchCallback = new ItemBinderTouchCallback(this);
      itemTouchHelper = new ItemTouchHelper(itemBinderTouchCallback);
    }
    return itemTouchHelper;
  }

  /**
   * Sets the number of spans to be laid out.
   * <p>
   * Based on the orientation of your {@link GridLayoutManager}'s orientation it can represent rows
   * or columns.</p>
   *
   * @param maxSpanCount The total number of spans in the grid
   */
  public final void setSpanCount(int maxSpanCount) {
    this.maxSpanCount = maxSpanCount;
  }

  /**
   * Returns the current {@link GridLayoutManager.SpanSizeLookup} used by the {@link
   * RecyclerAdapter}.<p> Default implementation sets each item to occupy exactly 1 span.</p>
   *
   * @return The {@link GridLayoutManager.SpanSizeLookup} used by the {@link RecyclerAdapter}.
   */
  public final GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
    return spanSizeLookup;
  }

  public final ItemDecorationManager getItemDecorationManager() {
    return itemDecorationManager;
  }

  /**
   * To add the {@link DataListManager} to the {@link RecyclerAdapter}
   *
   * @param dataManager The DataManager to be added to {@link RecyclerAdapter}
   */
  protected final void addDataManager(BaseDataManager dataManager) {
    dataManagers.add(dataManager);
  }

  /**
   * To register the {@link ItemBinder} to the {@link RecyclerAdapter}
   *
   * @param binder The ItemBinder to be register with {@link RecyclerAdapter}
   */
  protected final void registerBinder(ItemBinder binder) {
    addBinder(binder);
  }

  /**
   * To set the selection mode for the {@link RecyclerAdapter}
   *
   * @param expandableMode The expansion mode to be set
   * @see ExpandableMode ExpandableMode for possible values
   */
  public final void setExpandableMode(@ExpandableMode int expandableMode) {
    this.expandableMode = expandableMode;
  }

  /**
   * To set the selection mode for the {@link SelectableAdapter}
   *
   * @param groupExpandableMode The expansion mode to be set
   * @see ExpandableMode ExpandableMode for possible values
   */
  public final void setGroupExpandableMode(@ExpandableMode int groupExpandableMode) {
    this.groupExpandableMode = groupExpandableMode;
  }

  public void setInfiniteLoadingHelper(InfiniteLoadingHelper infiniteLoadingHelper) {
    registerBinder(infiniteLoadingHelper.getItemBinder());
    DataItemManager<String> dataItemManager = new DataItemManager<>(this, "LoadingItem");
    addDataManager(dataItemManager);
    infiniteLoadingHelper.setDataItemManager(dataItemManager);
  }
}