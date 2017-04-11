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

  void getItemOffsets(Rect outRect, int position, @PositionType int positionType);

  void onDraw(Canvas canvas, RecyclerView parent, View child, int position, @PositionType int positionType);

  @Retention(RetentionPolicy.SOURCE) @IntDef({
      POSITION_START, POSITION_MIDDLE, POSITION_END
  }) @interface PositionType {
  }
}