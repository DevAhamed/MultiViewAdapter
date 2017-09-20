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

import android.support.v7.widget.RecyclerView;
import com.ahamed.multiviewadapter.testconfig.DummyOne;
import com.ahamed.multiviewadapter.testconfig.DummyTwo;
import com.ahamed.multiviewadapter.util.InfiniteLoadingHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class RecyclerAdapterTest {

  @Mock RecyclerView recyclerView;
  @Mock ItemBinder dummyBinder;
  @Mock InfiniteLoadingHelper infiniteLoadingHelper;
  private RecyclerAdapter recyclerAdapter = new RecyclerAdapter();

  @Before public void setUp() {
    recyclerAdapter.registerBinder(new DummyOneBinder());
    recyclerAdapter.registerBinder(new DummyTwoBinder());

    DataListManager<DummyOne> dummyOneDataListManager = new DataListManager<>(recyclerAdapter);
    recyclerAdapter.addDataManager(dummyOneDataListManager);

    DataGroupManager<DummyOne, DummyTwo> dummyTwoDataListManager =
        new DataGroupManager<>(recyclerAdapter, new DummyOne(1, "Header"));
    recyclerAdapter.addDataManager(dummyTwoDataListManager);

    recyclerView.setAdapter(recyclerAdapter);
  }

  @Test public void addDataManagerTest() {
    DataListManager dataListManager = new DataListManager(recyclerAdapter);
    recyclerAdapter.addDataManager(dataListManager);

    assertEquals(recyclerAdapter.dataManagers.get(2), dataListManager);

    DataListManager anotherListManager = new DataListManager(recyclerAdapter);
    recyclerAdapter.addDataManager(anotherListManager);

    assertEquals(recyclerAdapter.dataManagers.get(3), anotherListManager);
  }

  @Test public void setSpanCountTest() {
    when(dummyBinder.canBindData("Dummy")).thenReturn(true);
    when(dummyBinder.getSpanSize(5)).thenReturn(5);

    recyclerAdapter.registerBinder(dummyBinder);

    recyclerAdapter.setSpanCount(5);
    recyclerAdapter.addDataManager(new DataItemManager<>(recyclerAdapter, "Dummy"));
    assertEquals(recyclerAdapter.getSpanSizeLookup().getSpanSize(1), 5);
  }

  @Test public void actionModeTest() {
    // By default action mode is false
    assertEquals(recyclerAdapter.isInActionMode, false);

    recyclerAdapter.startActionMode();
    assertEquals(recyclerAdapter.isInActionMode, true);

    recyclerAdapter.stopActionMode();
    assertEquals(recyclerAdapter.isInActionMode, false);
  }

  @Test public void setInfiniteLoadingHelperTest() {
    int previousCount = recyclerAdapter.dataManagers.size();
    int previousBinderCount = recyclerAdapter.binders.size();

    recyclerAdapter.setInfiniteLoadingHelper(infiniteLoadingHelper);

    assertEquals(recyclerAdapter.dataManagers.size(), previousCount + 1);
    assertEquals(recyclerAdapter.binders.size(), previousBinderCount + 1);
  }
}
