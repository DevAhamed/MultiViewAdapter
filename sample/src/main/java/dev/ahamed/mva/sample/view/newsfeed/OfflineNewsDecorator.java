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

package dev.ahamed.mva.sample.view.newsfeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.decorator.Decorator;

public class OfflineNewsDecorator extends Decorator {

  private final Rect bounds = new Rect();
  private Paint borderPaint = new Paint();
  private final Drawable divider;

  public OfflineNewsDecorator(MultiViewAdapter adapter, Context context) {
    super(adapter);
    int[] attrs = new int[] { android.R.attr.listDivider };
    final TypedArray a = context.obtainStyledAttributes(attrs);
    divider = a.getDrawable(0);
    a.recycle();
    borderPaint.setColor(Color.parseColor("#AFAFAF"));
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    if (parent.getLayoutManager() == null) {
      return;
    }
    if (isLast(getPositionType(adapterPosition, parent))) {
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

    parent.getDecoratedBoundsWithMargins(child, bounds);
    final int bottom = bounds.bottom + Math.round(ViewCompat.getTranslationY(child));
    final int top = bottom - divider.getIntrinsicHeight();
    divider.setBounds(left, top, right, bottom);
    divider.draw(canvas);
    canvas.restore();
  }
}
