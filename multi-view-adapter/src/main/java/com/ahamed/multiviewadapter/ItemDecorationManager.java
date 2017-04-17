package com.ahamed.multiviewadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorationManager extends RecyclerView.ItemDecoration {

  private final RecyclerListAdapter adapter;

  public ItemDecorationManager(RecyclerListAdapter adapter) {
    this.adapter = adapter;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    BaseBinder binder = adapter.getBinderForPosition(parent.getChildAdapterPosition(view));
    if (binder.isItemDecorationEnabled()) {
      int itemPosition = adapter.getItemPositionInManager(parent.getChildAdapterPosition(view));
      boolean isLastItem = adapter.isLastItemInManager(parent.getChildAdapterPosition(view));
      int positionType = itemPosition == 0 ? ItemDecorator.POSITION_START
          : isLastItem ? ItemDecorator.POSITION_END : ItemDecorator.POSITION_MIDDLE;
      binder.getItemOffsets(outRect, itemPosition, positionType);
    }
  }

  @Override public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount - 1; i++) {
      View child = parent.getChildAt(i);
      BaseBinder binder = adapter.getBinderForPosition(parent.getChildAdapterPosition(child));
      if (binder.isItemDecorationEnabled()) {
        int itemPosition = adapter.getItemPositionInManager(parent.getChildAdapterPosition(child));
        boolean isLastItem = adapter.isLastItemInManager(parent.getChildAdapterPosition(child));
        int positionType = itemPosition == 0 ? ItemDecorator.POSITION_START
            : isLastItem ? ItemDecorator.POSITION_END : ItemDecorator.POSITION_MIDDLE;
        binder.onDraw(canvas, parent, child, itemPosition, positionType);
      }
    }
  }
}