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

import android.graphics.Rect;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SpanSizeLookup;
import mva2.adapter.TestAdapter;
import mva2.adapter.testconfig.AdapterDataObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static mva2.adapter.decorator.PositionType.BOTTOM;
import static mva2.adapter.decorator.PositionType.LEFT;
import static mva2.adapter.decorator.PositionType.MIDDLE;
import static mva2.adapter.decorator.PositionType.RIGHT;
import static mva2.adapter.decorator.PositionType.TOP;

@RunWith(MockitoJUnitRunner.class) @SuppressWarnings("WrongConstant")
public class PositionTypeResolverTest {

  @Mock AdapterDataObserver adapterDataObserver;
  @Mock SpanSizeLookup spanSizeLookup;

  private Decorator decorator;

  @Test public void testIsFirst() {
    assertTrue(decorator.isFirst(TOP));
    assertTrue(decorator.isFirst(TOP | LEFT));
    assertTrue(decorator.isFirst(TOP | LEFT));
    assertTrue(decorator.isFirst(RIGHT | TOP));
    assertTrue(decorator.isFirst(RIGHT | LEFT | TOP));
    assertTrue(decorator.isFirst(RIGHT | LEFT | BOTTOM | TOP));

    assertFalse(decorator.isFirst(MIDDLE));
    assertFalse(decorator.isFirst(BOTTOM));
  }

  @Test public void testIsItemOnBottomEdge() {
    assertTrue(decorator.isItemOnBottomEdge(BOTTOM));
    assertTrue(decorator.isItemOnBottomEdge(BOTTOM));

    assertTrue(decorator.isItemOnBottomEdge(BOTTOM | LEFT));
    assertTrue(decorator.isItemOnBottomEdge(BOTTOM | LEFT));
    assertTrue(decorator.isItemOnBottomEdge(RIGHT | BOTTOM));
    assertTrue(decorator.isItemOnBottomEdge(BOTTOM | LEFT | TOP));
    assertTrue(decorator.isItemOnBottomEdge(RIGHT | LEFT | BOTTOM | TOP));

    assertFalse(decorator.isItemOnBottomEdge(TOP));
    assertFalse(decorator.isItemOnBottomEdge(MIDDLE));
  }

  @Test public void testIsItemOnLeftEdge() {
    assertTrue(decorator.isItemOnLeftEdge(LEFT));
    assertTrue(decorator.isItemOnLeftEdge(TOP | LEFT));
    assertTrue(decorator.isItemOnLeftEdge(BOTTOM | LEFT));
    assertTrue(decorator.isItemOnLeftEdge(RIGHT | LEFT));
    assertTrue(decorator.isItemOnLeftEdge(RIGHT | LEFT | BOTTOM));
    assertTrue(decorator.isItemOnLeftEdge(RIGHT | LEFT | BOTTOM | TOP));

    assertFalse(decorator.isItemOnLeftEdge(RIGHT | BOTTOM));
  }

  @Test public void testIsItemOnRightEdge() {
    assertTrue(decorator.isItemOnRightEdge(RIGHT));
    assertTrue(decorator.isItemOnRightEdge(TOP | RIGHT));
    assertTrue(decorator.isItemOnRightEdge(BOTTOM | RIGHT));
    assertTrue(decorator.isItemOnRightEdge(RIGHT | LEFT));
    assertTrue(decorator.isItemOnRightEdge(RIGHT | LEFT | BOTTOM));
    assertTrue(decorator.isItemOnLeftEdge(RIGHT | LEFT | BOTTOM | TOP));

    assertFalse(decorator.isItemOnRightEdge(LEFT | BOTTOM));
  }

  @Test public void testIsItemOnTopEdge() {
    assertTrue(decorator.isItemOnTopEdge(TOP));
    assertTrue(decorator.isItemOnTopEdge(TOP | LEFT));
    assertTrue(decorator.isItemOnTopEdge(TOP | LEFT));
    assertTrue(decorator.isItemOnTopEdge(RIGHT | TOP));
    assertTrue(decorator.isItemOnTopEdge(RIGHT | LEFT | TOP));
    assertTrue(decorator.isItemOnTopEdge(RIGHT | LEFT | BOTTOM | TOP));

    assertFalse(decorator.isItemOnTopEdge(MIDDLE));
    assertFalse(decorator.isItemOnTopEdge(BOTTOM));
  }

  @Test public void testIsLast() {
    assertTrue(decorator.isLast(BOTTOM));
    assertTrue(decorator.isLast(BOTTOM));

    assertTrue(decorator.isLast(BOTTOM | LEFT));
    assertTrue(decorator.isLast(BOTTOM | LEFT));
    assertTrue(decorator.isLast(RIGHT | BOTTOM));
    assertTrue(decorator.isLast(BOTTOM | LEFT | TOP));
    assertTrue(decorator.isLast(RIGHT | LEFT | BOTTOM | TOP));

    assertFalse(decorator.isLast(TOP));
    assertFalse(decorator.isLast(MIDDLE));
  }

  @Before public void setUpTest() {
    MockitoAnnotations.initMocks(this);
    decorator = new Decorator(new TestAdapter(adapterDataObserver, spanSizeLookup)) {

      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
        // No-op
      }
    };
  }
}
