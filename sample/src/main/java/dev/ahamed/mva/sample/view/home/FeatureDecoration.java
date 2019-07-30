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

package dev.ahamed.mva.sample.view.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import dev.ahamed.mva.sample.R;
import mva3.adapter.MultiViewAdapter;
import mva3.adapter.decorator.Decorator;

public class FeatureDecoration extends Decorator {

  private final int inset;
  private Paint borderPaint = new Paint();
  private final Rect mBounds = new Rect();

  FeatureDecoration(MultiViewAdapter adapter, Context context, int inset) {
    super(adapter);
    this.inset = inset;
    borderPaint.setColor(ContextCompat.getColor(context, R.color.color_divider));
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    addToRect(outRect, 0, 0, 0, inset);
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    canvas.save();
    parent.getDecoratedBoundsWithMargins(child, mBounds);

    final int bottom = mBounds.bottom;
    final int left = mBounds.left;
    final int right = mBounds.right;
    final int top = bottom - inset;

    canvas.drawLine(left, top, right, top, borderPaint);
    canvas.drawLine(left, bottom, right, bottom, borderPaint);

    canvas.restore();
  }
}
