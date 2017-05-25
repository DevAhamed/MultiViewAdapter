package com.ahamed.sample.common.decorator;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ahamed.multiviewadapter.ItemDecorator;

public class ArticleItemDecorator implements ItemDecorator {

  private final Rect mBounds = new Rect();
  private Paint myPaint = new Paint();

  public ArticleItemDecorator() {
    myPaint.setColor(Color.rgb(240, 240, 240));
  }

  @Override public void getItemOffsets(Rect outRect, int position, int positionType) {
    outRect.set(0, 0, 0, 16);
  }

  @Override public void onDraw(Canvas canvas, RecyclerView parent, View child, int position,
      int positionType) {
    if (parent.getLayoutManager() == null) {
      return;
    }
    draw(canvas, parent, child);
  }

  @SuppressLint("NewApi") private void draw(Canvas canvas, RecyclerView parent, View child) {
    canvas.save();
    final int left;
    final int right;
    if (parent.getClipToPadding()) {
      left = parent.getPaddingLeft();
      right = parent.getWidth() - parent.getPaddingRight();
      canvas.clipRect(left, parent.getPaddingTop(), right,
          parent.getHeight() - parent.getPaddingBottom());
    } else {
      left = 0;
      right = parent.getWidth();
    }

    parent.getDecoratedBoundsWithMargins(child, mBounds);
    final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
    final int top = bottom - 16;

    canvas.drawRect(left, top, right, bottom, myPaint);
    canvas.restore();
  }
}
