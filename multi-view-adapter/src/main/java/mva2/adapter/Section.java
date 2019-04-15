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
import android.view.View;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import mva2.adapter.decorator.Decorator;
import mva2.adapter.decorator.SectionPositionType;
import mva2.adapter.internal.Notifier;
import mva2.adapter.internal.RecyclerItem;
import mva2.adapter.util.Mode;

import static android.support.v7.widget.RecyclerView.VERTICAL;
import static mva2.adapter.util.Mode.INHERIT;

/**
 * Sections are the building blocks of the MultiViewAdapter.
 *
 * <p/>
 *
 * Sections abstracts all the data related operations away from the adapter. You can add as many as
 * sections to the adapter. All the updated to the section will correctly result in the proper
 * notify method being called.
 *
 * <p/>
 *
 * Similar to the MultiViewAdapter,
 * 1. You can add decorators to section
 * 2. Set SelectionMode, ExpansionMode and SectionExpansionMode
 * 3. Set span count for the section.
 *
 * <p/>
 *
 * The library has few implementation of Section classes which will help you in writing your own
 * section implementation.
 */
public abstract class Section implements ListUpdateCallback {

  @NonNull Mode selectionMode = INHERIT;
  @NonNull Mode expansionMode = INHERIT;
  @NonNull Mode sectionExpansionMode = INHERIT;
  private int spanCount = Integer.MAX_VALUE;
  private List<Decorator> decorators = new ArrayList<>();
  private Notifier notifier;
  private boolean isSectionExpanded = true;
  private boolean isSectionHidden = false;

  /**
   * Adds a decorator to the section. Decorators can affect both measurement and drawing of
   * individual item views.
   *
   * <p/>
   *
   * Decorators are ordered. Decorators placed earlier in the list will be run/queried/drawn first
   * for their effects on item views. Padding added to views will be nested; a padding added by an
   * earlier decorator will mean further decorator in the list will be asked to draw/pad within the
   * previous decorator's given area.
   *
   * @param decorator Decorator to be added
   */
  public void addDecorator(Decorator decorator) {
    addDecorator(decorator, -1);
  }

  /**
   * Adds a decorator to the section with priority. Decorators can affect both measurement and
   * drawing of individual item views.
   *
   * <p/>
   *
   * Decorators are ordered. Decorators placed earlier in the list will be run/queried/drawn first
   * for their effects on item views. Padding added to views will be nested; a padding added by an
   * earlier decorator will mean further decorator in the list will be asked to draw/pad within the
   * previous decorator's given area.
   *
   * @param decorator Decorator to be added
   */
  public void addDecorator(Decorator decorator, int priority) {
    if (null == decorators) {
      decorators = new ArrayList<>();
    }
    if (priority >= 0 && decorators.size() > priority) {
      decorators.add(priority, decorator);
    } else {
      decorators.add(decorator);
    }
  }

  /**
   * Hide's the section inside the adapter. If the section is hidden, then the {@link
   * Section#getCount()} returns 0.
   */
  public void hideSection() {
    if (!isSectionHidden) {
      int count = getCount();
      isSectionHidden = true;
      onRemoved(0, count);
    }
  }

  /**
   * Returns boolean flag indicating whether the section is hidden or not.
   */
  public boolean isSectionHidden() {
    return isSectionHidden;
  }

  /**
   * Removes all the decorators added to this section.
   */
  public void removeAllDecorators() {
    decorators.clear();
  }

  /**
   * Removes the mentioned decorator from this section. If the decorator is not found, then this
   * method
   * returns silently.
   *
   * @param decorator Decorator to be removed.
   *
   * @return boolean flag which indicated whether the decorator was removed or not.
   */
  public boolean removeDecorator(Decorator decorator) {
    return decorators.remove(decorator);
  }

  /**
   * Removes the decorator at the specified index from this section. If the decorator is not found,
   * then this method returns silently.
   *
   * @param index index of the decorator to be removed.
   */
  public void removeDecorator(int index) {
    decorators.remove(index);
  }

  /**
   * Set the expansion mode for this section. Default the expansion mode is {@link Mode#INHERIT}. If
   * the value is set then it overrides the parent's expansion mode.
   *
   * @param expansionMode Mode to be set for this section's expansion mode
   */
  public void setExpansionMode(@NonNull Mode expansionMode) {
    this.expansionMode = expansionMode;
  }

  /**
   * Set the section expansion mode for this section. Default the section expansion mode is {@link
   * Mode#INHERIT}. If
   * the value is set then it overrides the parent's section expansion mode.
   *
   * @param sectionExpansionMode Mode to be set for this section's section expansion mode
   */
  public void setSectionExpansionMode(@NonNull Mode sectionExpansionMode) {
    this.sectionExpansionMode = sectionExpansionMode;
  }

  /**
   * Set the selection mode for this section. Default the selection mode is {@link Mode#INHERIT}. If
   * the value is set then it overrides the parent's selection mode.
   *
   * @param selectionMode Mode to be set for this section's selection mode
   */
  public void setSelectionMode(@NonNull Mode selectionMode) {
    this.selectionMode = selectionMode;
  }

  /**
   * Set the span count for this section. The span count of the section will used along with its
   * parent's span count to calculate the maxSpanCount which is used in {@link
   * ItemBinder#getSpanSize(int)}.
   *
   * <p/>
   *
   * For example,
   * <ul>
   * <li>Parent's span count is 6.</li>
   * <li>Section's span count is 3.</li>
   * </ul>
   *
   * Then,
   * maxSpanCount sent in {@link ItemBinder#getSpanSize(int)} is 3. Span count for each item will
   * be multiplied by 2 (ParentSpanCount / SectionSpanCount). Lets assume that each item inside the
   * section returns span count as 1. Then, inside parent each will be considered as 1 * 2 = 2.
   *
   * <p/>
   *
   * As a best practice, set the spanCount for parent as multiple of the section's spanCount.
   *
   * @param spanCount Count to be set as span count
   */
  public void setSpanCount(int spanCount) {
    this.spanCount = spanCount;
  }

  /**
   * If the section was hidden from parent, this method shows it again in the parent.
   */
  public void showSection() {
    if (isSectionHidden) {
      isSectionHidden = false;
      onInserted(0, getCount());
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onInserted(int position, int count) {
    onDataSetChanged();
    if (null != notifier && isSectionVisible()) {
      notifier.notifySectionRangeInserted(this, position, count);
    }
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onRemoved(int position, int count) {
    onDataSetChanged();
    if (null != notifier) {
      notifier.notifySectionRangeRemoved(this, position, count);
    }
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onMoved(int fromPosition, int toPosition) {
    onDataSetChanged();
    if (null != notifier && isSectionVisible()) {
      notifier.notifySectionItemMoved(this, fromPosition, toPosition);
    }
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onChanged(int position, int count, Object payload) {
    onDataSetChanged();
    if (null != notifier && isSectionVisible()) {
      notifier.notifySectionRangeChanged(this, position, count, payload);
    }
  }

  int getMaxSpanCount(int itemPosition, int spanCount) {
    return this.spanCount == Integer.MAX_VALUE ? spanCount : this.spanCount;
  }

  boolean isSectionVisible() {
    return isSectionExpanded() && !isSectionHidden;
  }

  void setNotifier(Notifier notifier) {
    this.notifier = notifier;
  }

  Notifier getNotifier() {
    return notifier;
  }

  boolean isSectionExpanded() {
    return isSectionExpanded;
  }

  void setSectionExpanded(boolean isSectionExpanded) {
    this.isSectionExpanded = isSectionExpanded;
  }

  void collapseSection() {
    // No-op
  }

  boolean isSectionExpanded(int itemPosition) {
    return false;
  }

  SectionPositionType getSectionPositionType(int adapterPosition, int sectionPosition, int size) {
    return sectionPosition == 0 ? SectionPositionType.FIRST
        : size - 1 == sectionPosition ? SectionPositionType.LAST : SectionPositionType.MIDDLE;
  }

  void drawDecoration(int itemPosition, @NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    if (decorators.size() > 0) {
      for (Decorator decorator : decorators) {
        decorator.onDraw(canvas, parent, state, child, adapterPosition);
      }
    }
  }

  void getDecorationOffsets(int itemPosition, @NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    if (decorators.size() > 0) {
      for (Decorator decorator : decorators) {
        decorator.getItemOffsets(outRect, view, parent, state, adapterPosition);
      }
    }
  }

  boolean isReverseLayout(LayoutManager layoutManager) {
    return layoutManager instanceof LinearLayoutManager
        && ((LinearLayoutManager) layoutManager).getReverseLayout();
  }

  boolean isVertical(LayoutManager layoutManager) {
    return (layoutManager instanceof LinearLayoutManager)
        && (((LinearLayoutManager) layoutManager).getOrientation() == VERTICAL);
  }

  void onItemClicked(int position) {
    // No-op
  }

  void onDataSetChanged() {
    // No-op
  }

  @NonNull Mode getModeToHonor(@NonNull Mode parentMode, @NonNull Mode childMode) {
    return childMode != INHERIT ? childMode : parentMode;
  }

  abstract Object getItem(int itemPosition);

  abstract int getCount();

  abstract boolean isItemSelected(int itemPosition);

  abstract void onItemSelectionToggled(int itemPosition, @NonNull Mode selectionMode);

  abstract void clearAllSelections();

  abstract boolean isItemExpanded(int itemPosition);

  abstract void onItemExpansionToggled(int itemPosition, @NonNull Mode selectionMode);

  abstract void collapseAllItems();

  abstract int onSectionExpansionToggled(int itemPosition, @NonNull Mode sectionExpansionMode);

  abstract int getPositionType(int itemPosition, int adapterPosition, LayoutManager layoutManager);

  abstract void onItemDismiss(int itemPosition);

  abstract boolean move(int itemPosition, int targetOffset);

  abstract RecyclerItem startMovingItem(int itemPosition);

  abstract void finishMovingItem(int targetPosition, RecyclerItem itemToMove);
}
