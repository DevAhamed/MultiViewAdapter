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
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import mva2.adapter.decorator.Decorator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static android.widget.LinearLayout.VERTICAL;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class DecorationTest extends BaseTest {

  @Test public void getItemOffset_linear_check() {
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    LinearLayoutManager layoutManager = Mockito.mock(LinearLayoutManager.class);
    when(recyclerView.getLayoutManager()).thenReturn(layoutManager);
    when(layoutManager.getReverseLayout()).thenReturn(false);
    when(layoutManager.getOrientation()).thenReturn(VERTICAL);

    Decorator sectionDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
        int positionType = adapter.getPositionType(parent, adapterPosition);
        if (isItemOnBottomEdge(positionType)) {
          addToRect(outRect, 0, 0, 0, 10);
        }
        if (isItemOnTopEdge(positionType)) {
          addToRect(outRect, 0, 10, 0, 0);
        }
        sectionDecoratorCapture.captureOffsets(outRect.left, outRect.top, outRect.right,
            outRect.bottom);
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
      }
    };

    Decorator headerDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
        addToRect(outRect, 10, 10, 10, 10);
        headerDecoratorCapture.captureOffsets(outRect.left, outRect.top, outRect.right,
            outRect.bottom);
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
      }
    };

    Decorator testItemDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
        int positionType = adapter.getPositionType(parent, adapterPosition);
        if (isItemOnLeftEdge(positionType)) {
          addToRect(outRect, 10, 0, 0, 0);
        }
        if (isItemOnRightEdge(positionType)) {
          addToRect(outRect, 0, 0, 10, 0);
        }
        testItemDecoratorCapture.captureOffsets(outRect.left, outRect.top, outRect.right,
            outRect.bottom);
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
      }
    };

    listSection1.addDecorator(sectionDecorator);
    nestedSection1.addDecorator(sectionDecorator);
    headerBinder.addDecorator(headerDecorator);
    testItemBinder.addDecorator(testItemDecorator);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 0);
    verify(headerDecoratorCapture).captureOffsets(10, 10, 10, 10);

    clearInvocations(headerDecoratorCapture);

    // Position 1 is an TestItem
    // It has both section decorator and binder decorator
    // Section decorators are called first
    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 1);
    verify(sectionDecoratorCapture).captureOffsets(0, 10, 0, 0);
    verify(testItemDecoratorCapture).captureOffsets(10, 10, 10, 0);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 2);
    verify(sectionDecoratorCapture).captureOffsets(0, 0, 0, 0);
    verify(testItemDecoratorCapture).captureOffsets(10, 0, 10, 0);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 12);
    verify(sectionDecoratorCapture).captureOffsets(0, 0, 0, 0);
    verify(testItemDecoratorCapture).captureOffsets(10, 0, 10, 0);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 18);
    verify(sectionDecoratorCapture).captureOffsets(0, 0, 0, 10);
    verify(testItemDecoratorCapture).captureOffsets(10, 0, 10, 10);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 19);
    verify(sectionDecoratorCapture, never()).captureOffsets(anyInt(), anyInt(), anyInt(), anyInt());
    verify(testItemDecoratorCapture, never()).captureOffsets(anyInt(), anyInt(), anyInt(),
        anyInt());
    verify(headerDecoratorCapture).captureOffsets(10, 10, 10, 10);

    clearInvocations(headerDecoratorCapture);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 20);
    verify(testItemDecoratorCapture).captureOffsets(10, 0, 10, 0);

    clearInvocations(testItemDecoratorCapture);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 29);
    verify(sectionDecoratorCapture).captureOffsets(0, 10, 0, 10);
    verify(headerDecoratorCapture).captureOffsets(10, 20, 10, 20);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(headerDecoratorCapture);

    adapter.getDecorationOffset(new Rect(), child, recyclerView, state, 39);
    verify(sectionDecoratorCapture).captureOffsets(0, 10, 0, 0);
    verify(headerDecoratorCapture).captureOffsets(10, 20, 10, 10);
  }

  @Test public void drawDecoration_linear_check() {
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    Decorator sectionDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
        super.onDraw(canvas, parent, state, child, adapterPosition);
        sectionDecoratorCapture.captureDrawing(adapterPosition);
      }
    };

    Decorator headerDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
        super.onDraw(canvas, parent, state, child, adapterPosition);
        headerDecoratorCapture.captureDrawing(adapterPosition);
      }
    };

    Decorator testItemDecorator = new Decorator(adapter) {
      @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
          @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
      }

      @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state, View child, int adapterPosition) {
        super.onDraw(canvas, parent, state, child, adapterPosition);
        testItemDecoratorCapture.captureDrawing(adapterPosition);
      }
    };

    listSection1.addDecorator(sectionDecorator);
    nestedSection1.addDecorator(sectionDecorator);
    headerBinder.addDecorator(headerDecorator);
    testItemBinder.addDecorator(testItemDecorator);

    adapter.drawDecoration(canvas, recyclerView, state, child, 0);
    verify(headerDecoratorCapture).captureDrawing(0);

    clearInvocations(headerDecoratorCapture);

    // Position 1 is an TestItem
    // It has both section decorator and binder decorator
    // Section decorators are called first
    adapter.drawDecoration(canvas, recyclerView, state, child, 1);
    verify(sectionDecoratorCapture).captureDrawing(1);
    verify(testItemDecoratorCapture).captureDrawing(1);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.drawDecoration(canvas, recyclerView, state, child, 2);
    verify(sectionDecoratorCapture).captureDrawing(2);
    verify(testItemDecoratorCapture).captureDrawing(2);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.drawDecoration(canvas, recyclerView, state, child, 12);
    verify(sectionDecoratorCapture).captureDrawing(12);
    verify(testItemDecoratorCapture).captureDrawing(12);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.drawDecoration(canvas, recyclerView, state, child, 18);
    verify(sectionDecoratorCapture).captureDrawing(18);
    verify(testItemDecoratorCapture).captureDrawing(18);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(testItemDecoratorCapture);

    adapter.drawDecoration(canvas, recyclerView, state, child, 19);
    verify(sectionDecoratorCapture, never()).captureDrawing(anyInt());
    verify(testItemDecoratorCapture, never()).captureDrawing(anyInt());
    verify(headerDecoratorCapture).captureDrawing(19);

    clearInvocations(headerDecoratorCapture);

    adapter.drawDecoration(canvas, recyclerView, state, child, 20);
    verify(testItemDecoratorCapture).captureDrawing(20);

    clearInvocations(testItemDecoratorCapture);

    adapter.drawDecoration(canvas, recyclerView, state, child, 29);
    verify(sectionDecoratorCapture).captureDrawing(29);
    verify(headerDecoratorCapture).captureDrawing(29);

    clearInvocations(sectionDecoratorCapture);
    clearInvocations(headerDecoratorCapture);

    adapter.drawDecoration(canvas, recyclerView, state, child, 39);
    verify(sectionDecoratorCapture).captureDrawing(39);
    verify(headerDecoratorCapture).captureDrawing(39);
  }
}
