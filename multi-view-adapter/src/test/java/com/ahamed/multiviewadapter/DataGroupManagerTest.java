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

import com.ahamed.multiviewadapter.util.PayloadProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class) public class DataGroupManagerTest {

  @Mock RecyclerAdapter recyclerAdapter;
  @Mock PayloadProvider<String> payloadProvider;
  private DataGroupManager<String, String> dataGroupManager;
  private List<String> dataList = new ArrayList<>();

  @Before public void setUp() {
    this.dataGroupManager = new DataGroupManager<>(recyclerAdapter, "Title", payloadProvider);
    dataList.clear();
    dataList.add("One");
    dataList.add("Two");
    dataList.add("Three");
    dataList.add("Four");
    dataList.add("Five");
    dataGroupManager.addAll(dataList);
  }

  @Test public void collapsedByDefault() throws Exception {
    assertFalse(dataGroupManager.isExpanded());
    assertTrue(dataGroupManager.size() == 1);
  }

  @Test public void setExpandedTrue() throws Exception {
    dataGroupManager.setExpanded(true);
    assertTrue(dataGroupManager.isExpanded());
    assertTrue(dataGroupManager.size() == 6);
  }

  @Test public void setExpandedFalse() throws Exception {
    dataGroupManager.setExpanded(false);
    assertFalse(dataGroupManager.isExpanded());
    assertTrue(dataGroupManager.size() == 1);
  }

  @Test public void expandGroup() throws Exception {
    dataGroupManager.setExpanded(false);
    dataGroupManager.onGroupExpansionToggled();
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1,
        dataGroupManager.getDataList().size());
    assertTrue(dataGroupManager.size() == 6);
  }

  @Test public void addTest() {
    dataGroupManager.add("One+One");
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);

    dataGroupManager.onGroupExpansionToggled();
    assertTrue(dataGroupManager.size() == 7);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 6);

    dataGroupManager.add("One+Two");
    assertTrue(dataGroupManager.size() == 8);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 7, 1);
  }

  @Test public void addAtIndexTest() {
    dataGroupManager.add("One+One");
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);

    dataGroupManager.onGroupExpansionToggled();
    assertTrue(dataGroupManager.size() == 7);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 6);

    dataGroupManager.add(2, "New");
    assertTrue(dataGroupManager.size() == 8);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 3, 1);
  }

  @Test public void addItemsTest() {
    List<String> dummyList = new ArrayList<>();
    dummyList.add("One+One");
    dummyList.add("One+Two");
    dummyList.add("One+Three");
    dummyList.add("One+Four");

    dataGroupManager.addAll(dummyList);
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);

    dataGroupManager.onGroupExpansionToggled();
    assertTrue(dataGroupManager.size() == 10);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 9);

    dataGroupManager.addAll(dummyList);
    assertTrue(dataGroupManager.size() == 14);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 10, 4);
  }

  @Test public void addItemsAtIndexTest() {
    List<String> dummyList = new ArrayList<>();
    dummyList.add("One+One");
    dummyList.add("One+Two");
    dummyList.add("One+Three");
    dummyList.add("One+Four");

    dataGroupManager.addAll(2, dummyList);
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);

    dataGroupManager.onGroupExpansionToggled();
    assertTrue(dataGroupManager.size() == 10);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 9);

    dataGroupManager.addAll(2, dummyList);
    assertTrue(dataGroupManager.size() == 14);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 3, 4);
  }

  @Test public void setItemTest() {
    dataGroupManager.set(3, "Four+New");
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);
    assertTrue(dataGroupManager.get(3).equals("Four+New"));

    dataGroupManager.onGroupExpansionToggled();
    assertTrue(dataGroupManager.size() == 6);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 5);

    dataGroupManager.set(2, "Three+New");
    assertTrue(dataGroupManager.size() == 6);
    verify(recyclerAdapter).notifyBinderItemRangeChanged(dataGroupManager, 3, 1, null);
    assertTrue(dataGroupManager.get(2).equals("Three+New"));
  }

  @Test public void setItemsTest() {
    List<String> dummyList = new ArrayList<>();
    dummyList.add("One+One");
    dummyList.add("One+Two");
    dummyList.add("One+Three");
    dummyList.add("One+Four");

    dataGroupManager.set(dummyList);
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);

    dataGroupManager.onGroupExpansionToggled();
    assertTrue(dataGroupManager.size() == 5);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 4);

    List<String> newDummyList = new ArrayList<>();
    newDummyList.add("One+New");
    newDummyList.add("Two+New");
    newDummyList.add("Three+New");
    newDummyList.add("Four+New");

    dataGroupManager.set(newDummyList);
    assertTrue(dataGroupManager.size() == 5);
    // Just check whether its inserted. Payload test is done at different class
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 4);
  }

  @Test public void removeItemTest() {
    dataGroupManager.remove("Three");
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);

    dataGroupManager.onGroupExpansionToggled();
    assertTrue(dataGroupManager.size() == 5);
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataGroupManager, 1, 4);

    dataGroupManager.remove("Two");
    assertTrue(dataGroupManager.size() == 4);
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataGroupManager, 2, 1);

    dataGroupManager.remove(0);
    assertTrue(dataGroupManager.size() == 3);
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataGroupManager, 1, 1);
  }

  @Test public void clearItemTest() {
    dataGroupManager.clear();
    assertTrue(dataGroupManager.size() == 1);
    verifyNoMoreInteractions(recyclerAdapter);

    List<String> dummyList = new ArrayList<>();
    dummyList.add("One+One");
    dummyList.add("One+Two");
    dummyList.add("One+Three");
    dummyList.add("One+Four");
    dataGroupManager.addAll(dummyList);
    dataGroupManager.onGroupExpansionToggled();

    dataGroupManager.clear();
    assertTrue(dataGroupManager.size() == 1);
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataGroupManager, 1, 4);
  }
}
