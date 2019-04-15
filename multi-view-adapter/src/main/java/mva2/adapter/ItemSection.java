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
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.decorator.PositionType;
import mva2.adapter.internal.ItemMetaData;
import mva2.adapter.internal.RecyclerItem;
import mva2.adapter.util.Mode;

/**
 * Section which displays a single item inside the RecyclerView. For example, ItemSection can be
 * used to display header or footer inside the recyclerview.
 *
 * @param <M> Model class for the item to be displayed
 */
@SuppressWarnings("ConstantConditions") public class ItemSection<M> extends Section {

  private M item;
  private ItemMetaData itemMetaData;

  /**
   * No-arg constructor for ItemSection. This sets the {@link ItemSection#item} as null. This
   * results the item not to be displayed in the recyclerview. Call {@link
   * ItemSection#setItem(Object)} to set the item.
   */
  @SuppressWarnings("WeakerAccess") public ItemSection() {
  }

  /**
   * Initializes the ItemSection with the item passed.
   *
   * @param item Item that needs to be set for this ItemSection.
   */
  public ItemSection(M item) {
    this.item = item;
    this.itemMetaData = new ItemMetaData();
  }

  /**
   * Returns the item added to this ItemSection. If no item was set, returns null.
   *
   * @return Item that was set, if no item was set then returns null
   */
  @Nullable public M getItem() {
    return item;
  }

  /**
   * Removes the item added to this section. This results in the ItemSection not being visible in
   * the recyclerview.
   */
  public void removeItem() {
    this.item = null;
    this.itemMetaData = null;
    onRemoved(0, 1);
  }

  /**
   * Sets the item for this section. If the item was already set, {@link
   * RecyclerView.Adapter#notifyItemChanged(int)} method is called internally or {@link
   * RecyclerView.Adapter#notifyItemInserted(int)}.
   *
   * If you are passing null, the method exits silently.
   *
   * @param item Item that needs to be set for this section.
   */
  public void setItem(@NonNull M item) {
    if (item == null) {
      return;
    }
    if (null == itemMetaData) {
      this.itemMetaData = new ItemMetaData();
      this.item = item;
      onInserted(0, 1);
    } else {
      this.item = item;
      onChanged(0, 1, null);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @Override Object getItem(int position) {
    return item;
  }

  @Override int getCount() {
    return isItemShowing() ? 1 : 0;
  }

  @Override boolean isItemSelected(int adapterPosition) {
    return itemMetaData.isSelected();
  }

  @Override void onItemSelectionToggled(int itemPosition, @NonNull Mode selectionMode) {
    if (itemPosition < getCount()) {
      Mode modeToHonor = getModeToHonor(selectionMode, this.selectionMode);
      if (modeToHonor == Mode.SINGLE && itemMetaData.isSelected()) {
        itemMetaData.setSelected(!itemMetaData.isSelected());
        onChanged(0, 1, null);
      } else if (itemPosition < getCount() && itemPosition >= 0) {
        itemMetaData.setSelected(!itemMetaData.isSelected());
        onChanged(0, 1, null);
      }
    }
  }

  @Override void clearAllSelections() {
    if (itemMetaData.isSelected()) {
      itemMetaData.setSelected(!itemMetaData.isSelected());
      if (isItemShowing()) {
        onChanged(0, 1, null);
      }
    }
  }

  @Override boolean isItemExpanded(int adapterPosition) {
    return itemMetaData.isExpanded();
  }

  @Override void onItemExpansionToggled(int itemPosition, @NonNull Mode selectionMode) {
    if (itemPosition < getCount()) {
      Mode modeToHonor = getModeToHonor(selectionMode, this.expansionMode);
      if (modeToHonor == Mode.SINGLE && itemMetaData.isExpanded()) {
        itemMetaData.setExpanded(!itemMetaData.isExpanded());
        onChanged(0, 1, null);
      } else if (itemPosition < getCount() && itemPosition >= 0) {
        itemMetaData.setExpanded(!itemMetaData.isExpanded());
        onChanged(0, 1, null);
      }
    }
  }

  @Override void collapseAllItems() {
    if (itemMetaData.isExpanded()) {
      itemMetaData.setExpanded(!itemMetaData.isExpanded());
      if (isItemShowing()) {
        onChanged(0, 1, null);
      }
    }
  }

  @Override int onSectionExpansionToggled(int itemPosition, @NonNull Mode sectionExpansionMode) {
    // ItemSection cannot be expanded!!
    return itemPosition - getCount();
  }

  @Override int getPositionType(int itemPosition, int adapterPosition,
      RecyclerView.LayoutManager layoutManager) {
    return PositionType.LEFT | PositionType.TOP | PositionType.BOTTOM | PositionType.RIGHT;
  }

  @Override void onItemDismiss(int itemPosition) {
    itemMetaData = null;
    item = null;
    onRemoved(itemPosition, 1);
  }

  @Override boolean move(int itemPosition, int targetOffset) {
    return false;
  }

  @Override RecyclerItem startMovingItem(int itemPosition) {
    RecyclerItem<M> recyclerItem = new RecyclerItem<>(item, itemMetaData);
    item = null;
    itemMetaData = null;
    return recyclerItem;
  }

  @Override void finishMovingItem(int currentPosition, RecyclerItem itemToMove) {
    //noinspection unchecked
    this.item = (M) itemToMove.getItem();
    this.itemMetaData = itemToMove.getItemMetaData();
  }

  private boolean isItemShowing() {
    return isSectionVisible() && item != null;
  }
}
