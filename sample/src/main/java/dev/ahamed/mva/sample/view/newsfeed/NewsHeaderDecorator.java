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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.view.SampleActivity;
import mva3.adapter.MultiViewAdapter;
import mva3.adapter.decorator.Decorator;

public class NewsHeaderDecorator extends Decorator {

  private final Rect mBounds = new Rect();
  private final int offsetInPixels;
  private Paint dividerPaint = new Paint();

  NewsHeaderDecorator(MultiViewAdapter adapter, Context context) {
    super(adapter);
    int color = ContextCompat.getColor(context, R.color.color_divider);
    dividerPaint.setColor(color);
    dividerPaint.setStrokeWidth(SampleActivity.DP);
    offsetInPixels = SampleActivity.EIGHT_DP;
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    addToRect(outRect, 0, offsetInPixels, 0, 0);
  }

  @Override public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
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
    final int top = mBounds.top + Math.round(child.getTranslationY());
    final int bottom = top + offsetInPixels;

    canvas.drawLine(left, top, right, top, dividerPaint);
    canvas.drawLine(left, bottom, right, bottom, dividerPaint);
    canvas.restore();
  }
}
