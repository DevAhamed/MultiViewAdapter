package com.ahamed.multiviewadapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

final class ItemBinderTouchCallback extends ItemTouchHelper.Callback {

  private final RecyclerAdapter adapter;

  ItemBinderTouchCallback(RecyclerAdapter adapter) {
    this.adapter = adapter;
  }

  @Override public boolean isLongPressDragEnabled() {
    return true;
  }

  @Override public boolean isItemViewSwipeEnabled() {
    return true;
  }

  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    if (viewHolder instanceof BaseViewHolder) {
      BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
      final int dragFlags = baseViewHolder.getDragDirections();
      final int swipeFlags = baseViewHolder.getSwipeDirections();
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