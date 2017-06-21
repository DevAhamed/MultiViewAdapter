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

import com.ahamed.multiviewadapter.listener.SwipeToDismissListener;
import com.ahamed.multiviewadapter.util.PayloadProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class DataListManagerTest {

  @Mock RecyclerAdapter recyclerAdapter;
  @Mock PayloadProvider<String> payloadProvider;
  @Mock SwipeToDismissListener<String> swipeToDismissListener;
  private DataListManager<String> dataListManager;

  @Before public void setUp() {
    dataListManager = new DataListManager<>(recyclerAdapter, payloadProvider);
    List<String> dummyList = new ArrayList<>();
    dummyList.add("One");
    dummyList.add("Two");
    dummyList.add("Three");
    dummyList.add("Four");
    dummyList.add("Five");
    dataListManager.addAll(dummyList);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataListManager, 0, 5);
  }

  @Test public void addTest() {
    dataListManager.add("One+One");
    assertTrue(dataListManager.size() == 6);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataListManager, 5, 1);
  }

  @Test public void addAtIndexTest() {
    dataListManager.add(2, "One+One");
    assertTrue(dataListManager.size() == 6);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataListManager, 2, 1);
  }

  @Test public void addItemsTest() {
    List<String> dummyList = new ArrayList<>();
    dummyList.add("One+One");
    dummyList.add("One+Two");
    dummyList.add("One+Three");
    dummyList.add("One+Four");

    dataListManager.addAll(dummyList);
    assertTrue(dataListManager.size() == 9);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataListManager, 5, 4);
  }

  @Test public void addItemsAtIndexTest() {
    List<String> dummyList = new ArrayList<>();
    dummyList.add("One+One");
    dummyList.add("One+Two");
    dummyList.add("One+Three");
    dummyList.add("One+Four");

    dataListManager.addAll(2, dummyList);
    assertTrue(dataListManager.size() == 9);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataListManager, 2, 4);
  }

  @Test public void setItemTest() {
    dataListManager.set(0, "One+New");
    assertTrue(dataListManager.size() == 5);
    assertEquals(dataListManager.get(0), "One+New");
    verify(payloadProvider).getChangePayload("One", "One+New");
    verify(recyclerAdapter).notifyBinderItemRangeChanged(dataListManager, 0, 1, null);
  }

  @Test public void setItemsTest() {
    List<String> dummyList = new ArrayList<>();
    dummyList.add("One");
    dummyList.add("One+Two");
    dummyList.add("One+Three");
    dummyList.add("Four");

    dataListManager.set(dummyList);
    assertTrue(dataListManager.size() == 4);
  }

  @Test public void removeItemTest() {
    dataListManager.remove("Three");
    assertTrue(dataListManager.size() == 4);
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataListManager, 2, 1);

    dataListManager.remove(0);
    assertTrue(dataListManager.size() == 3);
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataListManager, 0, 1);
  }

  @Test public void clearItemTest() {
    dataListManager.clear();
    assertTrue(dataListManager.size() == 0);
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataListManager, 0, 5);
  }

  @Test public void swipeToDismissListenerTest() {
    dataListManager.onSwiped(2);
    assertTrue(dataListManager.size() == 4);
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataListManager, 2, 1);

    dataListManager.setSwipeToDismissListener(swipeToDismissListener);
    dataListManager.onSwiped(3);

    assertTrue(dataListManager.size() == 3);
    verify(swipeToDismissListener).onItemDismissed(3, "Five");
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataListManager, 3, 1);
  }
}
