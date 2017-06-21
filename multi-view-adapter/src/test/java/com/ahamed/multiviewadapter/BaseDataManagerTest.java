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

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class BaseDataManagerTest {

  @Mock SelectableAdapter adapter;
  @Mock RecyclerAdapter recyclerAdapter;
  private DataListManager<String> baseDataManager;

  @Before public void setUp() {
    this.baseDataManager = new DataListManager<>(adapter);

    baseDataManager.add("One");
    baseDataManager.add("Two");
    baseDataManager.add("Three");
    baseDataManager.add("Four");
    baseDataManager.add("Five");
  }

  @Test public void setSelectedItemTest() {
    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_SINGLE);

    baseDataManager.setSelectedItem("One");
    baseDataManager.setSelectedItem("Two");

    assertEquals(baseDataManager.getSelectedItem(), "Two");
    assertNotSame(baseDataManager.getSelectedItem(), "One");
  }

  @Test public void setSelectedItemsTest() {
    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_MULTIPLE);

    List<String> selectedItems = new ArrayList<>();
    selectedItems.add("One");
    selectedItems.add("Two");
    selectedItems.add("Four");

    baseDataManager.setSelectedItems(selectedItems);

    selectedItems.remove("Two");
    baseDataManager.setSelectedItems(selectedItems);

    selectedItems.add("Three");

    assertTrue(baseDataManager.getSelectedItems().contains("One"));
    assertTrue(baseDataManager.getSelectedItems().contains("Four"));
    assertFalse(baseDataManager.getSelectedItems().contains("Two"));
    assertFalse(baseDataManager.getSelectedItems().contains("Three"));
  }

  @Test public void clearSelectedItemsTest() {
    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_MULTIPLE);

    List<String> selectedItems = new ArrayList<>();
    selectedItems.add("One");
    selectedItems.add("Two");
    selectedItems.add("Four");

    baseDataManager.setSelectedItems(selectedItems);
    baseDataManager.clearSelectedItems();

    assertEquals(baseDataManager.getSelectedItems().size(), 0);
  }

  @Test(expected = IllegalStateException.class) public void setSelectedItemInWrongAdapter() {
    this.baseDataManager = new DataListManager<>(recyclerAdapter);
    baseDataManager.setSelectedItem("One");
  }

  @Test(expected = IllegalStateException.class) public void setSelectedItemsInWrongAdapter() {
    this.baseDataManager = new DataListManager<>(recyclerAdapter);
    baseDataManager.setSelectedItems(new ArrayList<String>());
  }

  @Test(expected = IllegalStateException.class) public void clearSelectedItemsInWrongAdapter() {
    this.baseDataManager = new DataListManager<>(recyclerAdapter);
    baseDataManager.clearSelectedItems();
  }

  @Test public void isItemSelectedTest() {
    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_MULTIPLE);

    List<String> selectedItems = new ArrayList<>();
    selectedItems.add("One");
    selectedItems.add("Two");
    selectedItems.add("Four");

    baseDataManager.setSelectedItems(selectedItems);

    // "Two" is selected
    assertTrue(baseDataManager.isItemSelected(1));

    // "Three" is not selected
    assertFalse(baseDataManager.isItemSelected(2));
  }

  @Test public void getSelectedIndexTest() {
    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_SINGLE);
    baseDataManager.setSelectedItem("Two");

    // "Two" is selected
    assertEquals(baseDataManager.getSelectedIndex(), 1);
  }

  @Test public void onSwappedTest() {
    baseDataManager.onSwapped(1, 3);
    assertEquals(baseDataManager.getDataList().get(3), "Two");

    verify(adapter).notifyBinderItemMoved(baseDataManager, 1, 3);
  }
}
