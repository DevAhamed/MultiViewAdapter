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

package dev.ahamed.mva.sample.view.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import dev.ahamed.mva.sample.view.SampleActivity;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.decorator.Decorator;
import mva2.adapter.decorator.SectionPositionType;

public class MovieSectionDecoration extends Decorator {

  private final Rect mBounds = new Rect();
  private final boolean insets;
  private final boolean borders;
  private int decorLeft = SampleActivity.DP_EIGHT;
  private int decorRight = SampleActivity.DP_EIGHT;
  private int decorTop = SampleActivity.DP_FOUR;
  private int decorBottom = SampleActivity.DP_FOUR;
  private Paint bgPaint = new Paint();
  private Paint borderPaint = new Paint();

  public MovieSectionDecoration(MultiViewAdapter adapter, boolean insets, boolean borders) {
    super(adapter);
    this.insets = insets;
    this.borders = borders;
    bgPaint.setColor(Color.parseColor("#EEEEEE"));
    borderPaint.setColor(Color.parseColor("#AFAFAF"));
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    if (!borders && !insets) {
      return;
    }

    int itemPositionType = getPositionType(adapterPosition, parent);
    SectionPositionType sectionPositionType = getSectionPositionType(adapterPosition);

    if (isItemOnBottomEdge(itemPositionType)) {
      addToRect(outRect, 0, 0, 0, decorBottom);
      if (sectionPositionType == SectionPositionType.LAST) {
        addToRect(outRect, 0, 0, 0, decorBottom);
      }
    }
    if (isItemOnTopEdge(itemPositionType)) {
      addToRect(outRect, 0, decorTop, 0, 0);
      if (sectionPositionType == SectionPositionType.FIRST) {
        addToRect(outRect, 0, decorTop, 0, 0);
      }
    }
    if (isItemOnLeftEdge(itemPositionType)) {
      addToRect(outRect, decorLeft, 0, 0, 0);
    }
    if (isItemOnRightEdge(itemPositionType)) {
      addToRect(outRect, 0, 0, decorRight, 0);
    }
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    if (!borders && !insets) {
      return;
    }

    canvas.save();
    parent.getDecoratedBoundsWithMargins(child, mBounds);

    final int topOffset = mBounds.top;
    final int bottomOffset = mBounds.bottom;
    final int leftOffset = mBounds.left;
    final int rightOffset = mBounds.right;

    int top = topOffset;
    int bottom = bottomOffset;
    int left = leftOffset;
    int right = rightOffset;

    int itemPositionType = getPositionType(adapterPosition, parent);
    SectionPositionType sectionPositionType = getSectionPositionType(adapterPosition);

    if (isItemOnBottomEdge(itemPositionType)) {
      int bottomSpace = decorBottom;
      if (sectionPositionType == SectionPositionType.LAST) {
        bottomSpace += decorBottom;
      }
      bottom -= bottomSpace;
    }

    if (isItemOnTopEdge(itemPositionType)) {
      int topSpace = decorTop;
      if (sectionPositionType == SectionPositionType.FIRST) {
        topSpace += decorTop;
      }
      top += topSpace;
    }

    if (isItemOnLeftEdge(itemPositionType)) {
      left += decorLeft;
    }

    if (isItemOnRightEdge(itemPositionType)) {
      right -= decorRight;
    }

    if (isItemOnBottomEdge(itemPositionType)) {
      if (insets) {
        canvas.drawRect(left - decorLeft, bottom, right + decorRight, bottomOffset, bgPaint);
      }
      if (borders) {
        canvas.drawLine(left, bottom, right, bottom, borderPaint);
      }
    }

    if (isItemOnTopEdge(itemPositionType)) {
      if (insets) {
        canvas.drawRect(left - decorLeft, top, right + decorRight, topOffset, bgPaint);
      }
      if (borders) {
        canvas.drawLine(left, top, right, top, borderPaint);
      }
    }

    if (isItemOnLeftEdge(itemPositionType)) {
      if (insets) {
        canvas.drawRect(left, top, leftOffset, bottom, bgPaint);
      }
      if (borders) {
        canvas.drawLine(left, top, left, bottom, borderPaint);
      }
    }

    if (isItemOnRightEdge(itemPositionType)) {
      if (insets) {
        canvas.drawRect(right, top, rightOffset, bottom, bgPaint);
      }
      if (borders) {
        canvas.drawLine(right, top, right, bottom, borderPaint);
      }
    }

    canvas.restore();
  }
}
