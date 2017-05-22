package com.ahamed.multiviewadapter.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.ahamed.multiviewadapter.RecyclerAdapter;

public class ItemBinderTouchCallback extends ItemTouchHelper.Callback {

  public static final float ALPHA_FULL = 1.0f;

  private final RecyclerAdapter adapter;

  public ItemBinderTouchCallback(RecyclerAdapter adapter) {
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
    if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
      final int dragFlags =
          ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
      final int swipeFlags = 0;
      return makeMovementFlags(dragFlags, swipeFlags);
    } else {
      final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
      final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
      return makeMovementFlags(dragFlags, swipeFlags);
    }
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