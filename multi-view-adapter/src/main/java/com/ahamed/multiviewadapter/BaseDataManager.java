/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ahamed.multiviewadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.util.ListUpdateCallback;
import com.ahamed.multiviewadapter.listener.ItemSelectionChangedListener;
import com.ahamed.multiviewadapter.listener.MultiSelectionChangedListener;
import java.util.ArrayList;
import java.util.List;

class BaseDataManager<M> implements ListUpdateCallback {

  final RecyclerAdapter adapter;
  private List<M> dataList = new ArrayList<>();
  private List<M> selectedItems = new ArrayList<>();
  private ItemSelectionChangedListener<M> itemSelectionChangedListener;
  private MultiSelectionChangedListener<M> multiSelectionChangedListener;

  BaseDataManager(@NonNull RecyclerAdapter adapter) {
    this.adapter = adapter;
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onInserted(int position, int count) {
    adapter.notifyBinderItemRangeInserted(this, position, count);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onRemoved(int position, int count) {
    adapter.notifyBinderItemRangeRemoved(this, position, count);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onMoved(int fromPosition, int toPosition) {
    adapter.notifyBinderItemMoved(this, fromPosition, toPosition);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onChanged(int position, int count, Object payload) {
    adapter.notifyBinderItemRangeChanged(this, position, count, payload);
  }

  /**
   * This method is used to get the selected items in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}
   *
   * @return List of selected items or empty list
   */
  public final List<M> getSelectedItems() {
    List<M> selectedItemsList = new ArrayList<>();
    for (M m : selectedItems) {
      if (contains(m)) {
        selectedItemsList.add(m);
      }
    }
    selectedItems = selectedItemsList;
    return selectedItemsList;
  }

  /**
   * This method is used to set the selected items in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}.
   * Exception will be thrown if calling {@link DataListManager} is not used in the {@link
   * SelectableAdapter}.
   *
   * @param selectedItems List of selected items
   */
  public final void setSelectedItems(@NonNull List<M> selectedItems) {
    if (!(adapter instanceof SelectableAdapter)) {
      throw new IllegalStateException(
          "Make sure your adapter extends from com.ahamed.multiviewadapter.SelectableAdapter");
    }
    if (size() < 0) {
      return;
    }
    List<M> oldSelectedItems = new ArrayList<>(this.selectedItems);
    this.selectedItems.clear();
    for (M m : selectedItems) {
      int index = indexOf(m);
      if (!oldSelectedItems.contains(m)) {
        onItemSelectionToggled(index, true);
      } else {
        this.selectedItems.add(m);
      }
    }
    for (M m : oldSelectedItems) {
      int index = indexOf(m);
      if (!selectedItems.contains(m)) {
        onItemSelectionToggled(index, false);
      }
    }
  }

  /**
   * This method is used to clear the selected items in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}.
   * Exception will be thrown if calling {@link DataListManager} is not used in the {@link
   * SelectableAdapter}.
   */
  public final void clearSelectedItems() {
    if (!(adapter instanceof SelectableAdapter)) {
      throw new IllegalStateException(
          "Make sure your adapter extends from com.ahamed.multiviewadapter.SelectableAdapter");
    }
    if (size() < 0) {
      return;
    }
    this.selectedItems.clear();
    onChanged(0, size(), null);
  }

  /**
   * This method is used to get the selected item in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}
   *
   * @return Selected item or null
   */
  @Nullable public final M getSelectedItem() {
    if (selectedItems.size() > 0) {
      return selectedItems.get(0);
    }
    return null;
  }

  /**
   * This method is used to set the selected item in a {@link DataListManager} or {@link
   * DataItemManager}. It should be used in conjunction with the {@link SelectableAdapter}.
   * Exception will be thrown if calling {@link DataListManager} is not used in the {@link
   * SelectableAdapter}.
   *
   * @param selectedItem Selected item
   */
  public final void setSelectedItem(@NonNull M selectedItem) {
    if (!(adapter instanceof SelectableAdapter)) {
      throw new IllegalStateException(
          "Make sure your adapter extends from com.ahamed.multiviewadapter.SelectableAdapter");
    }
    if (size() < 0) {
      return;
    }
    M previousSelectedItem = getSelectedItem();
    int index = indexOf(selectedItem);
    if (index != -1 && !selectedItem.equals(previousSelectedItem)) {
      onItemSelectionToggled(index, true);
    }
    if (null != previousSelectedItem && !previousSelectedItem.equals(selectedItem)) {
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
    return getItem(index);
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

  /**
   * Set listener to get notification when the selection changes
   *
   * @param itemSelectionChangedListener Listener for notify selection changes
   */
  public final void setItemSelectionChangedListener(
      ItemSelectionChangedListener<M> itemSelectionChangedListener) {
    this.itemSelectionChangedListener = itemSelectionChangedListener;
  }

  /**
   * Set listener to get notification when the selection changes
   *
   * @param multiSelectionChangedListener Listener for notify selection changes
   */
  public final void setMultiSelectionChangedListener(
      MultiSelectionChangedListener<M> multiSelectionChangedListener) {
    this.multiSelectionChangedListener = multiSelectionChangedListener;
  }

  ///////////////////////////////////////////
  /////////// Internal API ahead. ///////////
  ///////////////////////////////////////////

  void onItemSelectionToggled(int position, boolean isSelected) {
    if (isSelected) {
      selectedItems.add(get(position));
    } else {
      selectedItems.remove(get(position));
    }
    onChanged(position, 1, null);
    if (adapter instanceof SelectableAdapter && (itemSelectionChangedListener != null
        || multiSelectionChangedListener != null)) {
      SelectableAdapter adapter = (SelectableAdapter) this.adapter;
      switch (adapter.getSelectionMode()) {
        case SelectableAdapter.SELECTION_MODE_MULTIPLE:
          if (null != multiSelectionChangedListener) {
            multiSelectionChangedListener.onMultiSelectionChangedListener(getSelectedItems());
          }
          break;
        case SelectableAdapter.SELECTION_MODE_SINGLE:
        case SelectableAdapter.SELECTION_MODE_SINGLE_OR_NONE:
          if (null != itemSelectionChangedListener) {
            itemSelectionChangedListener.onItemSelectionChangedListener(get(position), isSelected);
          }
          break;
        case SelectableAdapter.SELECTION_MODE_NONE:
          break;
      }
    }
  }

  void onItemExpansionToggled(int position) {
    onChanged(position, 1, null);
  }

  void onGroupExpansionToggled() {
    // Do nothing. Should be overridden by GroupDataManager
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
    return selectedItems.contains(get(dataItemPosition));
  }

  void onSwapped(int currentPosition, int targetPosition) {
    M item = dataList.get(currentPosition);
    dataList.remove(currentPosition);
    dataList.add(targetPosition, item);
    onMoved(currentPosition, targetPosition);
  }

  int getSelectedIndex() {
    if (selectedItems.size() > 0 && contains(selectedItems.get(0))) {
      return indexOf(selectedItems.get(0));
    }
    selectedItems.clear();
    return -1;
  }
}