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

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import com.ahamed.multiviewadapter.testconfig.DummyOne;
import com.ahamed.multiviewadapter.testconfig.DummyTwo;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class) @SmallTest public class CoreRecyclerAdapterITest {

  private SelectableAdapter adapter = new SelectableAdapter();

  private DataListManager<DummyOne> dummyOneDataListManager;
  private DataGroupManager<DummyOne, DummyTwo> dummyTwoDataListManager;
  private DataListManager<DummyOne> dummyThreeDataListManager;

  private DummyOneBinder dummyOneBinder = new DummyOneBinder();
  private DummyTwoBinder dummyTwoBinder = new DummyTwoBinder();

  @Before public void setUp() {
    adapter.registerBinders(dummyOneBinder, dummyTwoBinder);

    dummyOneDataListManager = new DataListManager<>(adapter);
    adapter.addDataManager(dummyOneDataListManager);

    for (int i = 0; i < 10; i++) {
      dummyOneDataListManager.add(new DummyOne(i, "DummyOne " + i));
    }

    dummyTwoDataListManager = new DataGroupManager<>(adapter, new DummyOne(1, "Header"));
    adapter.addDataManager(dummyTwoDataListManager);

    for (int i = 0; i < 10; i++) {
      dummyTwoDataListManager.add(new DummyTwo(i, "DummyTwo " + i));
    }

    dummyThreeDataListManager = new DataListManager<>(adapter);
    adapter.addDataManager(dummyThreeDataListManager);

    for (int i = 0; i < 10; i++) {
      dummyThreeDataListManager.add(new DummyOne(i, "DummyOne 1+" + i));
    }
  }

  @Test public void getCountTest() {
    assertEquals(adapter.getItemCount(), 21);

    dummyTwoDataListManager.onGroupExpansionToggled();
    assertEquals(adapter.getItemCount(), 31);

    dummyTwoDataListManager.onGroupExpansionToggled();
    assertEquals(adapter.getItemCount(), 21);

    dummyOneDataListManager.remove(0);
    dummyTwoDataListManager.remove(0);

    assertEquals(dummyTwoDataListManager.get(0).getData(), "DummyTwo 1");
    assertEquals(adapter.getItemCount(), 20);
  }

  @Test public void getItemViewTypeTest() {
    assertEquals(adapter.getItemViewType(0), 0);
    assertEquals(adapter.getItemViewType(10), 0);
    assertEquals(adapter.getItemViewType(11), 0);

    dummyTwoDataListManager.onGroupExpansionToggled();
    assertEquals(adapter.getItemViewType(0), 0);
    assertEquals(adapter.getItemViewType(10), 0);
    assertEquals(adapter.getItemViewType(11), 1);
    assertEquals(adapter.getItemViewType(21), 0);

    dummyOneDataListManager.remove(0);
    assertEquals(adapter.getItemViewType(10), 1);
    assertEquals(adapter.getItemViewType(20), 0);
  }

  @Test public void getBinderForPosition_Test() {
    assertEquals(adapter.getBinderForPosition(0), dummyOneBinder);
    assertEquals(adapter.getBinderForPosition(10), dummyOneBinder);
    assertEquals(adapter.getBinderForPosition(11), dummyOneBinder);

    dummyTwoDataListManager.onGroupExpansionToggled();

    assertEquals(adapter.getBinderForPosition(0), dummyOneBinder);
    assertEquals(adapter.getBinderForPosition(10), dummyOneBinder);
    assertEquals(adapter.getBinderForPosition(11), dummyTwoBinder);
    assertEquals(adapter.getBinderForPosition(21), dummyOneBinder);
  }

  @Test(expected = IllegalStateException.class)
  public void getBinderForPosition_InvalidPositionTest() {
    adapter.getBinderForPosition(45);
  }

  @Test public void getItemPositionInManager_Test() {
    assertEquals(adapter.getItemPositionInManager(0), 0);
    assertEquals(adapter.getItemPositionInManager(10), 0);
    assertEquals(adapter.getItemPositionInManager(11), 0);

    dummyTwoDataListManager.onGroupExpansionToggled();

    assertEquals(adapter.getItemPositionInManager(0), 0);
    assertEquals(adapter.getItemPositionInManager(10), 0);
    assertEquals(adapter.getItemPositionInManager(11), 1);
    assertEquals(adapter.getItemPositionInManager(21), 0);
  }

  @Test public void getDataManager_Test() {
    assertEquals(adapter.getDataManager(0), dummyOneDataListManager);
    assertEquals(adapter.getDataManager(10), dummyTwoDataListManager.getDataManagerForPosition(0));
    assertEquals(adapter.getDataManager(11), dummyThreeDataListManager);

    dummyTwoDataListManager.onGroupExpansionToggled();

    assertEquals(adapter.getDataManager(0), dummyOneDataListManager);
    assertEquals(adapter.getDataManager(10), dummyTwoDataListManager.getDataManagerForPosition(0));
    assertEquals(adapter.getDataManager(11), dummyTwoDataListManager);
    assertEquals(adapter.getDataManager(21), dummyThreeDataListManager);
  }

  @Test public void justGetDataManager_Test() {
    assertEquals(adapter.justGetDataManager(0), dummyOneDataListManager);
    assertEquals(adapter.justGetDataManager(10), dummyTwoDataListManager);
    assertEquals(adapter.justGetDataManager(11), dummyThreeDataListManager);

    dummyTwoDataListManager.onGroupExpansionToggled();

    assertEquals(adapter.justGetDataManager(0), dummyOneDataListManager);
    assertEquals(adapter.justGetDataManager(10), dummyTwoDataListManager);
    assertEquals(adapter.justGetDataManager(11), dummyTwoDataListManager);
    assertEquals(adapter.justGetDataManager(21), dummyThreeDataListManager);
  }

  @Test public void getPositionInAdapter_Test() {
    assertEquals(adapter.getPositionInAdapter(dummyOneDataListManager, 0), 0);
    assertEquals(adapter.getPositionInAdapter(dummyTwoDataListManager, 0), 10);
    assertEquals(adapter.getPositionInAdapter(dummyThreeDataListManager, 0), 11);

    dummyTwoDataListManager.onGroupExpansionToggled();

    assertEquals(adapter.getPositionInAdapter(dummyOneDataListManager, 0), 0);
    assertEquals(adapter.getPositionInAdapter(dummyTwoDataListManager, 0), 10);
    assertEquals(adapter.getPositionInAdapter(dummyTwoDataListManager, 1), 11);
    assertEquals(adapter.getPositionInAdapter(dummyThreeDataListManager, 0), 21);
  }

  @Test public void isItemSelected_SingleTest() {
    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_SINGLE);

    adapter.onItemSelectionToggled(12);
    assertEquals(adapter.getDataManager(12).get(adapter.getItemPositionInManager(12)),
        dummyThreeDataListManager.getSelectedItem());

    dummyTwoDataListManager.onGroupExpansionToggled();
    assertEquals(adapter.getDataManager(22).get(adapter.getItemPositionInManager(22)),
        dummyThreeDataListManager.getSelectedItem());

    dummyThreeDataListManager.setSelectedItem(dummyThreeDataListManager.get(4));
    assertEquals(dummyThreeDataListManager.get(4), dummyThreeDataListManager.getSelectedItem());

    dummyThreeDataListManager.clearSelectedItems();
    assertEquals(null, dummyThreeDataListManager.getSelectedItem());

    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_SINGLE_OR_NONE);

    adapter.onItemSelectionToggled(12);
    assertEquals(adapter.getDataManager(12).getItem(adapter.getItemPositionInManager(12)),
        dummyTwoDataListManager.getSelectedItem());

    adapter.onItemSelectionToggled(25);
    dummyTwoDataListManager.onGroupExpansionToggled();
    assertEquals(adapter.getDataManager(15).getItem(adapter.getItemPositionInManager(15)),
        dummyThreeDataListManager.getSelectedItem());
  }

  @Test public void isItemSelected_MultipleTest() {
    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_MULTIPLE);

    adapter.onItemSelectionToggled(8);
    adapter.onItemSelectionToggled(12);

    assertTrue(adapter.getDataManager(8)
        .getSelectedItems()
        .contains(adapter.getDataManager(8).get(adapter.getItemPositionInManager(8))));

    assertTrue(adapter.getDataManager(12)
        .getSelectedItems()
        .contains(adapter.getDataManager(12).get(adapter.getItemPositionInManager(12))));

    dummyTwoDataListManager.onGroupExpansionToggled();

    assertTrue(adapter.getDataManager(8)
        .getSelectedItems()
        .contains(adapter.getDataManager(8).get(adapter.getItemPositionInManager(8))));

    adapter.onItemSelectionToggled(15);
    assertTrue(dummyTwoDataListManager.getSelectedItems().contains(dummyTwoDataListManager.get(4)));

    assertTrue(adapter.getDataManager(22)
        .getSelectedItems()
        .contains(adapter.getDataManager(22).get(adapter.getItemPositionInManager(22))));

    List<DummyOne> selectedItems = new ArrayList<>();
    selectedItems.add(dummyOneDataListManager.get(1));
    selectedItems.add(dummyOneDataListManager.get(2));
    selectedItems.add(dummyOneDataListManager.get(4));
    dummyOneDataListManager.setSelectedItems(selectedItems);

    assertTrue(dummyOneDataListManager.getSelectedItems().contains(dummyOneDataListManager.get(1)));
    assertTrue(dummyOneDataListManager.getSelectedItems().contains(dummyOneDataListManager.get(2)));
    assertTrue(dummyOneDataListManager.getSelectedItems().contains(dummyOneDataListManager.get(4)));

    assertFalse(adapter.getDataManager(8)
        .getSelectedItems()
        .contains(adapter.getDataManager(8).get(adapter.getItemPositionInManager(8))));

    assertTrue(dummyTwoDataListManager.getSelectedItems().contains(dummyTwoDataListManager.get(4)));

    assertTrue(adapter.getDataManager(22)
        .getSelectedItems()
        .contains(adapter.getDataManager(22).get(adapter.getItemPositionInManager(22))));
  }

  @Test public void onItemExpansionToggled_Test() {
    adapter.actionListener.onItemExpansionToggled(1);
    adapter.actionListener.onItemExpansionToggled(2);
    adapter.actionListener.onItemExpansionToggled(3);

    assertFalse(adapter.actionListener.isItemExpanded(1));
    assertFalse(adapter.actionListener.isItemExpanded(2));
    assertFalse(adapter.actionListener.isItemExpanded(3));

    adapter.setExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_SINGLE);

    adapter.actionListener.onItemExpansionToggled(1);
    adapter.actionListener.onItemExpansionToggled(2);
    adapter.actionListener.onItemExpansionToggled(3);

    assertFalse(adapter.actionListener.isItemExpanded(1));
    assertFalse(adapter.actionListener.isItemExpanded(2));
    assertTrue(adapter.actionListener.isItemExpanded(3));

    adapter.setExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);

    adapter.actionListener.onItemExpansionToggled(1);
    adapter.actionListener.onItemExpansionToggled(2);
    adapter.actionListener.onItemExpansionToggled(3);

    assertTrue(adapter.actionListener.isItemExpanded(1));
    assertTrue(adapter.actionListener.isItemExpanded(2));
    assertTrue(adapter.actionListener.isItemExpanded(3));
  }

  @Test public void collapseAll_Test() {
    adapter.setExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);

    adapter.actionListener.onItemExpansionToggled(1);
    adapter.actionListener.onItemExpansionToggled(2);
    adapter.actionListener.onItemExpansionToggled(3);

    assertTrue(adapter.actionListener.isItemExpanded(1));
    assertTrue(adapter.actionListener.isItemExpanded(2));
    assertTrue(adapter.actionListener.isItemExpanded(3));

    adapter.collapseAll();

    assertFalse(adapter.actionListener.isItemExpanded(1));
    assertFalse(adapter.actionListener.isItemExpanded(2));
    assertFalse(adapter.actionListener.isItemExpanded(3));
  }
}
