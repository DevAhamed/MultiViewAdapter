package com.ahamed.multiviewadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

  private List<ItemBinder> binders = new ArrayList<>();
  private List<BaseDataManager> dataManagers = new ArrayList<>();
  private ItemDecorationManager itemDecorationManager;
  private int maxSpanCount = 1;
  private final GridLayoutManager.SpanSizeLookup spanSizeLookup =
      new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          return getBinderForPosition(position).getSpanSize(maxSpanCount);
        }
      };

  protected RecyclerAdapter() {
    this.itemDecorationManager = new ItemDecorationManager(this);
  }

  @Override public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return binders.get(viewType).createViewHolder(LayoutInflater.from(parent.getContext()), parent);
  }

  @Override public final void onBindViewHolder(BaseViewHolder holder, int adapterPosition) {
    onBindViewHolder(holder, adapterPosition, null);
  }

  @Override public final void onBindViewHolder(BaseViewHolder holder, int adapterPosition,
      List<Object> payloads) {
    ItemBinder baseBinder = binders.get(holder.getItemViewType());

    int totalCount = 0;
    for (BaseDataManager dataManager : dataManagers) {
      totalCount += dataManager.getCount();
      if (adapterPosition < totalCount) {
        //noinspection unchecked
        holder.setItem(dataManager.getItem(getItemPositionInManager(adapterPosition)));
        break;
      }
    }

    if (null == payloads) {
      //noinspection unchecked
      baseBinder.bindViewHolder(holder, holder.getItem(), isItemSelected(adapterPosition));
    } else {
      //noinspection unchecked
      baseBinder.bindViewHolder(holder, holder.getItem(), isItemSelected(adapterPosition),
          payloads);
    }
  }

  @Override public final int getItemCount() {
    int itemCount = 0;
    for (int i = 0, size = dataManagers.size(); i < size; i++) {
      itemCount += dataManagers.get(i).size();
    }
    return itemCount;
  }

  @Override public final int getItemViewType(int adapterPosition) {
    ItemBinder baseBinder = getBinderForPosition(adapterPosition);
    if (null != baseBinder) {
      return binders.indexOf(baseBinder);
    }
    return super.getItemViewType(adapterPosition);
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

  ///////////////////////////////////////////
  /////////// Internal API ahead. ///////////
  ///////////////////////////////////////////

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
    int binderItemCount;
    for (int i = 0, size = dataManagers.size(); i < size; i++) {
      binderItemCount = dataManagers.get(i).getCount();
      if (adapterPosition - binderItemCount < 0) {
        break;
      }
      adapterPosition -= binderItemCount;
    }
    // adapterPosition now refers to position in manager
    return adapterPosition;
  }

  BaseDataManager getDataManager(int adapterPosition) {
    int processedCount = 0;
    for (BaseDataManager dataManager : dataManagers) {
      processedCount += dataManager.getCount();
      if (adapterPosition < processedCount) {
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

  boolean isItemSelected(int adapterPosition) {
    return false;
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

  void addBinder(ItemBinder binder) {
    binders.add(binder);
  }

  boolean isLastItemInManager(int adapterPosition) {
    int itemsCount;
    for (int i = 0, size = dataManagers.size(); i < size; i++) {
      itemsCount = dataManagers.get(i).getCount();
      if (adapterPosition - itemsCount < 0) {
        return adapterPosition == itemsCount - 1;
      }
      adapterPosition -= itemsCount;
    }
    return false;
  }
}