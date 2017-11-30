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

import android.support.annotation.RestrictTo;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.annotation.ExpandableMode;
import com.ahamed.multiviewadapter.listener.ItemActionListener;
import java.util.ArrayList;
import java.util.List;

import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE;
import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_NONE;
import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_SINGLE;

class CoreRecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder> {

  final List<BaseDataManager> dataManagers = new ArrayList<>();
  final ItemDecorationManager itemDecorationManager;
  final List<ItemBinder> binders = new ArrayList<>();
  final SparseBooleanArray expandedItems = new SparseBooleanArray();
  int maxSpanCount = 1;
  final GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
    @Override public int getSpanSize(int position) {
      return getBinderForPosition(position).getSpanSize(maxSpanCount);
    }
  };
  ItemTouchHelper itemTouchHelper;
  boolean isInActionMode = false;
  @ExpandableMode int expandableMode = EXPANDABLE_MODE_NONE;
  @ExpandableMode int groupExpandableMode = EXPANDABLE_MODE_NONE;
  private int lastExpandedIndex = -1;
  final ItemActionListener actionListener = new ItemActionListener() {

    @Override public void onItemSelectionToggled(int position) {
      CoreRecyclerAdapter.this.onItemSelectionToggled(position);
    }

    @Override public void onItemExpansionToggled(int position) {
      CoreRecyclerAdapter.this.onItemExpansionToggled(position);
    }

    @Override public void onGroupExpansionToggled(int position) {
      CoreRecyclerAdapter.this.onGroupExpansionToggled(position);
    }

    @Override public boolean isItemSelected(int position) {
      return CoreRecyclerAdapter.this.isItemSelected(position);
    }

    @Override public boolean isItemExpanded(int position) {
      return CoreRecyclerAdapter.this.itemExpanded(position);
    }

    @Override public boolean isAdapterInActionMode() {
      return isInActionMode;
    }

    @Override public void onStartDrag(ItemViewHolder viewHolder) {
      itemTouchHelper.startDrag(viewHolder);
    }
  };

  CoreRecyclerAdapter() {
    itemDecorationManager = new ItemDecorationManager(this);
  }

  private boolean itemExpanded(int position) {
    return expandedItems.get(position, false);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return binders.get(viewType)
        .createViewHolder(LayoutInflater.from(parent.getContext()), parent, actionListener);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onBindViewHolder(ItemViewHolder holder, int adapterPosition) {
    onBindViewHolder(holder, adapterPosition, null);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onBindViewHolder(ItemViewHolder holder, int adapterPosition,
      List<Object> payloads) {
    ItemBinder baseBinder = binders.get(holder.getItemViewType());

    int totalCount = 0;
    for (BaseDataManager dataManager : dataManagers) {
      totalCount += dataManager.getCount();
      if (adapterPosition < totalCount) {
        int itemPosition = getItemPositionInManager(adapterPosition);
        if (dataManager instanceof DataGroupManager) {
          dataManager = ((DataGroupManager) dataManager).getDataManagerForPosition(itemPosition);
        }
        //noinspection unchecked
        holder.setItem(dataManager.getItem(itemPosition));
        break;
      }
    }

    if (null == payloads || payloads.size() == 0) {
      //noinspection unchecked
      baseBinder.bindViewHolder(holder, holder.getItem());
    } else {
      //noinspection unchecked
      baseBinder.bindViewHolder(holder, holder.getItem(), payloads);
    }
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override public final int getItemCount() {
    int itemCount = 0;
    for (BaseDataManager dataManager : dataManagers) {
      itemCount += dataManager.size();
    }
    return itemCount;
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final int getItemViewType(int adapterPosition) {
    ItemBinder baseBinder = getBinderForPosition(adapterPosition);
    if (null != baseBinder) {
      return binders.indexOf(baseBinder);
    }
    return super.getItemViewType(adapterPosition);
  }

  ItemBinder getBinderForPosition(int adapterPosition) {
    BaseDataManager dataManager = getDataManager(adapterPosition);
    for (ItemBinder baseBinder : binders) {
      if (baseBinder.canBindData(dataManager.getItem(getItemPositionInManager(adapterPosition)))) {
        return baseBinder;
      }
    }
    throw new IllegalStateException("Binder not found for position. Position = " + adapterPosition);
  }

  int getItemPositionInManager(int adapterPosition) {
    int itemCount;
    for (BaseDataManager dataManager : dataManagers) {
      itemCount = dataManager.getCount();
      if (adapterPosition - itemCount < 0) {
        break;
      }
      adapterPosition -= itemCount;
    }
    // adapterPosition now refers to position in manager
    return adapterPosition;
  }

  BaseDataManager getDataManager(int adapterPosition) {
    BaseDataManager dataManager = justGetDataManager(adapterPosition);
    if (dataManager instanceof DataGroupManager) {
      return ((DataGroupManager) dataManager).getDataManagerForPosition(
          getItemPositionInManager(adapterPosition));
    } else {
      return dataManager;
    }
  }

  BaseDataManager justGetDataManager(int adapterPosition) {
    int processedCount = 0;
    for (BaseDataManager dataManager : dataManagers) {
      processedCount += dataManager.getCount();
      if (adapterPosition < processedCount) {
        return dataManager;
      }
    }
    throw new IllegalStateException("Invalid position for DataManager!");
  }

  int getPositionInAdapter(BaseDataManager dataManager, int binderPosition) {
    int dataManagerIndex = dataManagers.indexOf(dataManager);
    if (dataManagerIndex < 0) {
      throw new IllegalStateException("DataManager does not exist in adapter");
    }

    int position = binderPosition;
    for (int i = 0; i < dataManagerIndex; i++) {
      position += dataManagers.get(i).getCount();
    }
    return position;
  }

  boolean isItemSelected(int adapterPosition) {
    return getDataManager(adapterPosition).isItemSelected(
        getItemPositionInManager(adapterPosition));
  }

  final void notifyBinderItemRangeChanged(BaseDataManager dataManager, int positionStart,
      int itemCount, Object payload) {
    notifyItemRangeChanged(getPositionInAdapter(dataManager, positionStart), itemCount, payload);
  }

  final void notifyBinderItemMoved(BaseDataManager dataManager, int fromPosition, int toPosition) {
    notifyItemMoved(getPositionInAdapter(dataManager, fromPosition),
        getPositionInAdapter(dataManager, toPosition));
  }

  final void notifyBinderItemRangeInserted(BaseDataManager dataManager, int positionStart,
      int itemCount) {
    notifyItemRangeInserted(getPositionInAdapter(dataManager, positionStart), itemCount);
  }

  final void notifyBinderItemRangeRemoved(BaseDataManager dataManager, int positionStart,
      int itemCount) {
    notifyItemRangeRemoved(getPositionInAdapter(dataManager, positionStart), itemCount);
  }

  boolean isLastItemInManager(int adapterPosition, int itemPosition) {
    return getDataManager(adapterPosition).size() - 1 == itemPosition;
  }

  void onItemSelectionToggled(int position) {
    // Do nothing. Should be handled by SelectableAdapter
  }

  private void onItemExpansionToggled(int adapterPosition) {
    switch (expandableMode) {
      case EXPANDABLE_MODE_SINGLE:
        if (lastExpandedIndex != -1) {
          expandedItems.put(lastExpandedIndex, false);
          getDataManager(lastExpandedIndex).onItemExpansionToggled(
              getItemPositionInManager(lastExpandedIndex));
        }
        if (lastExpandedIndex == adapterPosition) {
          lastExpandedIndex = -1;
          return;
        }
        expandedItems.put(adapterPosition, true);
        getDataManager(adapterPosition).onItemExpansionToggled(
            getItemPositionInManager(adapterPosition));
        lastExpandedIndex = adapterPosition;
        break;
      case EXPANDABLE_MODE_MULTIPLE:
        expandedItems.put(adapterPosition, !expandedItems.get(adapterPosition, false));
        getDataManager(adapterPosition).onItemExpansionToggled(
            getItemPositionInManager(adapterPosition));
        break;
      case EXPANDABLE_MODE_NONE:
      default:
        break;
    }
  }

  private void onGroupExpansionToggled(int adapterPosition) {
    switch (groupExpandableMode) {
      case EXPANDABLE_MODE_SINGLE:
        if (lastExpandedIndex == adapterPosition) {
          return;
        }
        BaseDataManager dataMangerToExpand = justGetDataManager(adapterPosition);
        if (lastExpandedIndex != -1) {
          for (BaseDataManager dataManager : dataManagers) {
            if (dataManager instanceof DataGroupManager
                && ((DataGroupManager) dataManager).isExpanded()) {
              dataManager.onGroupExpansionToggled();
              break;
            }
          }
        }
        dataMangerToExpand.onGroupExpansionToggled();
        lastExpandedIndex = adapterPosition;
        break;
      case EXPANDABLE_MODE_MULTIPLE:
        expandedItems.put(adapterPosition, !expandedItems.get(adapterPosition, false));
        justGetDataManager(adapterPosition).onGroupExpansionToggled();
        break;
      case EXPANDABLE_MODE_NONE:
      default:
        break;
    }
  }

  void onItemDismiss(int adapterPosition) {
    BaseDataManager baseDataManager = getDataManager(adapterPosition);
    if (baseDataManager instanceof DataListManager) {
      ((DataListManager) baseDataManager).onSwiped(getItemPositionInManager(adapterPosition));
    } else if (baseDataManager instanceof DataItemManager) {
      ((DataItemManager) baseDataManager).removeItem();
    }
  }

  void onMove(int currentPosition, int targetPosition) {
    if (currentPosition == -1) {
      return;
    }
    BaseDataManager dataManager = getDataManager(currentPosition);
    BaseDataManager targetDataManager = getDataManager(targetPosition);
    int currentPositionInManager = getItemPositionInManager(currentPosition);
    int targetPositionInManager = getItemPositionInManager(targetPosition);
    if (dataManager.equals(targetDataManager)) {
      dataManager.onSwapped(currentPositionInManager, targetPositionInManager);
    } else {
      Object obj = dataManager.getItem(currentPositionInManager);
      ((DataListUpdateManager) dataManager).remove(currentPositionInManager, false);
      //noinspection unchecked
      ((DataListUpdateManager) targetDataManager).add(
          getItemPositionInManager(targetPosition + (targetPosition > currentPosition ? -1 : 0)),
          obj, false);
      notifyItemMoved(currentPosition, targetPosition);
    }
  }

  void resetExpandedItems() {
    if (expandableMode != EXPANDABLE_MODE_NONE) {
      boolean notify = expandedItems.size() > 0;
      expandedItems.clear();
      lastExpandedIndex = -1;
      if (!notify) return;
      for (BaseDataManager dataManager : dataManagers) {
        notifyBinderItemRangeChanged(dataManager, 0, dataManager.size(), null);
      }
    }
  }
}
