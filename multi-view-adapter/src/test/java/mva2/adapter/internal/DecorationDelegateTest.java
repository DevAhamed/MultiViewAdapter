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

package mva2.adapter.internal;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class DecorationDelegateTest {

  @Mock RecyclerView recyclerView;
  @Mock RecyclerView.State state;
  @Mock View child;
  @Mock Rect outRect;
  @Mock Canvas canvas;
  @Mock TestAdapter testAdapter;
  private DecorationDelegate decorationDelegate;

  @Test public void getItemOffsets_test() {
    when(recyclerView.getChildAdapterPosition(child)).thenReturn(1);

    testAdapter.getItemDecoration().getItemOffsets(outRect, child, recyclerView, state);
    verify(testAdapter).getDecorationOffset(outRect, child, recyclerView, state, 1);
  }

  @Test public void getItemOffsets_invalid_adapterPosition() {
    when(recyclerView.getChildAdapterPosition(child)).thenReturn(-1);

    testAdapter.getItemDecoration().getItemOffsets(outRect, child, recyclerView, state);
    verify(testAdapter, never()).getDecorationOffset(outRect, child, recyclerView, state, -1);
  }

  @Test public void onDraw_invalid_adapterPosition() {
    when(recyclerView.getChildAt(anyInt())).thenReturn(child);
    when(recyclerView.getChildCount()).thenReturn(3);
    when(recyclerView.getChildAdapterPosition(child)).thenReturn(-1);

    testAdapter.getItemDecoration().onDraw(canvas, recyclerView, state);
    verify(testAdapter, never()).drawDecoration(canvas, recyclerView, state, child, -1);
  }

  @Test public void onDraw_test() {
    when(recyclerView.getChildCount()).thenReturn(10);
    when(recyclerView.getChildAdapterPosition(child)).thenReturn(0);
    when(recyclerView.getChildAt(anyInt())).thenReturn(child);

    testAdapter.getItemDecoration().onDraw(canvas, recyclerView, state);
    verify(testAdapter, times(10)).drawDecoration(eq(canvas), eq(recyclerView), eq(state),
        eq(child), anyInt());
  }

  @Test public void onDrawOver_test() {
    // OnDrawOver feature is not completed.
    // If we write any implementation, this test case will fail.
    // This will nudge us to write a new test case for the implementation.

    decorationDelegate.onDrawOver(canvas, recyclerView, state);
    verifyZeroInteractions(testAdapter);
  }

  @Before public void setUpTest() {
    MockitoAnnotations.initMocks(this);
    decorationDelegate = new DecorationDelegate(testAdapter);
    when(testAdapter.getItemDecoration()).thenReturn(decorationDelegate);
  }
}
