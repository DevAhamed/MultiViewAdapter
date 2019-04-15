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

package mva2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.SpanSizeLookup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import mva2.adapter.decorator.PositionType;
import mva2.adapter.internal.DiffUtilCallback;
import mva2.adapter.internal.ItemMetaData;
import mva2.adapter.internal.RecyclerItem;
import mva2.adapter.util.Mode;
import mva2.adapter.util.MvaDiffUtil;
import mva2.adapter.util.OnItemClickListener;
import mva2.adapter.util.OnSelectionChangedListener;
import mva2.adapter.util.PayloadProvider;
import mva2.adapter.util.SwipeToDismissListener;

import static mva2.adapter.decorator.PositionType.BOTTOM;
import static mva2.adapter.decorator.PositionType.LEFT;
import static mva2.adapter.decorator.PositionType.MIDDLE;
import static mva2.adapter.decorator.PositionType.RIGHT;
import static mva2.adapter.decorator.PositionType.TOP;
import static mva2.adapter.util.Mode.MULTIPLE;
import static mva2.adapter.util.Mode.SINGLE;

/**
 * Internally section uses {@link DiffUtil} to calculate the diff and calls the correct notify
 * method.
 */
public class ListSection<M> extends Section {

  private PayloadProvider<M> payloadProvider;
  private List<M> dataList;
  private List<ItemMetaData> metaDataList;
  private final ListUpdateCallback listUpdateCallback = new ListUpdateCallback() {
    @Override public void onInserted(int position, int count) {
      for (int i = position; i < position + count; i++) {
        metaDataList.add(i, new ItemMetaData());
      }
      ListSection.super.onInserted(position, count);
    }

    @Override public void onRemoved(int position, int count) {
      if (position + count > position) {
        metaDataList.subList(position, position + count).clear();
      }
      ListSection.super.onRemoved(position, count);
    }

    @Override public void onMoved(int fromPosition, int toPosition) {
      Collections.swap(metaDataList, fromPosition, toPosition);
      ListSection.super.onMoved(fromPosition, toPosition);
    }

    @Override public void onChanged(int position, int count, Object payload) {
      ListSection.super.onChanged(position, count, payload);
    }
  };
  private OnItemClickListener<M> onItemClickListener;
  private MvaDiffUtil<M> diffUtil = new MvaDiffUtil<M>() {
    @Override public void calculateDiff(ListUpdateCallback listUpdateCallback, List<M> oldList,
        List<M> newList) {
      DiffUtil.calculateDiff(new DiffUtilCallback<M>(oldList, newList) {
        @Override public boolean areItemsTheSame(M oldItem, M newItem) {
          return payloadProvider.areItemsTheSame(oldItem, newItem);
        }

        @Override public boolean areContentsTheSame(M oldItem, M newItem) {
          return payloadProvider.areContentsTheSame(oldItem, newItem);
        }

        @Override public Object getChangePayload(M oldItem, M newItem) {
          return payloadProvider.getChangePayload(oldItem, newItem);
        }
      }).dispatchUpdatesTo(listUpdateCallback);
    }
  };
  private SwipeToDismissListener<M> swipeToDismissListener;
  private OnSelectionChangedListener<M> onSelectionChangedListener;

  /**
   * No arg constructor for ListSection. This initializes the list section with default {@link
   * PayloadProvider} which checks the equality using {@code Object.equals(object)}
   */
  @SuppressWarnings("WeakerAccess") public ListSection() {
    this(new PayloadProvider<M>() {
      @Override public boolean areContentsTheSame(M oldItem, M newItem) {
        return oldItem.equals(newItem);
      }

      @Override public boolean areItemsTheSame(M oldItem, M newItem) {
        return oldItem.equals(newItem);
      }

      @Override public Object getChangePayload(M oldItem, M newItem) {
        return null;
      }
    });
  }

  /**
   * Constructor which initializes the ListSection with give PayloadProvider.
   *
   * @param payloadProvider PayloadProvider to be used by the list section.
   */
  @SuppressWarnings("WeakerAccess") public ListSection(PayloadProvider<M> payloadProvider) {
    dataList = new ArrayList<>();
    metaDataList = new ArrayList<>();
    this.payloadProvider = payloadProvider;
  }

  /**
   * Appends the specified element to the end of {@link ListSection}. Also calls {@link
   * RecyclerView.ItemAnimator}'s add animation.
   *
   * @param item element to be appended to this list
   *
   * @throws UnsupportedOperationException if the <tt>add</tt> operation is not supported by this
   *                                       {@link ListSection}
   */
  public void add(M item) {
    add(this.dataList.size(), item);
  }

  /**
   * Inserts the specified element at the specified position in this data manger
   * (optional operation).  Shifts the element currently at that position
   * (if any) and any subsequent elements to the right (adds one to their
   * indices). Also calls {@link
   * RecyclerView.ItemAnimator}'s add animation.
   *
   * @param index index at which the specified element is to be inserted
   * @param item  element to be inserted
   *
   * @throws UnsupportedOperationException if the <tt>add</tt> operation
   *                                       is not supported by this data manger
   * @throws IndexOutOfBoundsException     if the index is out of range
   *                                       (<tt>index &lt; 0 || index &gt; size()</tt>)
   */
  public void add(int index, M item) {
    add(index, item, isSectionVisible());
  }

  /**
   * Appends all of the elements in the specified collection to the end of this {@link ListSection},
   * in the order that they are returned by the specified collection's iterator (optional
   * operation). The behavior of this operation is undefined if the specified collection is
   * modified while the operation is in progress. (Note that this will occur if the specified
   * collection is this list, and it's nonempty) Also calls {@link RecyclerView.ItemAnimator}'s add
   * animation.
   *
   * @param items collection containing elements to be added to this list
   *
   * @return <tt>true</tt> if this list changed as a result of the call
   *
   * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
   *                                       is not supported by this {@link ListSection}
   * @see #add(Object)
   */
  public boolean addAll(@NonNull Collection<? extends M> items) {
    return addAll(this.dataList.size(), items);
  }

  /**
   * Inserts all of the elements in the specified collection into this
   * data manager at the specified position (optional operation).  Shifts the
   * element currently at that position (if any) and any subsequent
   * elements to the right (increases their indices).  The new elements
   * will appear in this list in the order that they are returned by the
   * specified collection's iterator.  The behavior of this operation is
   * undefined if the specified collection is modified while the
   * operation is in progress. Also calls {@link
   * RecyclerView.ItemAnimator}'s add animation.
   *
   * @param index index at which to insert the first element from the
   *              specified collection
   * @param items collection containing elements to be added to this list
   *
   * @return <tt>true</tt> if this data manager changed as a result of the call
   *
   * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
   *                                       is not supported by this data manager
   */
  public boolean addAll(int index, @NonNull Collection<? extends M> items) {
    return addAll(index, items, isSectionVisible());
  }

  /**
   * Removes all of the elements from this data manager (optional operation).
   * The data manager will be empty after this call returns.  After the elements are removed
   * {@link RecyclerView.ItemAnimator}'s remove animation will be called.
   */
  public void clear() {
    if (size() <= 0) {
      return;
    }
    int oldSize = dataList.size();
    dataList.clear();
    metaDataList.clear();
    if (isSectionVisible()) {
      onRemoved(0, oldSize);
    }
  }

  /**
   * This method is used to clear the selected items in a {@link ListSection}
   */
  public void clearSelections() {
    clearAllSelections();
  }

  /**
   * Returns the element at the specified position in this list section.
   *
   * @param index index of the element to return
   *
   * @return the element at the specified position in this list section
   *
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  public M get(int index) {
    return this.dataList.get(index);
  }

  /**
   * Returns the data added to this section
   *
   * @return List of models added to this section
   */
  public List<M> getData() {
    return new ArrayList<>(dataList);
  }

  /**
   * Returns the list of selected items
   *
   * @return List of selected items or empty list if none was selected
   */
  public List<M> getSelectedItems() {
    List<M> selectedItems = new ArrayList<>();
    int position = 0;
    for (ItemMetaData itemMetaData : metaDataList) {
      if (itemMetaData.isSelected()) {
        selectedItems.add(dataList.get(position));
      }
      position++;
    }
    return selectedItems;
  }

  /**
   * Removes the element at the specified position in this list (optional operation).  Shifts any
   * subsequent elements to the left (subtracts one from their indices). If the element is removed
   * {@link RecyclerView.ItemAnimator}'s remove animation will be called.
   *
   * @param index the index of the element to be removed
   *
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  public void remove(int index) {
    this.dataList.remove(index);
    this.metaDataList.remove(index);
    if (isSectionVisible()) {
      onRemoved(index, 1);
    }
  }

  /**
   * Replaces the data list in this data manager with the new list. Also calls necessary {@link
   * RecyclerView.ItemAnimator}'s animation with payload.
   *
   * @param items list to be stored in the data manager
   */
  public void set(List<M> items) {
    List<M> oldItems = new ArrayList<>(this.dataList);
    List<M> newItems = new ArrayList<>(items);
    this.dataList = new ArrayList<>(items);

    if (isSectionVisible()) {
      diffUtil.calculateDiff(listUpdateCallback, oldItems, newItems);
    }
  }

  /**
   * Replaces the element at the specified position in this data manager with the
   * specified element (optional operation). Also calls necessary {@link
   * RecyclerView.ItemAnimator}'s animation with payload.
   *
   * @param index index of the element to replace
   * @param item  element to be stored at the specified position
   *
   * @throws UnsupportedOperationException if the <tt>set</tt> operation
   *                                       is not supported by this data manager
   * @throws IndexOutOfBoundsException     if the index is out of range
   *                                       (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  public void set(int index, M item) {
    M oldItem = this.dataList.get(index);
    this.dataList.set(index, item);
    onChanged(index, 1, payloadProvider.getChangePayload(oldItem, item));
  }

  /**
   * Sets the diffutil for this section. The default diffutil used in the adapter takes leverage of
   * the payload provider of this section. If you want to customize the entire behaviour of
   * calculating the diff, create your own implementation of {@link MvaDiffUtil} and set it here
   *
   * @param diffUtil DiffUtil to be used by the section
   */
  public void setDiffUtil(MvaDiffUtil<M> diffUtil) {
    this.diffUtil = diffUtil;
  }

  /**
   * Set the listener to get callback when an item is clicked inside the section. To invoke this
   * listener,
   * you need to call {@link ItemViewHolder#onClick()}
   *
   * @param itemClickListener Listener to be set
   */
  public void setOnItemClickListener(OnItemClickListener<M> itemClickListener) {
    this.onItemClickListener = itemClickListener;
  }

  /**
   * Set listener to get callback when an item is selected or unselected
   *
   * @param selectionChangedListener Listener to be set
   */
  public void setOnSelectionChangedListener(
      OnSelectionChangedListener<M> selectionChangedListener) {
    this.onSelectionChangedListener = selectionChangedListener;
  }

  /**
   * Set your own payload provider. You can write your own implementation of the {@link
   * PayloadProvider}  and set it here.
   *
   * @param payloadProvider PayloadProvider to be set
   */
  public void setPayloadProvider(PayloadProvider<M> payloadProvider) {
    this.payloadProvider = payloadProvider;
  }

  /**
   * Sets the SwipeToDismissListener
   *
   * @param swipeToDismissListener Listener to be set
   *
   * @see SwipeToDismissListener
   */
  public void setSwipeToDismissListener(SwipeToDismissListener<M> swipeToDismissListener) {
    this.swipeToDismissListener = swipeToDismissListener;
  }

  /**
   * Removes all of the elements from this data manager (optional operation).
   * The data manager will be empty after this call returns.  After the elements are removed
   * {@link RecyclerView.ItemAnimator}'s remove animation will be called.
   */
  public int size() {
    return dataList.size();
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @Override void onItemClicked(int position) {
    if (null != onItemClickListener) {
      onItemClickListener.onItemClicked(position, get(position));
    }
  }

  @Override Object getItem(int position) {
    return dataList.get(position);
  }

  @Override int getCount() {
    return isSectionVisible() ? dataList.size() : 0;
  }

  @Override boolean isItemSelected(int adapterPosition) {
    return metaDataList.get(adapterPosition).isSelected();
  }

  @Override void onItemSelectionToggled(int itemPosition, @NonNull Mode selectionMode) {
    Mode selectionModeToHonor =
        itemPosition < getCount() && itemPosition >= 0 ? getModeToHonor(selectionMode,
            this.selectionMode) : selectionMode;
    if (selectionModeToHonor == SINGLE) {
      toggleCurrentSelection(itemPosition);
    } else if (selectionModeToHonor == MULTIPLE) {
      toggleItemSelection(itemPosition);
    }
  }

  @Override void clearAllSelections() {
    int itemPosition = 0;
    for (ItemMetaData itemMetaData : metaDataList) {
      if (itemMetaData.isSelected()) {
        itemMetaData.setSelected(!itemMetaData.isSelected());
        onChanged(itemPosition++, 1, null);
      }
    }
  }

  @Override boolean isItemExpanded(int adapterPosition) {
    return metaDataList.get(adapterPosition).isExpanded();
  }

  @Override void onItemExpansionToggled(int itemPosition, @NonNull Mode expansionMode) {
    Mode expansionModeToHonor =
        itemPosition < getCount() && itemPosition >= 0 ? getModeToHonor(expansionMode,
            this.expansionMode) : expansionMode;
    if (expansionModeToHonor == SINGLE) {
      toggleCurrentExpansion(itemPosition);
    } else if (expansionModeToHonor == MULTIPLE) {
      toggleItemExpansion(itemPosition);
    }
  }

  @Override void collapseAllItems() {
    int adapterPosition = 0;
    for (ItemMetaData itemMetaData : metaDataList) {
      if (itemMetaData.isExpanded()) {
        itemMetaData.setExpanded(!itemMetaData.isExpanded());
        onChanged(adapterPosition, 1, null);
      }
      adapterPosition++;
    }
  }

  @Override int onSectionExpansionToggled(int itemPosition, @NonNull Mode sectionExpansionMode) {
    // ListSection can not be expanded directly!!
    return itemPosition - getCount();
  }

  @Override int getPositionType(int itemPosition, int adapterPosition,
      LayoutManager layoutManager) {
    return layoutManager instanceof GridLayoutManager ? getPositionTypeGrid(itemPosition,
        adapterPosition, (GridLayoutManager) layoutManager)
        : getPositionTypeLinear(itemPosition, layoutManager);
  }

  @Override void onItemDismiss(int itemPosition) {
    M m = dataList.remove(itemPosition);
    metaDataList.remove(itemPosition);
    onRemoved(itemPosition, 1);
    if (null != swipeToDismissListener) {
      swipeToDismissListener.onItemDismissed(itemPosition, m);
    }
  }

  @Override boolean move(int itemPosition, int targetOffset) {
    int offset = targetOffset > 0 ? 1 : -1;
    int currentPosition = itemPosition;
    for (int i = 0; i < targetOffset * offset; i++, currentPosition += offset) {
      Collections.swap(dataList, currentPosition, currentPosition + offset);
    }
    listUpdateCallback.onMoved(itemPosition, itemPosition + targetOffset);
    return false;
  }

  @Override RecyclerItem startMovingItem(int itemPosition) {
    return new RecyclerItem<>(dataList.remove(itemPosition), metaDataList.remove(itemPosition));
  }

  @Override void finishMovingItem(int currentPosition, RecyclerItem itemToMove) {
    //noinspection unchecked
    dataList.add(currentPosition, (M) itemToMove.getItem());
    metaDataList.add(currentPosition, itemToMove.getItemMetaData());
  }

  private void add(int index, @NonNull M item, boolean notifyChanges) {
    this.dataList.add(index, item);
    this.metaDataList.add(index, new ItemMetaData());
    if (notifyChanges) {
      onInserted(index, 1);
    }
  }

  private boolean addAll(int index, @NonNull Collection<? extends M> items,
      boolean notifyDataSetChanged) {
    boolean result = this.dataList.addAll(index, items);
    if (result) {
      for (int i = index; i < items.size() + index; i++) {
        metaDataList.add(new ItemMetaData());
      }
      if (notifyDataSetChanged) {
        onInserted(index, items.size());
      }
    }
    return result;
  }

  private @PositionType int getPositionTypeGrid(final int itemPosition, final int adapterPosition,
      GridLayoutManager layoutManager) {
    boolean isReverseLayout = isReverseLayout(layoutManager);
    boolean isVertical = isVertical(layoutManager);

    final int maxSpanCount = layoutManager.getSpanCount();
    SpanSizeLookup spanSizeLookup = (SpanSizeLookup) layoutManager.getSpanSizeLookup();

    int itemPositionType = MIDDLE;
    int positionOffset = adapterPosition - itemPosition;

    int currentSpanIndex = spanSizeLookup.getCachedSpanIndex(adapterPosition, maxSpanCount);
    int currentSpan = spanSizeLookup.getSpanSize(adapterPosition);

    boolean isLeft = currentSpanIndex == 0;
    boolean isRight = currentSpanIndex + currentSpan == maxSpanCount;
    int currentGroupIndex = spanSizeLookup.getSpanGroupIndex(adapterPosition, maxSpanCount);
    boolean isTop = itemPosition == 0 || (itemPosition <= maxSpanCount && (!isLeft
        && (currentGroupIndex - spanSizeLookup.getSpanGroupIndex(positionOffset, maxSpanCount))
        == 0));
    boolean isBottom = true;

    if ((maxSpanCount - currentSpanIndex - currentSpan) < (getCount() - itemPosition - 1)) {
      isBottom = false;
    } else {
      for (int looper = adapterPosition + 1; looper < (positionOffset + getCount()); looper++) {
        int groupIndex = spanSizeLookup.getSpanGroupIndex(looper, maxSpanCount);
        if (currentGroupIndex < groupIndex) {
          isBottom = false;
          break;
        }
      }
    }

    if (isLeft) {
      itemPositionType |= isVertical ? LEFT : TOP;
    }

    if (isRight) {
      itemPositionType |= isVertical ? RIGHT : BOTTOM;
    }

    if (isTop) {
      itemPositionType |=
          isVertical ? (isReverseLayout ? BOTTOM : TOP) : (isReverseLayout ? RIGHT : LEFT);
    }

    if (isBottom) {
      itemPositionType |=
          isVertical ? (isReverseLayout ? TOP : BOTTOM) : (isReverseLayout ? LEFT : RIGHT);
    }

    return itemPositionType;
  }

  private @PositionType int getPositionTypeLinear(int itemPosition, LayoutManager layoutManager) {
    int positionType = MIDDLE;
    boolean isReverseLayout = isReverseLayout(layoutManager);
    boolean isVertical = isVertical(layoutManager);
    boolean isFirstItem = isReverseLayout ? getCount() - 1 == itemPosition : itemPosition == 0;
    if (isFirstItem) {
      positionType |= isVertical ? TOP : LEFT;
    }
    boolean isLastItem = isReverseLayout ? itemPosition == 0 : getCount() - 1 == itemPosition;
    if (isLastItem) {
      positionType |= isVertical ? BOTTOM : RIGHT;
    }

    // For linear layout manager item will be on both edges
    positionType |= isVertical ? LEFT : TOP;
    positionType |= isVertical ? RIGHT : BOTTOM;

    return positionType;
  }

  private void toggleCurrentExpansion(int itemPosition) {
    int count = 0;
    for (ItemMetaData itemMetaData : metaDataList) {
      if (count == itemPosition) {
        if (itemMetaData.isExpanded()) {
          itemMetaData.setExpanded(!itemMetaData.isExpanded());
          onChanged(count, 1, null);
          break;
        } else {
          itemMetaData.setExpanded(!itemMetaData.isExpanded());
          onChanged(count, 1, null);
        }
      } else if (itemMetaData.isExpanded()) {
        itemMetaData.setExpanded(false);
        onChanged(count, 1, null);
        if (itemPosition < 0) {
          break;
        }
      }
      count++;
    }
  }

  private void toggleCurrentSelection(int itemPosition) {
    int count = 0;
    for (ItemMetaData itemMetaData : metaDataList) {
      if (count == itemPosition) {
        if (itemMetaData.isSelected()) {
          itemMetaData.setSelected(!itemMetaData.isSelected());
          onChanged(count, 1, null);
          notifySelectionChanged(count);
          break;
        } else {
          itemMetaData.setSelected(!itemMetaData.isSelected());
          onChanged(count, 1, null);
          notifySelectionChanged(count);
          itemPosition = -1;
        }
      } else if (itemMetaData.isSelected()) {
        itemMetaData.setSelected(false);
        onChanged(count, 1, null);
        notifySelectionChanged(count);
      }
      count++;
    }
  }

  private void toggleItemExpansion(int itemPosition) {
    if (itemPosition < getCount() && itemPosition >= 0) {
      ItemMetaData itemMetaData = metaDataList.get(itemPosition);
      itemMetaData.setExpanded(!itemMetaData.isExpanded());
      onChanged(itemPosition, 1, null);
    }
  }

  private void toggleItemSelection(int itemPosition) {
    if (itemPosition < getCount() && itemPosition >= 0) {
      ItemMetaData itemMetaData = metaDataList.get(itemPosition);
      itemMetaData.setSelected(!itemMetaData.isSelected());
      onChanged(itemPosition, 1, null);
      notifySelectionChanged(itemPosition);
    }
  }

  private void notifySelectionChanged(int itemPosition) {
    if (onSelectionChangedListener != null) {
      onSelectionChangedListener.onSelectionChanged(dataList.get(itemPosition),
          metaDataList.get(itemPosition).isSelected(), getSelectedItems());
    }
  }
}
