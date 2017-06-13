/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.ahamed.multiviewadapter.util.ItemDecorator;

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
    if (positionType == POSITION_LAST_ITEM) {
      return;
    }
    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight() * 4);
  }

  @Override public void onDraw(Canvas canvas, RecyclerView parent, View child, int position,
      int positionType) {
    if (parent.getLayoutManager() == null) {
      return;
    }
    if (positionType == POSITION_LAST_ITEM) {
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
