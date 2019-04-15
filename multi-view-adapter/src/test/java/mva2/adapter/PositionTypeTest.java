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

package mva2.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.decorator.Decorator;
import mva2.adapter.decorator.SectionPositionType;
import org.junit.Test;
import org.mockito.Mockito;

import static android.widget.LinearLayout.VERTICAL;
import static mva2.adapter.decorator.PositionType.BOTTOM;
import static mva2.adapter.decorator.PositionType.LEFT;
import static mva2.adapter.decorator.PositionType.MIDDLE;
import static mva2.adapter.decorator.PositionType.RIGHT;
import static mva2.adapter.decorator.PositionType.TOP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PositionTypeTest extends BaseTest {

  @Test public void positionType_linear() {
    LinearLayoutManager layoutManager = Mockito.mock(LinearLayoutManager.class);
    when(recyclerView.getLayoutManager()).thenReturn(layoutManager);
    when(layoutManager.getReverseLayout()).thenReturn(false);
    when(layoutManager.getOrientation()).thenReturn(VERTICAL);

    Decorator sectionDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
      }
    };

    assertEquals(TOP | LEFT | RIGHT | BOTTOM, sectionDecorator.getPositionType(0, recyclerView));
    assertEquals(TOP | LEFT | RIGHT, sectionDecorator.getPositionType(1, recyclerView));
    assertEquals(LEFT | RIGHT, sectionDecorator.getPositionType(6, recyclerView));
    assertEquals(BOTTOM | LEFT | RIGHT, sectionDecorator.getPositionType(18, recyclerView));
    assertEquals(TOP | LEFT | RIGHT, sectionDecorator.getPositionType(19, recyclerView));
    assertEquals(LEFT | RIGHT, sectionDecorator.getPositionType(21, recyclerView));
    assertEquals(LEFT | RIGHT, sectionDecorator.getPositionType(47, recyclerView));
    assertEquals(LEFT | TOP | RIGHT | BOTTOM, sectionDecorator.getPositionType(56, recyclerView));
  }

  @Test public void positionType_grid() {
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    GridLayoutManager layoutManager = Mockito.mock(GridLayoutManager.class);
    when(recyclerView.getLayoutManager()).thenReturn(layoutManager);
    when(layoutManager.getReverseLayout()).thenReturn(false);
    when(layoutManager.getOrientation()).thenReturn(VERTICAL);
    when(layoutManager.getSpanSizeLookup()).thenReturn(spanSizeLookup);
    when(layoutManager.getSpanCount()).thenReturn(6);

    Decorator sectionDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
      }
    };

    assertEquals(TOP | LEFT | RIGHT | BOTTOM, sectionDecorator.getPositionType(0, recyclerView));
    assertEquals(LEFT | TOP, sectionDecorator.getPositionType(1, recyclerView));
    assertEquals(TOP, sectionDecorator.getPositionType(2, recyclerView));
    assertEquals(TOP, sectionDecorator.getPositionType(3, recyclerView));
    assertEquals(TOP | RIGHT, sectionDecorator.getPositionType(6, recyclerView));
    assertEquals(LEFT, sectionDecorator.getPositionType(7, recyclerView));
    assertEquals(MIDDLE, sectionDecorator.getPositionType(8, recyclerView));
    assertEquals(MIDDLE, sectionDecorator.getPositionType(9, recyclerView));
    assertEquals(MIDDLE, sectionDecorator.getPositionType(11, recyclerView));
    assertEquals(RIGHT, sectionDecorator.getPositionType(12, recyclerView));
    assertEquals(LEFT | BOTTOM, sectionDecorator.getPositionType(13, recyclerView));
    assertEquals(BOTTOM, sectionDecorator.getPositionType(14, recyclerView));
    assertEquals(RIGHT | BOTTOM, sectionDecorator.getPositionType(18, recyclerView));
    assertEquals(TOP | LEFT | RIGHT, sectionDecorator.getPositionType(19, recyclerView));
    assertEquals(LEFT, sectionDecorator.getPositionType(20, recyclerView));
    assertEquals(MIDDLE, sectionDecorator.getPositionType(21, recyclerView));
    assertEquals(RIGHT, sectionDecorator.getPositionType(22, recyclerView));
    assertEquals(LEFT, sectionDecorator.getPositionType(23, recyclerView));
    assertEquals(MIDDLE, sectionDecorator.getPositionType(24, recyclerView));
    assertEquals(RIGHT, sectionDecorator.getPositionType(25, recyclerView));
    assertEquals(LEFT | BOTTOM, sectionDecorator.getPositionType(26, recyclerView));
    assertEquals(BOTTOM, sectionDecorator.getPositionType(27, recyclerView));
    assertEquals(RIGHT | BOTTOM, sectionDecorator.getPositionType(28, recyclerView));
  }

  @Test public void positionType_section() {
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    GridLayoutManager layoutManager = Mockito.mock(GridLayoutManager.class);
    when(recyclerView.getLayoutManager()).thenReturn(layoutManager);
    when(layoutManager.getReverseLayout()).thenReturn(false);
    when(layoutManager.getOrientation()).thenReturn(VERTICAL);
    when(layoutManager.getSpanSizeLookup()).thenReturn(spanSizeLookup);
    when(layoutManager.getSpanCount()).thenReturn(6);

    Decorator sectionDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
      }
    };

    assertEquals(SectionPositionType.FIRST, sectionDecorator.getSectionPositionType(0));
    assertEquals(SectionPositionType.MIDDLE, sectionDecorator.getSectionPositionType(1));
    assertEquals(SectionPositionType.MIDDLE, sectionDecorator.getSectionPositionType(10));
    assertEquals(SectionPositionType.MIDDLE, sectionDecorator.getSectionPositionType(18));
    assertEquals(SectionPositionType.MIDDLE, sectionDecorator.getSectionPositionType(19));
    assertEquals(SectionPositionType.MIDDLE, sectionDecorator.getSectionPositionType(20));
    assertEquals(SectionPositionType.MIDDLE, sectionDecorator.getSectionPositionType(50));
    assertEquals(SectionPositionType.MIDDLE, sectionDecorator.getSectionPositionType(56));

    // TreeSection always returns MIDDLE as the sectionPositionType
    // Remove the TreeSection from adapter and check for the last item
    adapter.removeAllSections();

    adapter.addSection(itemSection1);
    adapter.addSection(listSection1);
    adapter.addSection(headerSection1);
    adapter.addSection(nestedSection1);
    assertEquals(SectionPositionType.LAST, sectionDecorator.getSectionPositionType(48));
  }
}
