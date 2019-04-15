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

import java.util.List;
import mva2.adapter.testconfig.TestItem;
import mva2.adapter.util.Mode;
import mva2.adapter.util.OnItemClickListener;
import mva2.adapter.util.OnSelectionChangedListener;
import mva2.adapter.util.PayloadProvider;
import mva2.adapter.util.SwipeToDismissListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class ListSectionTest extends BaseTest {

  @Mock OnSelectionChangedListener<TestItem> selectionChangedListener;
  @Mock OnItemClickListener<TestItem> onItemClickListener;
  @Mock SwipeToDismissListener<TestItem> swipeToDismissListener;

  @Test public void clear_test() {
    assertEquals(listSection1.size(), 18);

    listSection1.clear();
    verify(adapterDataObserver).notifyItemRangeRemoved(1, 18);
    assertEquals(listSection1.size(), 0);

    // Section is already cleared
    clearInvocations(adapterDataObserver);
    listSection1.clear();
    verify(adapterDataObserver, never()).notifyItemRangeRemoved(anyInt(), anyInt());
    assertEquals(listSection1.size(), 0);

    assertEquals(listSection3.size(), 9);
    listSection3.hideSection();
    clearInvocations(adapterDataObserver);

    listSection3.clear();
    verify(adapterDataObserver, never()).notifyItemRangeRemoved(anyInt(), anyInt());
    assertEquals(listSection3.size(), 0);
  }

  @Test public void clearSelectedItems_test() {
    listSection1.setSelectionMode(Mode.MULTIPLE);
    assertEquals(listSection1.getSelectedItems().size(), 0);

    adapter.onItemSelectionToggled(1);
    adapter.onItemSelectionToggled(2);
    adapter.onItemSelectionToggled(3);
    adapter.onItemSelectionToggled(4);

    assertEquals(listSection1.getSelectedItems().size(), 4);
    clearInvocations(adapterDataObserver);

    listSection1.clearSelections();
    verify(adapterDataObserver, times(4)).notifyItemRangeChanged(anyInt(), anyInt(), any());
    assertEquals(listSection1.getSelectedItems().size(), 0);
  }

  @Test public void remove_test() {
    assertEquals(listSection1.size(), 18);

    listSection1.remove(10);
    verify(adapterDataObserver).notifyItemRangeRemoved(11, 1);
    assertEquals(listSection1.size(), 17);

    assertEquals(listSection3.size(), 9);
    listSection3.hideSection();
    clearInvocations(adapterDataObserver);

    listSection3.remove(5);
    verify(adapterDataObserver, never()).notifyItemRangeRemoved(anyInt(), anyInt());
    assertEquals(listSection3.size(), 8);
  }

  @Test public void getData_test() {
    assertEquals(listSection1.size(), 18);
    listSection1.getData().clear();
    assertEquals(listSection1.size(), 18);
  }

  @Test public void set_test() {
    TestItem testItem = listSection1.get(0);
    TestItem newTestItem = new TestItem(testItem.getId(), "Updated text!");

    listSection1.set(0, newTestItem);
    assertEquals(listSection1.get(0).getText(), "Updated text!");
    verify(adapterDataObserver).notifyItemRangeChanged(1, 1, null);
  }

  @Test public void setData_default_payloadProvider() {
    List<TestItem> testItems = listSection1.getData();
    TestItem testItem = testItems.get(0);
    TestItem newTestItem = new TestItem(testItem.getId(), "Updated text!");
    testItems.set(0, newTestItem);

    listSection1.set(testItems);

    assertEquals(listSection1.get(0).getText(), "Updated text!");

    // Since using default PayloadProvider it results in removing the item and adding new item
    verify(adapterDataObserver).notifyItemRangeInserted(1, 1);
    verify(adapterDataObserver).notifyItemRangeRemoved(1, 1);
  }

  @Test public void setData_custom_payloadProvider() {
    listSection1.setPayloadProvider(new PayloadProvider<TestItem>() {
      @Override public boolean areContentsTheSame(TestItem oldItem, TestItem newItem) {
        return oldItem.equals(newItem);
      }

      @Override public boolean areItemsTheSame(TestItem oldItem, TestItem newItem) {
        return oldItem.getId() == newItem.getId();
      }

      @Override public Object getChangePayload(TestItem oldItem, TestItem newItem) {
        return null;
      }
    });
    List<TestItem> testItems = listSection1.getData();

    TestItem testItem = testItems.get(0);
    TestItem newTestItem = new TestItem(testItem.getId(), "Updated text!");
    testItems.set(0, newTestItem);

    listSection1.set(testItems);

    assertEquals(listSection1.get(0).getText(), "Updated text!");
    verify(adapterDataObserver).notifyItemRangeChanged(1, 1, null);
  }

  @Test public void setOnItemClickListener_test() {
    listSection1.setOnItemClickListener(onItemClickListener);

    adapter.onItemClicked(1);
    adapter.onItemClicked(2);
    adapter.onItemClicked(3);
    adapter.onItemClicked(4);

    verify(onItemClickListener, times(4)).onItemClicked(anyInt(), any(TestItem.class));
  }

  @Test public void setOnSelectionChangedListener_test() {
    listSection1.setOnSelectionChangedListener(selectionChangedListener);

    listSection1.setSelectionMode(Mode.MULTIPLE);
    assertEquals(listSection1.getSelectedItems().size(), 0);

    adapter.onItemSelectionToggled(1);
    adapter.onItemSelectionToggled(2);
    adapter.onItemSelectionToggled(3);
    adapter.onItemSelectionToggled(4);

    adapter.onItemSelectionToggled(3);
    adapter.onItemSelectionToggled(4);

    verify(selectionChangedListener, times(6)).onSelectionChanged(any(TestItem.class), anyBoolean(),
        ArgumentMatchers.<TestItem>anyList());
  }

  @Test public void setSwipeToDismissListener_test() {
    listSection1.setSwipeToDismissListener(swipeToDismissListener);

    adapter.onItemDismiss(1);
    adapter.onItemDismiss(2);
    adapter.onItemDismiss(3);
    adapter.onItemDismiss(4);

    verify(swipeToDismissListener, times(4)).onItemDismissed(anyInt(), any(TestItem.class));
    assertEquals(listSection1.size(), 14);
    assertEquals(adapter.getItemCount(), 53);
  }
}
