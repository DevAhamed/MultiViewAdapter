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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ahamed.multiviewadapter.util.ItemDecorator;

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
