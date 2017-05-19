package com.ahamed.multiviewadapter.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.RecyclerAdapter;

public class ItemBinderTouchCallback extends ItemTouchHelper.SimpleCallback {

  private final RecyclerAdapter mAdapter;

  public ItemBinderTouchCallback(RecyclerAdapter mAdapter) {
    super(0, 0);
    this.mAdapter = mAdapter;
  }

  @Override public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    return ((BaseViewHolder) viewHolder).getSwipeDirections();
  }

  @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      RecyclerView.ViewHolder target) {
    return false;
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
  }
}