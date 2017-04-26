package com.ahamed.multiviewadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * This is an internal class. Should not be extended by client code. Used to manage the different
 * {@link RecyclerView.ItemDecoration} for different {@link ItemBinder}. It will delegate the {@link
 * RecyclerView.ItemDecoration}
 */
class ItemDecorationManager extends RecyclerView.ItemDecoration {

  private final RecyclerListAdapter adapter;

  ItemDecorationManager(RecyclerListAdapter adapter) {
    this.adapter = adapter;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    boolean isReverseLayout = getIsReverseLayout(parent);
    ItemBinder binder = adapter.getBinderForPosition(parent.getChildAdapterPosition(view));
    if (binder.isItemDecorationEnabled()) {
      int itemPosition = adapter.getItemPositionInManager(parent.getChildAdapterPosition(view));
      int positionType = getPositionType(parent, view, itemPosition, isReverseLayout);
      binder.getItemOffsets(outRect, itemPosition, positionType);
    }
  }

  @Override public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
    boolean isReverseLayout = getIsReverseLayout(parent);
    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);
      ItemBinder binder = adapter.getBinderForPosition(parent.getChildAdapterPosition(child));
      if (binder.isItemDecorationEnabled()) {
        int itemPosition = adapter.getItemPositionInManager(parent.getChildAdapterPosition(child));
        int positionType = getPositionType(parent, child, itemPosition, isReverseLayout);
        binder.onDraw(canvas, parent, child, itemPosition, positionType);
      }
    }
  }

  private boolean getIsReverseLayout(RecyclerView parent) {
    return parent.getLayoutManager() instanceof LinearLayoutManager && ((LinearLayoutManager) parent
        .getLayoutManager()).getReverseLayout();
  }

  private @ItemDecorator.PositionType int getPositionType(RecyclerView parent, View child,
      int itemPosition, boolean isReverseLayout) {
    boolean isFirstItem =
        isReverseLayout ? adapter.isLastItemInManager(parent.getChildAdapterPosition(child))
            : itemPosition == 0;
    boolean isLastItem = isReverseLayout ? itemPosition == 0
        : adapter.isLastItemInManager(parent.getChildAdapterPosition(child));
    return isFirstItem ? ItemDecorator.POSITION_START
        : isLastItem ? ItemDecorator.POSITION_END : ItemDecorator.POSITION_MIDDLE;
  }
}