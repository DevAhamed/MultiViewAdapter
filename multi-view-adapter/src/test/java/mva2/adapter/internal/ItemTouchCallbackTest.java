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

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.testconfig.TestItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class ItemTouchCallbackTest {

  @Mock MultiViewAdapter adapter;
  @Mock View view;
  @Mock ItemViewHolder<TestItem> testItemItemViewHolderOne;
  @Mock ItemViewHolder<TestItem> testItemItemViewHolderTwo;
  @Mock ItemViewHolder<String> incorrectItemViewHolder;
  @Mock RecyclerView recyclerView;

  private ItemTouchCallback touchCallback;

  @Test public void allTest() {
    int flags = ItemTouchHelper.Callback.makeMovementFlags(UP | DOWN, LEFT | RIGHT);

    assertEquals(-1, touchCallback.getMovementFlags(recyclerView, new InvalidViewHolder(view)));
    assertEquals(0, touchCallback.getMovementFlags(recyclerView, testItemItemViewHolderOne));
    assertEquals(flags, touchCallback.getMovementFlags(recyclerView, testItemItemViewHolderTwo));

    assertTrue(touchCallback.isItemViewSwipeEnabled());
    assertFalse(touchCallback.isLongPressDragEnabled());

    assertTrue(
        touchCallback.onMove(recyclerView, testItemItemViewHolderOne, testItemItemViewHolderTwo));
    verify(adapter).onMove(10, 15);
    assertFalse(
        touchCallback.onMove(recyclerView, testItemItemViewHolderOne, incorrectItemViewHolder));

    touchCallback.onSwiped(testItemItemViewHolderOne, LEFT);
    verify(adapter).onItemDismiss(10);
    touchCallback.onSwiped(testItemItemViewHolderTwo, RIGHT);
    verify(adapter).onItemDismiss(15);
  }

  @Before public void setUp() {
    when(testItemItemViewHolderOne.getItemViewType()).thenReturn(1);
    when(testItemItemViewHolderTwo.getItemViewType()).thenReturn(1);
    when(testItemItemViewHolderOne.getAdapterPosition()).thenReturn(10);
    when(testItemItemViewHolderTwo.getAdapterPosition()).thenReturn(15);
    when(incorrectItemViewHolder.getItemViewType()).thenReturn(2);

    when(testItemItemViewHolderTwo.getDragDirections()).thenReturn(UP | ItemTouchHelper.DOWN);

    when(testItemItemViewHolderTwo.getSwipeDirections()).thenReturn(
        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

    touchCallback = new ItemTouchCallback(adapter);
  }

  private static class InvalidViewHolder extends RecyclerView.ViewHolder {

    InvalidViewHolder(View itemView) {
      super(itemView);
    }
  }
}
