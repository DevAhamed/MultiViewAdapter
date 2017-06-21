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

package com.ahamed.multiviewadapter;

import com.ahamed.multiviewadapter.util.PositionTypeResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_BOTTOM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_FIRST_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_LAST_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_LEFT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_MIDDLE_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_RIGHT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_TOP;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class) @SuppressWarnings("WrongConstant")
public class PositionTypeResolverTest {

  @Test public void testIsItemOnTopEdge() {
    assertTrue(PositionTypeResolver.isItemOnTopEdge(POSITION_FIRST_ITEM));
    assertTrue(PositionTypeResolver.isItemOnTopEdge(POSITION_TOP));

    assertTrue(PositionTypeResolver.isItemOnTopEdge(POSITION_TOP));
    assertTrue(PositionTypeResolver.isItemOnTopEdge(POSITION_TOP | POSITION_LEFT));
    assertTrue(PositionTypeResolver.isItemOnTopEdge(POSITION_TOP | POSITION_LEFT));
    assertTrue(PositionTypeResolver.isItemOnTopEdge(POSITION_RIGHT | POSITION_TOP));
    assertTrue(PositionTypeResolver.isItemOnTopEdge(POSITION_RIGHT | POSITION_LEFT | POSITION_TOP));
    assertTrue(PositionTypeResolver.isItemOnTopEdge(
        POSITION_RIGHT | POSITION_LEFT | POSITION_BOTTOM | POSITION_TOP));

    assertFalse(PositionTypeResolver.isItemOnTopEdge(POSITION_MIDDLE_ITEM));
    assertFalse(PositionTypeResolver.isItemOnTopEdge(POSITION_LAST_ITEM));
    assertFalse(PositionTypeResolver.isItemOnTopEdge(POSITION_BOTTOM));
  }

  @Test public void testIsItemOnBottomEdge() {
    assertTrue(PositionTypeResolver.isItemOnBottomEdge(POSITION_LAST_ITEM));
    assertTrue(PositionTypeResolver.isItemOnBottomEdge(POSITION_BOTTOM));

    assertTrue(PositionTypeResolver.isItemOnBottomEdge(POSITION_BOTTOM | POSITION_LEFT));
    assertTrue(PositionTypeResolver.isItemOnBottomEdge(POSITION_BOTTOM | POSITION_LEFT));
    assertTrue(PositionTypeResolver.isItemOnBottomEdge(POSITION_RIGHT | POSITION_BOTTOM));
    assertTrue(
        PositionTypeResolver.isItemOnBottomEdge(POSITION_BOTTOM | POSITION_LEFT | POSITION_TOP));
    assertTrue(PositionTypeResolver.isItemOnBottomEdge(
        POSITION_RIGHT | POSITION_LEFT | POSITION_BOTTOM | POSITION_TOP));

    assertFalse(PositionTypeResolver.isItemOnBottomEdge(POSITION_TOP));
    assertFalse(PositionTypeResolver.isItemOnBottomEdge(POSITION_MIDDLE_ITEM));
    assertFalse(PositionTypeResolver.isItemOnBottomEdge(POSITION_FIRST_ITEM));
  }

  @Test public void testIsItemOnLeftEdge() {
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(POSITION_FIRST_ITEM));
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(POSITION_MIDDLE_ITEM));
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(POSITION_LAST_ITEM));

    assertTrue(PositionTypeResolver.isItemOnLeftEdge(POSITION_LEFT));
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(POSITION_TOP | POSITION_LEFT));
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(POSITION_BOTTOM | POSITION_LEFT));
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(POSITION_RIGHT | POSITION_LEFT));
    assertTrue(
        PositionTypeResolver.isItemOnLeftEdge(POSITION_RIGHT | POSITION_LEFT | POSITION_BOTTOM));
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(
        POSITION_RIGHT | POSITION_LEFT | POSITION_BOTTOM | POSITION_TOP));

    assertFalse(PositionTypeResolver.isItemOnLeftEdge(POSITION_RIGHT | POSITION_BOTTOM));
  }

  @Test public void testIsItemOnRightEdge() {
    assertTrue(PositionTypeResolver.isItemOnRightEdge(POSITION_FIRST_ITEM));
    assertTrue(PositionTypeResolver.isItemOnRightEdge(POSITION_MIDDLE_ITEM));
    assertTrue(PositionTypeResolver.isItemOnRightEdge(POSITION_LAST_ITEM));

    assertTrue(PositionTypeResolver.isItemOnRightEdge(POSITION_RIGHT));
    assertTrue(PositionTypeResolver.isItemOnRightEdge(POSITION_TOP | POSITION_RIGHT));
    assertTrue(PositionTypeResolver.isItemOnRightEdge(POSITION_BOTTOM | POSITION_RIGHT));
    assertTrue(PositionTypeResolver.isItemOnRightEdge(POSITION_RIGHT | POSITION_LEFT));
    assertTrue(
        PositionTypeResolver.isItemOnRightEdge(POSITION_RIGHT | POSITION_LEFT | POSITION_BOTTOM));
    assertTrue(PositionTypeResolver.isItemOnLeftEdge(
        POSITION_RIGHT | POSITION_LEFT | POSITION_BOTTOM | POSITION_TOP));

    assertFalse(PositionTypeResolver.isItemOnRightEdge(POSITION_LEFT | POSITION_BOTTOM));
  }
}
