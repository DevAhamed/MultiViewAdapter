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

package mva2.adapter.decorator;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.ItemBinder;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.Section;

import static mva2.adapter.decorator.PositionType.BOTTOM;
import static mva2.adapter.decorator.PositionType.LEFT;
import static mva2.adapter.decorator.PositionType.RIGHT;
import static mva2.adapter.decorator.PositionType.TOP;

/**
 * A Decorator allows the application to add a special drawing and layout offset to specific
 * itemviews from the adapter's data set.
 * This can be useful for drawing dividers between items, highlights and more.
 *
 * <p>
 *
 * Decorations will be drawn in the order by which they were added to the {@link ItemBinder} or
 * {@link Section}.
 * Also {@link ItemBinder}'s decoration will be applied only after applying the {@link Section}'s
 * decoration
 */
@SuppressWarnings("unused") public abstract class Decorator {

  private MultiViewAdapter adapter;

  /**
   * Constructor which initialized the Decorator with the adapter object.
   *
   * @param adapter MultiViewAaapter that is attached with this decorator.
   */
  public Decorator(MultiViewAdapter adapter) {
    this.adapter = adapter;
  }

  /**
   * Utility method to add the coordinates to the existing values to the Rect class
   *
   * @param outRect Rect object for which the values are updated
   * @param left    The X coordinate of the left side of the rectangle
   * @param top     The Y coordinate of the top of the rectangle
   * @param right   The X coordinate of the right side of the rectangle
   * @param bottom  The Y coordinate of the bottom of the rectangle
   */
  public void addToRect(Rect outRect, int left, int top, int right, int bottom) {
    outRect.left += left;
    outRect.top += top;
    outRect.right += right;
    outRect.bottom += bottom;
  }

  /**
   * Returns the position type of the item for given adapter position.
   *
   * @param adapterPosition Item position inside the adapter for which position type is calculated
   * @param parent          Parent recyclerview
   *
   * @return Position type of the given item
   *
   * @see PositionType
   */
  public @PositionType int getPositionType(int adapterPosition, RecyclerView parent) {
    return adapter.getPositionType(parent, adapterPosition);
  }

  /**
   * Returns the {@link SectionPositionType} for the given adapter position.
   *
   * @param adapterPosition Position of the item inside the recyclerview
   *
   * @return SectionPositionType for given position
   */
  public SectionPositionType getSectionPositionType(int adapterPosition) {
    return adapter.getSectionPositionType(adapterPosition);
  }

  /**
   * <p>
   * Returns whether the item is first element in the {@link LinearLayoutManager}. Avoid using this
   * method on {@link GridLayoutManager}
   * </p>
   *
   * @see PositionType
   */
  public boolean isFirst(@PositionType int positionType) {
    return isItemOnTopEdge(positionType);
  }

  /**
   * <p>
   * Returns whether the item is on the bottom edge of the {@link RecyclerView}.
   * </p>
   *
   * @see PositionType
   */
  public boolean isItemOnBottomEdge(@PositionType int positionType) {
    return ((positionType & BOTTOM) == BOTTOM);
  }

  /**
   * <p>
   * Returns whether the item is on the left edge of the {@link RecyclerView}.
   * </p>
   *
   * @see PositionType
   */
  public boolean isItemOnLeftEdge(@PositionType int positionType) {
    return ((positionType & LEFT) == LEFT);
  }

  /**
   * <p>
   * Returns whether the item is on the right edge of the {@link RecyclerView}.
   * </p>
   *
   * @see PositionType
   */
  public boolean isItemOnRightEdge(@PositionType int positionType) {
    return ((positionType & RIGHT) == RIGHT);
  }

  /**
   * <p>
   * Returns whether the item is on the top edge of the {@link RecyclerView}.
   * </p>
   *
   * @see PositionType
   */
  public boolean isItemOnTopEdge(@PositionType int positionType) {
    return ((positionType & TOP) == TOP);
  }

  /**
   * <p>
   * Returns whether the item is last element in the {@link LinearLayoutManager}. Avoid using this
   * method on {@link GridLayoutManager}
   * </p>
   *
   * @see PositionType
   */
  public boolean isLast(@PositionType int positionType) {
    return isItemOnBottomEdge(positionType);
  }

  /**
   * Draw any appropriate decorations into the Canvas supplied to the RecyclerView. Any content
   * drawn by this method will be drawn before the item views are drawn, and will thus appear
   * underneath the views.
   *
   * @param canvas          The canvas to draw into
   * @param parent          The recyclerView this decoration is drawing into
   * @param state           The current state of RecyclerView
   * @param child           The child for which item decoration is being drawn
   * @param adapterPosition The item's position inside the recyclerview
   *
   * @see SectionPositionType
   * @see PositionType
   */
  public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    // Write your own implementation
  }

  /**
   * Draw any appropriate decorations into the Canvas supplied to the RecyclerView. Any content
   * drawn by this method will be drawn after the item views are drawn, and will thus appear
   * above the views.
   *
   * @param canvas          The canvas to draw into
   * @param parent          The recyclerView this decoration is drawing into
   * @param state           The current state of RecyclerView
   * @param child           The child for which item decoration is being drawn
   * @param adapterPosition The item's position inside the recyclerview
   *
   * @see SectionPositionType
   * @see PositionType
   */
  public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    // Write your own implementation
  }

  /**
   * Retrieve item offsets for the given position.
   *
   * <p>
   *
   * Each field of <code>rectUtil</code> specifies the number of pixels that the item view should be
   * inset by, similar to padding or margin.
   * The default implementation sets the bounds of rectUtil to 0 and returns.
   *
   * <p>
   *
   * If this ItemDecoration does affect the positioning of item views, it should set
   * any of four fields of <code>rectUtil</code> (left, top, right, bottom).
   *
   * @param outRect         Rect to receive the output.
   * @param view            The child view to decorate
   * @param parent          RecyclerView this ItemDecoration is decorating
   * @param state           The current state of RecyclerView.
   * @param adapterPosition The item's position inside the recyclerview
   *
   * @see SectionPositionType
   * @see PositionType
   */
  public abstract void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition);
}
