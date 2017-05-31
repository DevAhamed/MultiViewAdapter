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

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.ahamed.multiviewadapter.annotation.PositionType;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.ahamed.multiviewadapter.util.PositionTypeResolver;

public class GridInsetDecoration implements ItemDecorator {

  private int insetInPixel;

  public GridInsetDecoration(int insetInDp) {
    this.insetInPixel = insetInDp;
  }

  @Override public void getItemOffsets(Rect outRect, int position, @PositionType int positionType) {

    int top = insetInPixel;
    int left = insetInPixel;
    int right = insetInPixel;
    int bottom = insetInPixel;

    if (PositionTypeResolver.isItemOnTopEdge(positionType)) {
      top *= 2;
    }

    if (PositionTypeResolver.isItemOnLeftEdge(positionType)) {
      left *= 2;
    }

    if (PositionTypeResolver.isItemOnRightEdge(positionType)) {
      right *= 2;
    }

    if (PositionTypeResolver.isItemOnBottomEdge(positionType)) {
      bottom *= 2;
    }

    outRect.set(left, top, right, bottom);
  }

  @Override public void onDraw(Canvas canvas, RecyclerView parent, View child, int position,
      @PositionType int positionType) {
    // Just the insets
  }
}
