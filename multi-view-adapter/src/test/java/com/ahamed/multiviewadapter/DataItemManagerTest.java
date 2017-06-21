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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class) public class DataItemManagerTest {

  @Mock RecyclerAdapter recyclerAdapter;
  private DataItemManager<String> dataItemManager;

  @Test public void emptyConstructorTest() {
    dataItemManager = new DataItemManager<>(recyclerAdapter);
    verifyZeroInteractions(recyclerAdapter);
  }

  @Test public void setItemTest() {
    dataItemManager = new DataItemManager<>(recyclerAdapter, "Header");
    verifyZeroInteractions(recyclerAdapter);
    dataItemManager.setItem("New Header");
    verify(recyclerAdapter).notifyBinderItemRangeChanged(dataItemManager, 0, 1, null);

    dataItemManager = new DataItemManager<>(recyclerAdapter);
    dataItemManager.setItem("Header");
    verify(recyclerAdapter).notifyBinderItemRangeInserted(dataItemManager, 0, 1);
  }

  @Test public void removeItemTest() {
    dataItemManager = new DataItemManager<>(recyclerAdapter, "Header");
    dataItemManager.removeItem();
    verify(recyclerAdapter).notifyBinderItemRangeRemoved(dataItemManager, 0, 1);

    dataItemManager = new DataItemManager<>(recyclerAdapter);
    dataItemManager.removeItem();
    verifyZeroInteractions(recyclerAdapter);
  }
}
