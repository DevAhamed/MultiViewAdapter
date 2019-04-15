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

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SpanSizeLookup;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mva2.adapter.decorator.Decorator;
import mva2.adapter.decorator.PositionType;
import mva2.adapter.decorator.SectionPositionType;
import mva2.adapter.internal.Cache;
import mva2.adapter.internal.DecorationDelegate;
import mva2.adapter.internal.ItemTouchCallback;
import mva2.adapter.internal.Notifier;
import mva2.adapter.internal.SparseIntArrayCache;
import mva2.adapter.util.InfiniteLoadingHelper;
import mva2.adapter.util.Mode;

import static mva2.adapter.util.Mode.INHERIT;
import static mva2.adapter.util.Mode.MULTIPLE;

/**
 * MultiViewAdapter lets you write complex RecyclerView adapters easily.
 *
 * <p/>
 *
 * MultiViewAdapter consists of {@link Section}s and {@link ItemBinder}s. Both are modular and
 * composable meaning that they can be used in any adapter and you can use as many of them inside a
 * single adapter.
 *
 * <p/>
 *
 * Apart from this MultiViewAdapter has lot of other features like,
 *
 * <ul>
 * <li>Selection
 * <li>Expansion (Item level)
 * <li>Expansion (Section level)
 * <li>Decoration
 * </ul>
 */
public class MultiViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {

  private final NestedSection nestedSection = new NestedSection();
  private final List<ItemBinder> itemBinders = new ArrayList<>();
  private final DecorationDelegate decorationDelegate = new DecorationDelegate(this);
  private final Cache spanSizeCache;
  private final Cache positionTypeCache;
  private final Cache viewTypeCache;
  private ItemTouchHelper itemTouchHelper;
  private boolean isInActionMode = false;
  private int spanCount = 1;
  private final SpanSizeLookup spanSizeLookup;
  private final Notifier notifier = new Notifier() {
    @Override
    public void notifySectionItemMoved(Section section, int fromPosition, int toPosition) {
      onDataSetChanged();
      notifyAdapterItemMoved(fromPosition, toPosition);
    }

    @Override
    public void notifySectionRangeChanged(Section section, int positionStart, int itemCount,
        Object payload) {
      onDataSetChanged();
      notifyAdapterRangeChanged(positionStart, itemCount, payload);
    }

    @Override
    public void notifySectionRangeInserted(Section section, int positionStart, int itemCount) {
      onDataSetChanged();
      notifyAdapterRangeInserted(positionStart, itemCount);
    }

    @Override
    public void notifySectionRangeRemoved(Section section, int positionStart, int itemCount) {
      onDataSetChanged();
      notifyAdapterRangeRemoved(positionStart, itemCount);
    }
  };

  /**
   * No-arg constructor for MultiViewAdapter. Initializes the MultiViewAdapter class.
   */
  public MultiViewAdapter() {
    this(new SpanSizeLookup(new SparseIntArrayCache()), new SparseIntArrayCache(),
        new SparseIntArrayCache(), new SparseIntArrayCache());
  }

  /**
   * This constructor is used only in unit test cases. This constructor is not part of the public
   * api set offered by this library. This constructor api is subject to change without backward
   * compatibility.
   */
  MultiViewAdapter(SpanSizeLookup spanSizeLookup, Cache spanSizeCache, Cache positionTypeCache,
      Cache viewTypeCache) {
    this.spanSizeLookup = spanSizeLookup;
    this.spanSizeLookup.setAdapter(this);
    nestedSection.setNotifier(notifier);

    this.spanSizeCache = spanSizeCache;
    this.positionTypeCache = positionTypeCache;
    this.viewTypeCache = viewTypeCache;
  }

  /**
   * Add a section to this adapter. You can add any number of section to the adapter. If null is
   * being set, then this method returns silently. If the section was already added to a parent, and
   * you are adding it again, then IllegalStateException will be thrown.
   *
   * @param section Section to be added to this adapter.
   *
   * @throws IllegalStateException If the section already has a parent.
   */
  @SuppressWarnings("ConstantConditions") public void addSection(@NonNull Section section) {
    if (null == section) {
      return;
    }
    if (section.getNotifier() != null) {
      throw new IllegalStateException("Section is already has a parent!");
    }
    nestedSection.addSection(section);
  }

  /**
   * Clears all the selected items inside the adapter.
   */
  public void clearAllSelections() {
    nestedSection.clearAllSelections();
  }

  /**
   * Collapses all the items inside the adapter.
   */
  public void collapseAllItems() {
    nestedSection.collapseAllItems();
  }

  /**
   * Collapses all the sections inside the adapter.
   */
  public void collapseAllSections() {
    nestedSection.collapseAllSections();
  }

  /**
   * If you would like to use the decoration feature of the adapter, use this method to get
   * ItemDecoration and add it to your recyclerview.
   *
   * <p/>
   * Usage :
   * <code>
   * recyclerView.addItemDecoration(adapter.getItemDecoration());
   * </code>
   *
   * <p/>
   *
   * @return The ItemDecoration for this adapter
   */
  public RecyclerView.ItemDecoration getItemDecoration() {
    return decorationDelegate;
  }

  /**
   * Get the {@link ItemTouchHelper} which resolves the swipe and drag gestures for the adapter's
   * individual ItemBinder.
   *
   * @return ItemTouchHelper which needs to be attached with RecyclerView
   */
  public ItemTouchHelper getItemTouchHelper() {
    if (null == itemTouchHelper) {
      ItemTouchCallback itemTouchCallback = new ItemTouchCallback(this);
      itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
    }
    return itemTouchHelper;
  }

  /**
   * Returns the positionType of the item inside the layout manager.
   *
   * <p/>
   *
   * What is positionType? Position type lets you know the edges for the item. For example, inside
   * LinearLayoutManager, item 0 will be at top edge, left edge and right edge. Inside
   * GridLayoutManager, item 0 will be at top edge and left edge. This information can be retrieved
   * with the help of Decorator. Ex : {@link Decorator#isItemOnLeftEdge(int)} etc.,
   *
   * <p/>
   *
   * By knowing this information you can draw the decorations creatively.
   *
   * @param parent          RecyclerView where this adapter is attached.
   * @param adapterPosition Position of the item inside the adapter.
   *
   * @return PositionType for the adapterPosition
   */
  public @PositionType int getPositionType(RecyclerView parent, int adapterPosition) {
    int positionType = positionTypeCache.get(adapterPosition, -1);
    if (positionType == -1) {
      positionType = nestedSection.getPositionType(adapterPosition, adapterPosition,
          parent.getLayoutManager());
      positionTypeCache.append(adapterPosition, positionType);
    }
    return positionType;
  }

  /**
   * Returns the SectionPositionType for the giver adapter position.
   *
   * <p/>
   *
   * SectionPositionType lets you know whether the Section is at the TOP, MIDDLE or BOTTOM of the
   * adapter. By knowing this information you can draw the decorations creatively.
   *
   * @param adapterPosition Position of the item inside the adapter.
   *
   * @return SectionPositionType for the adapterPosition
   */
  public SectionPositionType getSectionPositionType(int adapterPosition) {
    return nestedSection.getSectionPositionType(adapterPosition);
  }

  /**
   * Return the span size lookup used by this adapter. Make sure to set this SpanSizeLookup to your
   * grid layout manager.
   *
   * @return Span size lookup used in this adapter
   */
  public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
    return spanSizeLookup;
  }

  /**
   * Register an ItemBinder to the adapter.
   *
   * <p/>
   *
   * Each ItemBinder can create and bind ViewHolders for a model. If no ItemBinders are registered
   * with adapter, IllegalStateException will be thrown.
   */
  public void registerItemBinders(ItemBinder... itemBinders) {
    Collections.addAll(this.itemBinders, itemBinders);
  }

  /**
   * Remove all the sections added to the adapter
   */
  public void removeAllSections() {
    int previousCount = getItemCount();
    nestedSection.removeAllSections();
    onDataSetChanged();
    notifier.notifySectionRangeRemoved(null, 0, previousCount);
  }

  /**
   * Set the expansion mode for this adapter. This will be used by all the section unless the
   * section has its own expansion mode.
   *
   * @param expansionMode Mode to be set as expansion mode
   */
  public void setExpansionMode(@NonNull Mode expansionMode) {
    if (expansionMode == INHERIT) {
      return;
    }
    nestedSection.setExpansionMode(expansionMode);
  }

  /**
   * By setting an infiniteLoadingHelper, you can add infinite scrolling feature to the adapter.
   *
   * <p/>
   * Usage :
   *
   * <code>
   *
   * InfiniteLoadingHelper helper = new InfiniteLoadingHelper(recyclerView,
   * R.layout.item_infinite_loader);
   * <br/>
   * adapter.setInfiniteLoadingHelper(helper);
   *
   * </code>
   *
   * @param infiniteLoadingHelper Helper object to be set
   *
   * @see InfiniteLoadingHelper
   */
  public void setInfiniteLoadingHelper(@NonNull InfiniteLoadingHelper infiniteLoadingHelper) {
    registerItemBinders(infiniteLoadingHelper.getItemBinder());
    ItemSection<String> itemSection = new ItemSection<>("LoadingItem");
    addSection(itemSection);
    infiniteLoadingHelper.setFooterSection(itemSection);
  }

  /**
   * Set the section expansion mode for this adapter. This will be used by all the section unless
   * the
   * section has its own section expansion mode.
   *
   * @param sectionExpansionMode Mode to be set as section expansion mode
   */
  public void setSectionExpansionMode(Mode sectionExpansionMode) {
    if (sectionExpansionMode == INHERIT) {
      return;
    }
    nestedSection.setSectionExpansionMode(sectionExpansionMode);
  }

  /**
   * Set the selection mode for this adapter. This will be used by all the section unless the
   * section has its own selection mode.
   *
   * @param selectionMode Mode to be set as selection mode
   */
  public void setSelectionMode(Mode selectionMode) {
    if (selectionMode == INHERIT) {
      return;
    }
    nestedSection.setSelectionMode(selectionMode);
  }

  /**
   * Set the span count for the adapter. This will be used for all the section unless the section
   * has its own span count.
   *
   * @param spanCount Span count to be set
   */
  public void setSpanCount(int spanCount) {
    this.spanCount = spanCount;
    spanSizeCache.clear();
  }

  /**
   * Helper method to set the adapter is in contextual action mode.
   *
   * @see ItemViewHolder To get the state of action mode
   */
  public void startActionMode() {
    isInActionMode = true;
  }

  /**
   * Helper method to set the adapter has exited contextual action mode.
   *
   * @see ItemViewHolder To get the state of action mode
   */
  public void stopActionMode() {
    isInActionMode = false;
  }

  /**
   * Removes all the ItemBinders which were registered to this adapter. Make sure that new
   * ItemBinders are registered to this adapter.
   */
  public void unRegisterAllItemBinders() {
    itemBinders.clear();
    onDataSetChanged();
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @RestrictTo(RestrictTo.Scope.LIBRARY) public final int getSpanSize(int adapterPosition) {
    int calculatedSpanSize = spanSizeCache.get(adapterPosition, -1);
    if (calculatedSpanSize == -1) {
      int sectionSpanCount = getSectionSpanCount(adapterPosition);
      int spanSize = getItemBinder(adapterPosition).getSpanSize(sectionSpanCount);
      calculatedSpanSize = spanSize * spanCount / sectionSpanCount;
      spanSizeCache.append(adapterPosition, calculatedSpanSize);
    }
    return calculatedSpanSize;
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public final void drawDecoration(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    drawSectionDecoration(canvas, parent, state, child, adapterPosition);
    drawItemDecoration(canvas, parent, state, child, adapterPosition);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public final void getDecorationOffset(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    applySectionDecorationOffset(outRect, view, parent, state, adapterPosition);
    applyBinderDecorationOffset(outRect, view, parent, state, adapterPosition);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) public final void onItemDismiss(int adapterPosition) {
    nestedSection.onItemDismiss(adapterPosition);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public final void onMove(int initialPosition, int targetPosition) {
    if (initialPosition == -1 || initialPosition == targetPosition) {
      return;
    }
    if (nestedSection.move(initialPosition, targetPosition - initialPosition)) {
      notifier.notifySectionItemMoved(nestedSection, initialPosition, targetPosition);
    }
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override @NonNull
  public final ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return itemBinders.get(viewType).createViewHolder(parent, this);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onBindViewHolder(@NonNull ItemViewHolder holder, int adapterPosition) {
    onBindViewHolder(holder, adapterPosition, new ArrayList<>());
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @SuppressWarnings("unchecked") @Override
  public final void onBindViewHolder(ItemViewHolder holder, int adapterPosition,
      @NonNull List<Object> payloads) {
    ItemBinder itemBinder = itemBinders.get(holder.getItemViewType());
    holder.setItem(getItem(adapterPosition));

    if (payloads.size() == 0) {
      itemBinder.bindViewHolder(holder, holder.getItem());
    } else {
      itemBinder.bindViewHolder(holder, holder.getItem(), payloads);
    }
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final int getItemViewType(int adapterPosition) {
    int viewType = viewTypeCache.get(adapterPosition, -1);
    if (viewType == -1) {
      Object recyclerItem = getItem(adapterPosition);
      viewType = getItemBinderPositionForItem(recyclerItem);
      viewTypeCache.append(adapterPosition, viewType);
    }
    return viewType;
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override public final int getItemCount() {
    return nestedSection.getCount();
  }

  boolean isSectionExpanded(int adapterPosition) {
    return nestedSection.isSectionExpanded(adapterPosition);
  }

  void onItemClicked(int adapterPosition) {
    nestedSection.onItemClicked(adapterPosition);
  }

  boolean isAdapterInActionMode() {
    return isInActionMode;
  }

  boolean isItemExpanded(int adapterPosition) {
    return nestedSection.isItemExpanded(adapterPosition);
  }

  boolean isItemSelected(int adapterPosition) {
    return nestedSection.isItemSelected(adapterPosition);
  }

  void onSectionExpansionToggled(int adapterPosition) {
    nestedSection.onSectionExpansionToggled(adapterPosition, MULTIPLE);
  }

  void onItemExpansionToggled(int adapterPosition) {
    nestedSection.onItemExpansionToggled(adapterPosition, MULTIPLE);
  }

  void onItemSelectionToggled(int adapterPosition) {
    nestedSection.onItemSelectionToggled(adapterPosition, MULTIPLE);
  }

  void onStartDrag(ItemViewHolder itemViewHolder) {
    if (null != itemTouchHelper) {
      itemTouchHelper.startDrag(itemViewHolder);
    }
  }

  void onDataSetChanged() {
    spanSizeLookup.clearCache();
    positionTypeCache.clear();
    spanSizeCache.clear();
    viewTypeCache.clear();
  }

  @SuppressWarnings("WeakerAccess") void notifyAdapterItemMoved(int fromPosition, int toPosition) {
    notifyItemMoved(fromPosition, toPosition);
  }

  @SuppressWarnings("WeakerAccess") void notifyAdapterRangeChanged(int positionStart, int itemCount,
      Object payload) {
    notifyItemRangeChanged(positionStart, itemCount, payload);
  }

  @SuppressWarnings("WeakerAccess") void notifyAdapterRangeInserted(int positionStart,
      int itemCount) {
    notifyItemRangeInserted(positionStart, itemCount);
  }

  @SuppressWarnings("WeakerAccess") void notifyAdapterRangeRemoved(int positionStart,
      int itemCount) {
    notifyItemRangeRemoved(positionStart, itemCount);
  }

  private void applyBinderDecorationOffset(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    getItemBinder(adapterPosition).getItemOffsets(outRect, view, parent, state, adapterPosition);
  }

  private void applySectionDecorationOffset(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    nestedSection.getChildSectionOffsets(adapterPosition, outRect, view, parent, state,
        adapterPosition);
  }

  private void drawItemDecoration(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    getItemBinder(adapterPosition).drawItemDecoration(canvas, parent, state, child,
        adapterPosition);
  }

  private void drawSectionDecoration(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    nestedSection.drawChildSectionDecoration(adapterPosition, canvas, parent, state, child,
        adapterPosition);
  }

  private ItemBinder getItemBinder(int adapterPosition) {
    return itemBinders.get(getItemViewType(adapterPosition));
  }

  private int getItemBinderPositionForItem(Object recyclerItem) {
    int binderPosition = 0;
    for (ItemBinder itemBinder : itemBinders) {
      if (itemBinder.canBindData(recyclerItem)) {
        return binderPosition;
      }
      binderPosition++;
    }
    throw new IllegalStateException("ItemBinder not found for position. Item = " + recyclerItem);
  }

  @NonNull private Object getItem(int adapterPosition) {
    return nestedSection.getItem(adapterPosition);
  }

  private int getSectionSpanCount(int adapterPosition) {
    return nestedSection.getMaxSpanCount(adapterPosition, spanCount);
  }
}
