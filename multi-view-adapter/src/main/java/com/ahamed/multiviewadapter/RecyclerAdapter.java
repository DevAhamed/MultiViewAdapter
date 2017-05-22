package com.ahamed.multiviewadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.annotation.ExpandableMode;
import com.ahamed.multiviewadapter.util.ItemBinderTouchCallback;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

  public static final int EXPANDABLE_MODE_NONE = -1;
  public static final int EXPANDABLE_MODE_SINGLE = 1;
  public static final int EXPANDABLE_MODE_MULTIPLE = 2;

  private List<ItemBinder> binders = new ArrayList<>();
  private List<BaseDataManager> dataManagers = new ArrayList<>();
  private SparseBooleanArray expandedItems = new SparseBooleanArray();
  private ItemDecorationManager itemDecorationManager;
  private int maxSpanCount = 1;
  private final GridLayoutManager.SpanSizeLookup spanSizeLookup =
      new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          return getBinderForPosition(position).getSpanSize(maxSpanCount);
        }
      };
  private ItemTouchHelper itemTouchHelper;
  private int lastExpandedIndex = -1;
  @ExpandableMode private int expandableMode = EXPANDABLE_MODE_NONE;
  @ExpandableMode private int groupExpandableMode = EXPANDABLE_MODE_NONE;
  private ItemActionListener actionListener = new ItemActionListener() {

    @Override public void onItemSelectionToggled(int position) {
      RecyclerAdapter.this.onItemSelectionToggled(position);
    }

    @Override public void onItemExpansionToggled(int position) {
      RecyclerAdapter.this.onItemExpansionToggled(position);
    }

    @Override public void onGroupExpansionToggled(int position) {
      RecyclerAdapter.this.onGroupExpansionToggled(position);
    }

    @Override public boolean isItemSelected(int position) {
      return RecyclerAdapter.this.isItemSelected(position);
    }

    @Override public boolean isItemExpanded(int position) {
      return RecyclerAdapter.this.itemExpanded(position);
    }
  };

  protected RecyclerAdapter() {
    itemDecorationManager = new ItemDecorationManager(this);
  }

  private boolean itemExpanded(int position) {
    return expandedItems.get(position, false);
  }

  @Override public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return binders.get(viewType)
        .createViewHolder(LayoutInflater.from(parent.getContext()), parent, actionListener);
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
        if (dataManager instanceof DataGroupManager) {
          dataManager = ((DataGroupManager) dataManager).getDataManagerForPosition(
              getItemPositionInManager(adapterPosition));
        }
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

  int getBinderIndexForPosition(int adapterPosition) {
    BaseDataManager dataManager = getDataManager(adapterPosition);
    int index = 0;
    for (ItemBinder baseBinder : binders) {
      if (baseBinder.canBindData(dataManager.getItem(getItemPositionInManager(adapterPosition)))) {
        return index;
      }
      index++;
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
        if (dataManager instanceof DataGroupManager) {
          return ((DataGroupManager) dataManager).getDataManagerForPosition(
              getItemPositionInManager(adapterPosition));
        } else {
          return dataManager;
        }
      }
    }
    throw new IllegalStateException("Invalid position for DataManager!");
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
    return getDataManager(adapterPosition).isItemSelected(
        getItemPositionInManager(adapterPosition));
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

  private void addBinder(ItemBinder binder) {
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

  void onItemSelectionToggled(int position) {
    // Do nothing. Should be handled by SelectableAdapter
  }

  private void onItemExpansionToggled(int adapterPosition) {
    switch (expandableMode) {
      case EXPANDABLE_MODE_SINGLE:
        if (lastExpandedIndex == adapterPosition) {
          return;
        }
        if (lastExpandedIndex != -1) {
          getDataManager(lastExpandedIndex).onItemExpansionToggled(
              getItemPositionInManager(lastExpandedIndex));
        }
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
        if (lastExpandedIndex != -1) {
          getDataManager(lastExpandedIndex).onGroupExpansionToggled();
        }
        getDataManager(adapterPosition).onGroupExpansionToggled();
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

  public void onItemDismiss(int adapterPosition) {
    BaseDataManager baseDataManager = getDataManager(adapterPosition);
    if (baseDataManager instanceof DataListManager) {
      ((DataListManager) baseDataManager).remove(getItemPositionInManager(adapterPosition));
    } else if (baseDataManager instanceof DataItemManager) {
      ((DataItemManager) baseDataManager).removeItem();
    }
  }

  public void onMove(int currentPosition, int targetPosition) {
    if (currentPosition == -1) {
      return;
    }
    BaseDataManager dataManager = getDataManager(currentPosition);
    BaseDataManager targetDataManager = getDataManager(targetPosition);
    if (dataManager.equals(targetDataManager)) {
      dataManager.onSwapped(getItemPositionInManager(currentPosition),
          getItemPositionInManager(targetPosition));
    } else {
      Object obj = dataManager.get(getItemPositionInManager(currentPosition));
      ((DataListUpdateManager) dataManager).remove(getItemPositionInManager(currentPosition),
          false);
      ((DataListUpdateManager) targetDataManager).add(
          getItemPositionInManager(targetPosition + (targetPosition > currentPosition ? -1 : 0)),
          obj, false);
      notifyItemMoved(currentPosition, targetPosition);
    }
  }
}