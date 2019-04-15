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

package mva2.extension.decorator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.decorator.Decorator;

/**
 * DividerDecoration is a {@link RecyclerView.ItemDecoration} that can be used as a divider
 * between items of a {@link LinearLayoutManager}. It supports both {@link #HORIZONTAL} and
 * {@link #VERTICAL} orientations.
 *
 * This code has been adapted from official support library's  {@link DividerItemDecoration}
 */
@SuppressWarnings("WeakerAccess") public class DividerDecoration extends Decorator {

  public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
  public static final int VERTICAL = LinearLayout.VERTICAL;

  private final Rect bounds = new Rect();
  private final Drawable divider;
  private int orientation;

  public DividerDecoration(MultiViewAdapter adapter, Context context, int orientation) {
    super(adapter);
    int[] attrs = new int[] { android.R.attr.listDivider };
    final TypedArray a = context.obtainStyledAttributes(attrs);
    divider = a.getDrawable(0);
    a.recycle();
    setOrientation(orientation);
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    int itemPositionType = getPositionType(adapterPosition, parent);
    if (isItemOnBottomEdge(itemPositionType)) {
      return;
    }
    if (orientation == VERTICAL) {
      addToRect(outRect, 0, 0, 0, divider.getIntrinsicHeight());
    } else {
      addToRect(outRect, 0, 0, divider.getIntrinsicHeight(), 0);
    }
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    if (parent.getLayoutManager() == null) {
      return;
    }
    if (isLast(getPositionType(adapterPosition, parent))) {
      return;
    }
    if (orientation == VERTICAL) {
      drawVertical(canvas, parent, child);
    } else {
      drawHorizontal(canvas, parent, child);
    }
  }

  public void setOrientation(int orientation) {
    if (orientation != HORIZONTAL && orientation != VERTICAL) {
      throw new IllegalArgumentException(
          "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
    }
    this.orientation = orientation;
  }

  @SuppressLint("NewApi")
  private void drawHorizontal(Canvas canvas, RecyclerView parent, View child) {
    canvas.save();
    final int top;
    final int bottom;
    if (parent.getClipToPadding()) {
      top = parent.getPaddingTop();
      bottom = parent.getHeight() - parent.getPaddingBottom();
      canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
          bottom);
    } else {
      top = 0;
      bottom = parent.getHeight();
    }

    parent.getLayoutManager().getDecoratedBoundsWithMargins(child, bounds);
    final int right = bounds.right + Math.round(ViewCompat.getTranslationX(child));
    final int left = right - divider.getIntrinsicWidth();
    divider.setBounds(left, top, right, bottom);
    divider.draw(canvas);
    canvas.restore();
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