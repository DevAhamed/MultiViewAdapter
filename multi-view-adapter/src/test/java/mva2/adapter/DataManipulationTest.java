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

import java.util.ArrayList;
import java.util.List;
import mva2.adapter.testconfig.TestItem;
import mva2.adapter.util.Mode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class DataManipulationTest extends BaseTest {

  @Test public void addTest() {

    clearInvocations(adapterDataObserver);
    listSection1.add(new TestItem(101, "Test Item 101"));
    verify(adapterDataObserver).notifyItemRangeInserted(19, 1);

    headerSection1.getListSection().add(new TestItem(101, "Test Item 101"));
    verify(adapterDataObserver).notifyItemRangeInserted(30, 1);

    List<TestItem> list = new ArrayList<>();
    list.add(new TestItem(101, "Test Item 101"));
    list.add(new TestItem(101, "Test Item 102"));
    headerSection2.getListSection().addAll(list);
    verify(adapterDataObserver).notifyItemRangeInserted(51, 2);

    listSection1.add(5, new TestItem(103, "Test Item 103"));
    verify(adapterDataObserver).notifyItemRangeInserted(6, 1);

    headerSection1.onSectionExpansionToggled(0, Mode.SINGLE);
    clearInvocations(adapterDataObserver);

    headerSection1.getListSection().add(new TestItem(104, "Test Item 104"));
    verify(adapterDataObserver, never()).notifyItemRangeInserted(anyInt(), eq(1));
  }

  @Test public void moveTest() {
    adapter.onMove(34, 37);
    verify(adapterDataObserver).notifyItemMoved(34, 37);

    clearInvocations(adapterDataObserver);
    adapter.onMove(24, 36);
    verify(adapterDataObserver).notifyItemMoved(24, 36);

    clearInvocations(adapterDataObserver);
    adapter.onMove(15, 35);
    verify(adapterDataObserver).notifyItemMoved(15, 35);

    clearInvocations(adapterDataObserver);
    adapter.onMove(13, 16);
    verify(adapterDataObserver).notifyItemMoved(13, 16);

    clearInvocations(adapterDataObserver);
    adapter.onMove(5, 8);
    verify(adapterDataObserver).notifyItemMoved(5, 8);

    clearInvocations(adapterDataObserver);
    adapter.onMove(2, 15);
    verify(adapterDataObserver).notifyItemMoved(2, 15);

    clearInvocations(adapterDataObserver);
    adapter.onMove(5, 25);
    verify(adapterDataObserver).notifyItemMoved(5, 25);

    // REVERSE

    clearInvocations(adapterDataObserver);
    adapter.onMove(37, 34);
    verify(adapterDataObserver).notifyItemMoved(37, 34);

    clearInvocations(adapterDataObserver);
    adapter.onMove(36, 24);
    verify(adapterDataObserver).notifyItemMoved(36, 24);

    clearInvocations(adapterDataObserver);
    adapter.onMove(35, 15);
    verify(adapterDataObserver).notifyItemMoved(35, 15);

    clearInvocations(adapterDataObserver);
    adapter.onMove(13, 16);
    verify(adapterDataObserver).notifyItemMoved(13, 16);

    clearInvocations(adapterDataObserver);
    adapter.onMove(8, 5);
    verify(adapterDataObserver).notifyItemMoved(8, 5);

    clearInvocations(adapterDataObserver);
    adapter.onMove(15, 2);
    verify(adapterDataObserver).notifyItemMoved(15, 2);

    clearInvocations(adapterDataObserver);
    adapter.onMove(25, 5);
    verify(adapterDataObserver).notifyItemMoved(25, 5);

    // On Move for ItemSection
    clearInvocations(adapterDataObserver);
    adapter.onMove(0, 19);
    verify(adapterDataObserver).notifyItemMoved(0, 19);
  }
}
