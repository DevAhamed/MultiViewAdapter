package com.ahamed.sample.common.decorator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ahamed.multiviewadapter.ItemDecorator;

public class ThickItemDecorator implements ItemDecorator {

  private static final int[] ATTRS = new int[] { android.R.attr.listDivider };
  private final Rect mBounds = new Rect();
  private Drawable mDivider;

  public ThickItemDecorator(Context context) {
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    mDivider = a.getDrawable(0);
    a.recycle();
  }

  @Override public void getItemOffsets(Rect outRect, int position, int positionType) {
    if (positionType == POSITION_END) {
      return;
    }
    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight() * 4);
  }

  @Override public void onDraw(Canvas canvas, RecyclerView parent, View child, int position,
      int positionType) {
    if (parent.getLayoutManager() == null) {
      return;
    }
    if (positionType == POSITION_END) {
      return;
    }
    drawVertical(canvas, parent, child);
  }

  @SuppressLint("NewApi")
  private void drawVertical(Canvas canvas, RecyclerView parent, View child) {
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
    final int top = bottom - mDivider.getIntrinsicHeight() * 4;
    mDivider.setBounds(left, top, right, bottom);
    mDivider.draw(canvas);
    canvas.restore();
  }
}
