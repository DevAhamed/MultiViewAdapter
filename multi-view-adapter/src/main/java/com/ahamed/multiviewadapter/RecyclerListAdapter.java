package com.ahamed.multiviewadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

  private List<BaseBinder> binders = new ArrayList<>();
  private List<BaseDataManager> dataManagers = new ArrayList<>();

  private ItemDecorationManager itemDecorationManager;
  private int maxSpanCount = 1;

  private final GridLayoutManager.SpanSizeLookup spanSizeLookup =
      new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          return getBinderForPosition(position).getSpanSize(maxSpanCount);
        }
      };

  public RecyclerListAdapter() {
    this.itemDecorationManager = new ItemDecorationManager(this);
  }

  @Override public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return binders.get(viewType).createViewHolder(LayoutInflater.from(parent.getContext()), parent);
  }

  @Override public final void onBindViewHolder(BaseViewHolder holder, int position) {
    onBindViewHolder(holder, position, null);
  }

  @Override
  public final void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
    BaseBinder baseBinder = binders.get(holder.getItemViewType());

    int totalCount = 0;
    for (BaseDataManager dataManager : dataManagers) {
      totalCount += dataManager.getCount();
      if (position < totalCount) {
        //noinspection unchecked
        holder.setItem(dataManager.getItem(getItemPositionInManager(position)));
        break;
      }
    }

    if (null == payloads) {
      //noinspection unchecked
      baseBinder.bindViewHolder(holder, holder.getItem(), isItemSelected(position));
    } else {
      //noinspection unchecked
      baseBinder.bindViewHolder(holder, holder.getItem(), isItemSelected(position), payloads);
    }
  }

  boolean isItemSelected(int adapterPosition) {
    return false;
  }

  @Override public final int getItemCount() {
    int itemCount = 0;
    for (int i = 0, size = dataManagers.size(); i < size; i++) {
      BaseDataManager dataManager = dataManagers.get(i);
      itemCount += dataManager.getCount();
    }
    return itemCount;
  }

  @Override public final int getItemViewType(int position) {
    BaseBinder baseBinder = getBinderForPosition(position);
    if (null != baseBinder) {
      return binders.indexOf(baseBinder);
    }
    return super.getItemViewType(position);
  }

  public final void setSpanCount(int maxSpanCount) {
    this.maxSpanCount = maxSpanCount;
  }

  public final GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
    return spanSizeLookup;
  }

  /*
  * Position refers to overall list position
   */
  BaseBinder getBinderForPosition(int position) {
    BaseDataManager dataManager = getDataManager(position);
    for (BaseBinder baseBinder : binders) {
      if (baseBinder.canBindData(dataManager.getItem(getItemPositionInManager(position)))) {
        return baseBinder;
      }
    }
    throw new IllegalStateException("Binder not found for position. Position = " + position);
  }

  public ItemDecorationManager getItemDecorationManager() {
    return itemDecorationManager;
  }

  int getItemPositionInManager(int position) {
    int binderItemCount;
    for (int i = 0, size = dataManagers.size(); i < size; i++) {
      binderItemCount = dataManagers.get(i).getCount();
      if (position - binderItemCount < 0) {
        break;
      }
      position -= binderItemCount;
    }
    return position;
  }

  // TODO AdapterPosition
  BaseDataManager getDataManager(int position) {
    int processedCount = 0;
    for (BaseDataManager dataManager : dataManagers) {
      processedCount += dataManager.getCount();
      if (position < processedCount) {
        return dataManager;
      }
    }
    throw new IllegalStateException("Invalid position for DataManager!");
  }

  private int getPosition(BaseDataManager dataManager, int binderPosition) {
    int viewType = dataManagers.indexOf(dataManager);
    if (viewType < 0) {
      throw new IllegalStateException("DataManager does not exist in adapter");
    }

    int position = binderPosition;
    for (int i = 0; i < viewType; i++) {
      position += dataManagers.get(i).getCount();
    }
    return position;
  }

  final void notifyBinderItemRangeChanged(BaseDataManager binder, int positionStart, int itemCount,
      Object payload) {
    notifyItemRangeChanged(getPosition(binder, positionStart), itemCount, payload);
  }

  final void notifyBinderItemMoved(BaseDataManager binder, int fromPosition, int toPosition) {
    notifyItemMoved(getPosition(binder, fromPosition), getPosition(binder, toPosition));
  }

  final void notifyBinderItemRangeInserted(BaseDataManager binder, int positionStart,
      int itemCount) {
    notifyItemRangeInserted(getPosition(binder, positionStart), itemCount);
  }

  final void notifyBinderItemRangeRemoved(BaseDataManager binder, int positionStart,
      int itemCount) {
    notifyItemRangeRemoved(getPosition(binder, positionStart), itemCount);
  }

  protected final void addDataManager(BaseDataManager dataManager) {
    dataManagers.add(dataManager);
  }

  protected final void registerBinder(BaseBinder binder) {
    addBinder(binder);
  }

  void addBinder(BaseBinder binder) {
    binders.add(binder);
  }

  boolean isLastItemInManager(int position) {
    int itemsCount;
    for (int i = 0, size = dataManagers.size(); i < size; i++) {
      itemsCount = dataManagers.get(i).getCount();
      if (position - itemsCount < 0) {
        return position == itemsCount - 1;
      }
      position -= itemsCount;
    }
    return false;
  }
}