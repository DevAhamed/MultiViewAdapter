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

package mva2.adapter.internal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.MultiViewAdapter;

public final class ItemTouchCallback extends ItemTouchHelper.Callback {

  private final MultiViewAdapter adapter;

  public ItemTouchCallback(MultiViewAdapter adapter) {
    this.adapter = adapter;
  }

  @Override public int getMovementFlags(@NonNull RecyclerView recyclerView,
      @NonNull RecyclerView.ViewHolder viewHolder) {
    if (viewHolder instanceof ItemViewHolder) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      final int dragFlags = itemViewHolder.getDragDirections();
      final int swipeFlags = itemViewHolder.getSwipeDirections();
      return makeMovementFlags(dragFlags, swipeFlags);
    }
    return -1;
  }

  @Override
  public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder source,
      RecyclerView.ViewHolder target) {
    if (source.getItemViewType() != target.getItemViewType()) {
      return false;
    }
    adapter.onMove(source.getAdapterPosition(), target.getAdapterPosition());
    return true;
  }

  @Override public boolean isLongPressDragEnabled() {
    return false;
  }

  @Override public boolean isItemViewSwipeEnabled() {
    return true;
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    adapter.onItemDismiss(viewHolder.getAdapterPosition());
  }
}