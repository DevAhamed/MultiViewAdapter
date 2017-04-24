package com.ahamed.multiviewadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class ItemBinder<M, VH extends BaseViewHolder<M>> {

  private ItemDecorator itemDecorator;

  public ItemBinder() {
  }

  public ItemBinder(ItemDecorator itemDecorator) {
    this.itemDecorator = itemDecorator;
  }

  /**
   * @param inflater LayoutInflater to inflate view
   * @param parent The ViewGroup into which the new View will be added after it is bound to
   * an adapter position.
   * @return A new ViewHolder that holds a View for the given {@link ItemBinder}.
   */
  public abstract VH create(LayoutInflater inflater, ViewGroup parent);

  /**
   * @param holder holder The ViewHolder which should be updated to represent the contents of the
   * item at the given position in the data set.
   * @param item The object which holds the data
   * @see #bind(BaseViewHolder, Object, List)
   */
  public abstract void bind(VH holder, M item);

  /**
   * @param item The object from the data set
   * @return boolean value which determines whether the {@link ItemBinder} can bind the {@param
   * item} to the ViewHolder
   */
  public abstract boolean canBindData(Object item);

  /**
   * @param holder holder The ViewHolder which should be updated to represent the contents of the
   * item at the given position in the data set.
   * @param item The object which holds the data
   * @param payloads A non-null list of merged payloads. Can be empty list if requires full
   * update.
   * @see #bind(BaseViewHolder, Object)
   */
  public void bind(VH holder, M item, List payloads) {
    bind(holder, item);
  }

  /**
   * Used to determine the span size for the {@link ItemBinder}.
   * <p>
   * By default the {@link ItemBinder} has the span size as 1. It can be overridden by the child
   * ViewBinders to provide the custom span size.
   * </p>
   *
   * @param maxSpanCount The maximum span count of the {@link GridLayoutManager} used inside the
   * RecyclerView
   */
  public int getSpanSize(int maxSpanCount) {
    return 1;
  }

  ///////////////////////////////////////////
  /////////// Internal API ahead. ///////////
  ///////////////////////////////////////////

  void bindViewHolder(VH holder, M item, boolean isSelected) {
    bind(holder, item);
  }

  void bindViewHolder(VH holder, M item, boolean isSelected, List payloads) {
    bind(holder, item, payloads);
  }

  VH createViewHolder(LayoutInflater inflater, ViewGroup parent) {
    return create(inflater, parent);
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