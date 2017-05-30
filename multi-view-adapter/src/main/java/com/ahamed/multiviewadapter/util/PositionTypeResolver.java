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

package com.ahamed.multiviewadapter.util;

import com.ahamed.multiviewadapter.annotation.PositionType;

import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_BOTTOM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_FIRST_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_LAST_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_LEFT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_MIDDLE_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_RIGHT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_TOP;

public class PositionTypeResolver {

  private PositionTypeResolver() {
    // No init
  }

  public static boolean isItemOnTopEdge(@PositionType int positionType) {
    return (positionType == POSITION_FIRST_ITEM) || ((positionType & POSITION_TOP) == POSITION_TOP);
  }

  public static boolean isItemOnLeftEdge(@PositionType int positionType) {
    return ((positionType & POSITION_LEFT) == POSITION_LEFT) || (positionType
        == POSITION_FIRST_ITEM) || (positionType == POSITION_MIDDLE_ITEM) || (positionType
        == POSITION_LAST_ITEM);
  }

  public static boolean isItemOnRightEdge(@PositionType int positionType) {
    return ((positionType & POSITION_RIGHT) == POSITION_RIGHT) || (positionType
        == POSITION_FIRST_ITEM) || (positionType == POSITION_MIDDLE_ITEM) || (positionType
        == POSITION_LAST_ITEM);
  }

  public static boolean isItemOnBottomEdge(@PositionType int positionType) {
    return ((positionType & POSITION_BOTTOM) == POSITION_BOTTOM) || (positionType
        == POSITION_LAST_ITEM);
  }
}
