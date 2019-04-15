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

import mva2.adapter.testconfig.CommentBinder;
import mva2.adapter.testconfig.HeaderBinder;
import mva2.adapter.testconfig.TestItem;
import mva2.adapter.testconfig.TestItemBinder;
import mva2.adapter.util.Mode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MultiViewAdapterTest extends BaseTest {

  @Test public void getCountTest() {
    assertEquals(adapter.getItemCount(), 57);

    // Add an item to a section and check
    headerSection2.getListSection().add(new TestItem(100, "Test Item 101"));
    assertEquals(adapter.getItemCount(), 58);

    // Remove an item to a section and check
    headerSection1.getListSection().remove(5);
    assertEquals(adapter.getItemCount(), 57);

    // Hide a section and check the count
    listSection1.hideSection();
    assertEquals(adapter.getItemCount(), 39);

    // Add an item to the hidden section and check
    listSection1.add(new TestItem(100, "Test Item 101"));
    assertEquals(adapter.getItemCount(), 39);

    // Show section and check the count
    listSection1.showSection();
    assertEquals(adapter.getItemCount(), 58);

    // Collapse TreeSection and check the count
    treeSection1.onSectionExpansionToggled(0, Mode.SINGLE);
    assertEquals(adapter.getItemCount(), 51);

    // Expand TreeSection and check the count
    treeSection1.onSectionExpansionToggled(0, Mode.SINGLE);
    assertEquals(adapter.getItemCount(), 58);

    // Remove all the sections. Count should be 0
    adapter.removeAllSections();
    assertEquals(adapter.getItemCount(), 0);
  }

  @Test public void getItemViewTypeTest() {
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    // Check the view type for random items
    // Items binded by headerBinder should return 0
    // Items binded by testItemBinder should return 1
    // Items binded by commentBinder should return 2

    assertEquals(adapter.getItemViewType(0), 0);
    assertEquals(adapter.getItemViewType(19), 0);
    assertEquals(adapter.getItemViewType(29), 0);
    assertEquals(adapter.getItemViewType(39), 0);

    assertEquals(adapter.getItemViewType(10), 1);
    assertEquals(adapter.getItemViewType(20), 1);
    assertEquals(adapter.getItemViewType(30), 1);
    assertEquals(adapter.getItemViewType(40), 1);

    assertEquals(adapter.getItemViewType(49), 2);
    assertEquals(adapter.getItemViewType(50), 2);
    assertEquals(adapter.getItemViewType(52), 2);
    assertEquals(adapter.getItemViewType(54), 2);
    assertEquals(adapter.getItemViewType(56), 2);

    // Dismiss an item
    // ViewTypeCache should be cleared and check if the binders are returning correct data
    adapter.onItemDismiss(0);

    assertEquals(adapter.getItemViewType(18), 0);
    assertEquals(adapter.getItemViewType(28), 0);
    assertEquals(adapter.getItemViewType(38), 0);

    assertEquals(adapter.getItemViewType(10), 1);
    assertEquals(adapter.getItemViewType(20), 1);
    assertEquals(adapter.getItemViewType(30), 1);
    assertEquals(adapter.getItemViewType(40), 1);

    assertEquals(adapter.getItemViewType(50), 2);
    assertEquals(adapter.getItemViewType(51), 2);
    assertEquals(adapter.getItemViewType(53), 2);
    assertEquals(adapter.getItemViewType(55), 2);
  }

  @Test(expected = IllegalStateException.class) public void getItemViewTypeTest_binderNotPresent() {
    adapter.registerItemBinders(headerBinder, testItemBinder);
    adapter.getItemViewType(49);
  }

  @Test(expected = IllegalStateException.class)
  public void getItemViewTypeTest_unRegisterBinders() {
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    assertEquals(adapter.getItemViewType(49), 2);

    adapter.unRegisterAllItemBinders();
    adapter.registerItemBinders(headerBinder);
    adapter.registerItemBinders(testItemBinder);

    adapter.getItemViewType(49);
  }

  @Test public void isAdapterInActionModeTest() {
    assertFalse(adapter.isAdapterInActionMode());
    adapter.startActionMode();
    assertTrue(adapter.isAdapterInActionMode());
    adapter.stopActionMode();
    assertFalse(adapter.isAdapterInActionMode());
  }

  @Test public void onBindViewHolderTest() {

    // Remove all binders and add again
    adapter.unRegisterAllItemBinders();
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    // Set up viewholder's view type
    when(headerViewHolder.getItemViewType()).thenReturn(0);
    when(testItemViewHolder.getItemViewType()).thenReturn(1);
    when(commentViewHolder.getItemViewType()).thenReturn(2);

    // Check bind method
    adapter.onBindViewHolder(headerViewHolder, 0);
    verify(headerBinder).bindViewHolder(headerViewHolder, null);

    adapter.onBindViewHolder(testItemViewHolder, 11);
    verify(testItemBinder).bindViewHolder(testItemViewHolder, null);

    adapter.onBindViewHolder(commentViewHolder, 51);
    verify(commentBinder).bindViewHolder(commentViewHolder, null);
  }

  @Test public void onCreateViewHolderTest() {

    // Remove all binders and add again
    adapter.unRegisterAllItemBinders();
    adapter.registerItemBinders(headerBinder, testItemBinder, commentBinder);

    assertTrue(adapter.onCreateViewHolder(viewGroup, 0) instanceof HeaderBinder.ViewHolder);
    assertTrue(adapter.onCreateViewHolder(viewGroup, 1) instanceof TestItemBinder.ViewHolder);
    assertTrue(adapter.onCreateViewHolder(viewGroup, 2) instanceof CommentBinder.ViewHolder);
  }

  @Test public void collapseAll_test() {
    adapter.collapseAllSections();
    assertEquals(adapter.getItemCount(), 32);
  }

  @Test public void clearSelections_test() {
    // Select multiple items from different sections
    // Clear all selection
    // Check the selected items count from different section

    adapter.setSelectionMode(Mode.MULTIPLE);
    itemSection1.setSelectionMode(Mode.SINGLE);
    headerSection1.setSelectionMode(Mode.SINGLE);

    adapter.onItemSelectionToggled(0);

    adapter.onItemSelectionToggled(1);
    adapter.onItemSelectionToggled(2);
    adapter.onItemSelectionToggled(3);
    adapter.onItemSelectionToggled(4);

    adapter.onItemSelectionToggled(20);
    adapter.onItemSelectionToggled(21);

    adapter.onItemSelectionToggled(34);
    adapter.onItemSelectionToggled(35);
    adapter.onItemSelectionToggled(44);
    adapter.onItemSelectionToggled(45);

    adapter.clearAllSelections();

    assertEquals(listSection1.getSelectedItems().size(), 0);
    assertEquals(headerSection1.getListSection().getSelectedItems().size(), 0);
    assertEquals(listSection3.getSelectedItems().size(), 0);
    assertEquals(headerSection2.getListSection().getSelectedItems().size(), 0);
  }

  @Test public void removeAllSections_test() {
    assertEquals(adapter.getItemCount(), 57);

    adapter.removeAllSections();
    assertEquals(adapter.getItemCount(), 0);

    adapter.addSection(new ListSection<>());
    assertEquals(adapter.getItemCount(), 0);
  }
}