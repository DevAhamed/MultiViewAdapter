package com.ahamed.multiviewadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class BaseBinder<M, VH extends BaseViewHolder<M>> {

  private ItemDecorator itemDecorator;

  public BaseBinder() {
  }

  public BaseBinder(ItemDecorator itemDecorator) {
    this.itemDecorator = itemDecorator;
  }

  public abstract VH create(LayoutInflater inflater, ViewGroup parent);

  public abstract void bind(VH holder, M item);

  public abstract boolean canBindData(Object item);

  public void bind(VH holder, M item, List payloads) {
    bind(holder, item);
  }

  public int getSpanSize(int maxSpanCount) {
    return 1;
  }

  boolean isItemDecorationEnabled() {
    return itemDecorator != null;
  }

  void getItemOffsets(Rect outRect, int position, @ItemDecorator.PositionType int positionType) {
    if (null != itemDecorator) {
      itemDecorator.getItemOffsets(outRect, position, positionType);
    }
  }

  void onDraw(Canvas canvas, RecyclerView parent, View child, int position,
      @ItemDecorator.PositionType int positionType) {
    if (null != itemDecorator) {
      itemDecorator.onDraw(canvas, parent, child, position, positionType);
    }
  }
}