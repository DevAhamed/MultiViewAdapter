package com.ahamed.multiviewadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ItemDecorator {

  int POSITION_START = 1;
  int POSITION_MIDDLE = 2;
  int POSITION_END = 3;

  /**
   * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
   * the number of pixels that the item view should be inset by, similar to padding or margin.
   * The default implementation sets the bounds of outRect to 0 and returns.
   *
   * <p>
   * If this ItemDecoration does not affect the positioning of item views, it should set
   * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
   * before returning.
   *
   * <p>
   * If you need to access Adapter for additional data, you can call
   * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
   * View.
   *
   * @param outRect Rect to receive the output.
   * @param position Position of the item inside the {@link DataListManager}
   * @param positionType Represents the {@link PositionType} of the item.
   */
  void getItemOffsets(Rect outRect, int position, @PositionType int positionType);

  void onDraw(Canvas canvas, RecyclerView parent, View child, int position,
      @PositionType int positionType);

  @Retention(RetentionPolicy.SOURCE) @IntDef({
      POSITION_START, POSITION_MIDDLE, POSITION_END
  }) @interface PositionType {
  }
}