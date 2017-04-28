package com.ahamed.multiviewadapter;

import android.support.annotation.Nullable;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import java.util.ArrayList;
import java.util.List;

class BaseDataManager<M> implements ListUpdateCallback {

  private final RecyclerListAdapter listAdapter;
  private List<M> dataList = new ArrayList<>();
  private SparseBooleanArray selectedItems = new SparseBooleanArray();

  BaseDataManager(RecyclerListAdapter baseAdapter) {
    this.listAdapter = baseAdapter;
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

  /**
   * Called by the {@link BaseDataManager} when it wants to check whether two items have the same
   * data.
   * BaseDataManager uses this information to detect if the contents of an item has changed.
   * <p>
   * BaseDataManager uses this method to check equality instead of {@link Object#equals(Object)}
   * so that you can change its behavior depending on your UI.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list which replaces the oldItem
   * @return True if the contents of the items are the same or false if they are different, ie., you
   * should return whether the items' visual representations are the same.
   */
  public boolean areContentsTheSame(M oldItem, M newItem) {
    return oldItem.equals(newItem);
  }

  /**
   * Called by the {@link BaseDataManager} when it wants to get the payload of changed elements.
   * <p>
   * For example, if you are using DiffUtil with {@link RecyclerView}, you can return the
   * particular field that changed in the item and your
   * {@link android.support.v7.widget.RecyclerView.ItemAnimator ItemAnimator} can use that
   * information to run the correct animation.
   * <p>
   * Default implementation returns {@code null}.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list
   * @return A payload object that represents the change between the two items.
   */
  @SuppressWarnings("UnusedParameters") public Object getChangePayload(M oldItem, M newItem) {
    return null;
  }

  /**
   * This method is used to get the selected items in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}
   *
   * @return List of selected items or empty list
   */
  public List<M> getSelectedItems() {
    List<M> selectedItemsList = new ArrayList<>();
    for (int i = 0; i < size(); i++) {
      if (selectedItems.get(i)) {
        selectedItemsList.add(dataList.get(i));
      }
    }
    return selectedItemsList;
  }

  /**
   * This method is used to set the selected items in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}.
   * Exception will be thrown if calling {@link DataListManager} is not used in the {@link
   * SelectableAdapter}.
   */
  public void setSelectedItems(List<M> selectedItems) {
    if (!(listAdapter instanceof SelectableAdapter)) {
      throw new IllegalStateException(
          "Make sure your adapter extends from com.ahamed.multiviewadapter.SelectableAdapter");
    }
    if (size() < 0) {
      return;
    }
    SparseBooleanArray oldSelectedItems = this.selectedItems.clone();
    this.selectedItems = new SparseBooleanArray();
    int i = 0;
    for (M m : selectedItems) {
      boolean isSelected = contains(m);
      this.selectedItems.put(i, isSelected);
      if (oldSelectedItems.get(i, false) != isSelected) {
        onItemSelectionToggled(i, isSelected);
      }
      i++;
    }
  }

  /**
   * This method is used to get the selected item in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}
   *
   * @return Selected item or null
   */
  @Nullable public M getSelectedItem() {
    for (int i = 0; i < size(); i++) {
      if (selectedItems.get(i)) {
        return dataList.get(i);
      }
    }
    return null;
  }

  /**
   * This method is used to set the selected item in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}.
   * Exception will be thrown if calling {@link DataListManager} is not used in the {@link
   * SelectableAdapter}.
   *
   * @return List of selected items or empty list
   */
  public void setSelectedItem(M selectedItem) {
    if (!(listAdapter instanceof SelectableAdapter)) {
      throw new IllegalStateException(
          "Make sure your adapter extends from com.ahamed.multiviewadapter.SelectableAdapter");
    }
    if (size() < 0) {
      return;
    }
    M previousSelectedItem = getSelectedItem();
    int index = indexOf(selectedItem);
    if (index != -1) {
      this.selectedItems.put(index, true);
      onItemSelectionToggled(index, true);
      ((SelectableAdapter) listAdapter).setLastSelectedIndex(index);
    }
    if (null != previousSelectedItem && indexOf(previousSelectedItem) != -1) {
      onItemSelectionToggled(indexOf(previousSelectedItem), false);
    }
  }

  /**
   * Returns the number of elements in this data manager.  If this list contains
   * more than <tt>Integer.MAX_VALUE</tt> elements, returns
   * <tt>Integer.MAX_VALUE</tt>.
   *
   * @return the number of elements in this list
   */
  public final int getCount() {
    return size();
  }

  /**
   * Returns <tt>true</tt> if this data manager contains no elements.
   *
   * @return <tt>true</tt> if this data manager contains no elements
   */
  public final boolean isEmpty() {
    return dataList.isEmpty();
  }

  /**
   * Returns <tt>true</tt> if this data manager contains the specified element.
   * More formally, returns <tt>true</tt> if and only if this data manager contains
   * at least one element <tt>e</tt> such that
   * <tt>(item==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;item.equals(e))</tt>.
   *
   * @param item element whose presence in this list is to be tested
   * @return <tt>true</tt> if this list contains the specified element
   */
  public final boolean contains(M item) {
    return dataList.contains(item);
  }

  /**
   * Returns the element at the specified position in this data manager.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range
   * (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  public final M get(int index) {
    return dataList.get(index);
  }

  /**
   * Returns the index of the first occurrence of the specified element
   * in this data manager, or -1 if this data manager does not contain the element.
   * More formally, returns the lowest index <tt>i</tt> such that
   * <tt>(item==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;item.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param item element to search for
   * @return the index of the first occurrence of the specified element in
   * this data manager, or -1 if this data manager does not contain the element
   */
  public final int indexOf(M item) {
    return dataList.indexOf(item);
  }

  /**
   * Returns the index of the last occurrence of the specified element
   * in this list, or -1 if this data manager does not contain the element.
   * More formally, returns the highest index <tt>i</tt> such that
   * <tt>(item==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;item.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param item element to search for
   * @return the index of the last occurrence of the specified element in
   * this data manager, or -1 if this data manager does not contain the element
   */
  public final int lastIndexOf(M item) {
    return dataList.lastIndexOf(item);
  }

  ///////////////////////////////////////////
  /////////// Internal API ahead. ///////////
  ///////////////////////////////////////////

  void onItemSelectionToggled(int position, boolean isSelected) {
    selectedItems.put(position, isSelected);
    onChanged(position, 1, null);
  }

  List<M> getDataList() {
    return dataList;
  }

  void setDataList(List<M> dataList) {
    this.dataList = new ArrayList<>(dataList);
  }

  int size() {
    return dataList.size();
  }

  M getItem(int dataItemPosition) {
    return dataList.get(dataItemPosition);
  }

  boolean isItemSelected(int dataItemPosition) {
    return selectedItems.get(dataItemPosition);
  }
}