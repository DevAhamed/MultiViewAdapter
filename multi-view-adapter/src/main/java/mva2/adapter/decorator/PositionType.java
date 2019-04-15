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

import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static mva2.adapter.decorator.PositionType.BOTTOM;
import static mva2.adapter.decorator.PositionType.LEFT;
import static mva2.adapter.decorator.PositionType.MIDDLE;
import static mva2.adapter.decorator.PositionType.RIGHT;
import static mva2.adapter.decorator.PositionType.TOP;

/**
 * PositionType refers to relative position of item in the {@link RecyclerView.LayoutManager}.
 *
 * <p>
 *
 * PositionType is an int value, which is the result of binary 'OR' function. If the item is first
 * element in a {@link GridLayoutManager}, then the item will be on the top and left edge. So the
 * positionTYpe for that item is (LEFT | TOP)
 *
 * <p>
 *
 * Though PositionType can be used independently, it is advised to use it with {@link Decorator}.
 *
 * <p>
 *
 * You can use {@link Decorator} to resolve the position type into more useful information. The
 * {@link Decorator} will return whether the item lies on the particular edge.
 *
 * Example Usage :
 *
 * <pre>
 * {@code
 * boolean isOnTopEdge = Decorator.isItemOnTopEdge(positionType);
 * }
 * </pre>
 *
 * @see Decorator
 */
@Retention(RetentionPolicy.SOURCE) @IntDef({
    TOP, LEFT, MIDDLE, RIGHT, BOTTOM
}) public @interface PositionType {

  /**
   * Denotes that the item/section is lies in the left edge
   */
  int LEFT = 1;

  /**
   * Denotes that the item/section is lies in the top edge
   */
  int TOP = 2;

  /**
   * Denotes that the item/section lies in the right edge
   */
  int RIGHT = 4;

  /**
   * Denotes that the item/section lies in the bottom edge
   */
  int BOTTOM = 8;

  /**
   * Denotes that the item/section lies in the middle
   */
  int MIDDLE = 0;
}