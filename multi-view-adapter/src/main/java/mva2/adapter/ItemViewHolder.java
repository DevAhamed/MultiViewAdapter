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

import android.view.View;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.util.OnItemClickListener;

/**
 * ItemViewHolder is a wrapper class around {@link RecyclerView.ViewHolder}.
 */
public class ItemViewHolder<M> extends RecyclerView.ViewHolder {

  private M item;
  private MultiViewAdapter adapter;

  public ItemViewHolder(View itemView) {
    super(itemView);
  }

  /**
   * Returns the drag directions for the provided ItemViewHolder. Default implementation returns the
   * drag directions as 0.
   * This method can be overridden by child classes to provide valid drag direction flags.
   *
   * @return A binary OR of direction flags.
   */
  public int getDragDirections() {
    return 0;
  }

  /**
   * Returns the item object bounded by this {@link ItemViewHolder}
   *
   * @return item bounded by this {@link ItemViewHolder}
   */
  public M getItem() {
    return item;
  }

  /**
   * Returns the swipe directions for the provided ItemViewHolder.
   * Default implementation returns the swipe directions as 0.
   * This method can be overridden by child classes to provide valid swipe direction flags.
   *
   * @return A binary OR of direction flags.
   */
  public int getSwipeDirections() {
    return 0;
  }

  /**
   * @return boolean value indicating whether the adapter is in context mode or not.
   *
   * @see MultiViewAdapter#startActionMode() ()
   * @see MultiViewAdapter#stopActionMode() ()
   */
  public boolean isInActionMode() {
    return adapter.isAdapterInActionMode();
  }

  /**
   * @return boolean value indicating whether the viewholder is expanded or not.
   *
   * @see ItemViewHolder#toggleItemExpansion()
   */
  public boolean isItemExpanded() {
    return adapter.isItemExpanded(getAdapterPosition());
  }

  /**
   * @return boolean value indicating whether the viewholder is selected or not.
   *
   * @see ItemViewHolder#toggleItemSelection()
   */
  public boolean isItemSelected() {
    return adapter.isItemSelected(getAdapterPosition());
  }

  /**
   * @return boolean value indicating whether the viewholder is expanded or not.
   *
   * @see ItemViewHolder#toggleItemExpansion()
   */
  public boolean isSectionExpanded() {
    return adapter.isSectionExpanded(getAdapterPosition());
  }

  /**
   * If you set the {@link OnItemClickListener} for your ListSection, call this method to get
   * notified.
   */
  public void onClick() {
    adapter.onItemClicked(getAdapterPosition());
  }

  /**
   * The method lets the user to start dragging the viewholder
   */
  public void startDrag() {
    adapter.onStartDrag(this);
  }

  /**
   * Can be called by the child view holders to toggle the {@link ItemViewHolder}'s expansion
   * status.
   */
  public void toggleItemExpansion() {
    adapter.onItemExpansionToggled(getAdapterPosition());
  }

  /**
   * Can be called by the child view holders to toggle the selection.
   */
  public void toggleItemSelection() {
    adapter.onItemSelectionToggled(getAdapterPosition());
  }

  /**
   * Can be called by the child view holders to toggle the {@link Section}'s expansion
   * status.
   */
  public void toggleSectionExpansion() {
    adapter.onSectionExpansionToggled(getAdapterPosition());
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  final void setAdapter(MultiViewAdapter adapter) {
    this.adapter = adapter;
  }

  void setItem(M item) {
    this.item = item;
  }
}
