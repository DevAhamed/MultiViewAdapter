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

import static dev.ahamed.mva.sample.view.SampleActivity.EIGHT_DP;

public class NewsSectionDecorator extends Decorator {

  private final Rect mBounds = new Rect();
  private Paint dividerPaint = new Paint();

  NewsSectionDecorator(MultiViewAdapter adapter, Context context) {
    super(adapter);
    dividerPaint.setColor(ContextCompat.getColor(context, R.color.color_divider));
    dividerPaint.setStrokeWidth(SampleActivity.TWO_DP);
    dividerPaint.setStyle(Paint.Style.STROKE);
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    addToRect(outRect, EIGHT_DP, EIGHT_DP, EIGHT_DP, EIGHT_DP);
  }

  @Override public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    canvas.save();

    parent.getDecoratedBoundsWithMargins(child, mBounds);

    final int left = mBounds.left + EIGHT_DP;
    final int right = mBounds.right - EIGHT_DP;
    final int bottom = mBounds.bottom - EIGHT_DP;
    final int top = mBounds.top + EIGHT_DP;

    canvas.drawRoundRect(left, top, right, bottom, EIGHT_DP, EIGHT_DP, dividerPaint);
    canvas.restore();
  }
}
