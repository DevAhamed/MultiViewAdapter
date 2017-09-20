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

import android.support.test.runner.AndroidJUnit4;
import com.ahamed.multiviewadapter.listener.ItemSelectionChangedListener;
import com.ahamed.multiviewadapter.listener.MultiSelectionChangedListener;
import com.ahamed.multiviewadapter.testconfig.DummyOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class) public class RecyclerAdapterITest {

  @Mock ItemSelectionChangedListener<DummyOne> itemSelectionChangedListener;
  @Mock MultiSelectionChangedListener<DummyOne> multiSelectionChangedListener;
  private RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
  private SelectableAdapter selectableAdapter = new SelectableAdapter();

  @Test public void setExpandableModeTest() {
    recyclerAdapter.setExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
    assertEquals(recyclerAdapter.expandableMode, RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
  }

  @Test public void setGroupExpandableModeTest() {
    recyclerAdapter.setGroupExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
    assertEquals(recyclerAdapter.groupExpandableMode, RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
  }

  @Test public void onItemSelectionToggledTest() {
    MockitoAnnotations.initMocks(this);

    selectableAdapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_MULTIPLE);

    DataListManager<DummyOne> dataListManager = new DataListManager<>(selectableAdapter);
    selectableAdapter.addDataManager(dataListManager);

    for (int i = 0; i < 10; i++) {
      dataListManager.add(new DummyOne(i, "DummyOne " + i));
    }
    dataListManager.setMultiSelectionChangedListener(multiSelectionChangedListener);

    selectableAdapter.onItemSelectionToggled(1);

    verify(multiSelectionChangedListener).onMultiSelectionChangedListener(
        dataListManager.getSelectedItems());

    selectableAdapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_SINGLE);
    dataListManager.setItemSelectionChangedListener(itemSelectionChangedListener);

    selectableAdapter.onItemSelectionToggled(2);

    verify(itemSelectionChangedListener).onItemSelectionChangedListener(dataListManager.getItem(2),
        true);
  }
}
