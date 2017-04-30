package com.ahamed.multiviewadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ItemDecorator {

  /**
   * Denotes that the item is the first element in the {@link DataListManager} by the order of
   * display
   */
  int POSITION_START = 1;

  /**
   * Denotes that the item is neither first element nor last element in the {@link DataListManager}.
   */
  int POSITION_MIDDLE = 2;

  /**
   * Denotes that the item is the last element in the {@link DataListManager} by the order of
   * display
   */
  int POSITION_END = 3;

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

  /**
   * PositionType can be used to resolve whether the item is first/last element in the {@link
   * DataListManager} in the order of display.
   *
   * <p>ie., if the LinearLayoutManager has reverseLayout as true, then the first element in the
   * {@link DataListManager} will have the {@link PositionType} as  PositionType.POSITION_END</p>
   *
   * <p>The item can be a first element, last element or middle element(for all other
   * positions).</p>
   */
  @Retention(RetentionPolicy.SOURCE) @IntDef({
      POSITION_START, POSITION_MIDDLE, POSITION_END
  }) @interface PositionType {
  }
}