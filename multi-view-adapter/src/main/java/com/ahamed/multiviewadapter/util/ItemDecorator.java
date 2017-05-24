package com.ahamed.multiviewadapter.util;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.annotation.PositionType;

/**
 * Interface to draw the item decoration for each view binder
 */
public interface ItemDecorator {

  /**
   * Denotes that the item is the first element in the {@link DataListManager} by the order of
   * display
   */
  int POSITION_FIRST_ITEM = 11;

  /**
   * @deprecated Use {@link ItemDecorator#POSITION_FIRST_ITEM} instead.
   */
  @Deprecated int POSITION_START = POSITION_FIRST_ITEM;

  /**
   * Denotes that the item is neither first element nor last element in the {@link DataListManager}.
   */
  int POSITION_MIDDLE_ITEM = 12;

  /**
   * Denotes that the item is the last element in the {@link DataListManager} by the order of
   * display
   */
  int POSITION_LAST_ITEM = 13;

  /**
   * @deprecated Use {@link ItemDecorator#POSITION_LAST_ITEM} instead.
   */
  @Deprecated int POSITION_END = POSITION_LAST_ITEM;

  /**
   * Denotes that the item is lies in the left edge of the grid
   */
  int POSITION_LEFT = 1;

  /**
   * Denotes that the item is lies in the top edge of the grid
   */
  int POSITION_TOP = 2;

  /**
   * Denotes that the item is lies in the middle of the grid
   */
  int POSITION_MIDDLE = 4;

  /**
   * Denotes that the item is lies in the right edge of the grid
   */
  int POSITION_RIGHT = 8;

  /**
   * Denotes that the item is lies in the bottom edge of the grid
   */
  int POSITION_BOTTOM = 16;

  /**
   * Retrieve any offsets for the given position. Each field of <code>outRect</code> specifies
   * the number of pixels that the item view should be inset by, similar to padding or margin.
   * The default implementation sets the bounds of outRect to 0 and returns.
   *
   * <p>
   * If this ItemDecoration does not affect the positioning of item views, it should set
   * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
   * before returning.
   *
   * @param outRect Rect to receive the output.
   * @param position index of the element inside the data manager
   * @param positionType denotes whether the item's position. Check {@link PositionType} for more
   * info
   */
  void getItemOffsets(Rect outRect, int position, @PositionType int positionType);

  /**
   * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
   * Any content drawn by this method will be drawn before the item views are drawn,
   * and will thus appear underneath the views.
   *
   * @param canvas Canvas to draw into
   * @param parent RecyclerView this ItemDecoration is drawing into
   * @param child The child for which item decoration is being drawn
   * @param position index of the element inside the data manager
   * @param positionType denotes whether the item's position. Check {@link PositionType} for more
   * info
   */
  void onDraw(Canvas canvas, RecyclerView parent, View child, int position,
      @PositionType int positionType);
}