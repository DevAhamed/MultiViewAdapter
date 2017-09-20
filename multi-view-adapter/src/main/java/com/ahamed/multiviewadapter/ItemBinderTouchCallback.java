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

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

final class ItemBinderTouchCallback extends ItemTouchHelper.Callback {

  private final RecyclerAdapter adapter;

  ItemBinderTouchCallback(RecyclerAdapter adapter) {
    this.adapter = adapter;
  }

  @Override public boolean isLongPressDragEnabled() {
    return false;
  }

  @Override public boolean isItemViewSwipeEnabled() {
    return true;
  }

  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    if (viewHolder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      final int dragFlags = itemViewHolder.getDragDirections();
      final int swipeFlags = itemViewHolder.getSwipeDirections();
      return makeMovementFlags(dragFlags, swipeFlags);
    }
    return -1;
  }

  @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
      RecyclerView.ViewHolder target) {
    if (source.getItemViewType() != target.getItemViewType()) {
      return false;
    }
    adapter.onMove(source.getAdapterPosition(), target.getAdapterPosition());
    return true;
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
    adapter.onItemDismiss(viewHolder.getAdapterPosition());
  }
}