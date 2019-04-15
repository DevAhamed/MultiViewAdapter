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

import android.view.View;
import mva2.adapter.testconfig.TestItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class ViewHolderTest {

  private ItemViewHolder<TestItem> viewHolder;
  private TestItem testItem = new TestItem(1, "Test Item");

  @Mock private MultiViewAdapter adapter;
  @Mock private View itemView;

  @SuppressWarnings("ResultOfMethodCallIgnored") @Test public void itemActionListenerTest() {
    viewHolder.setItem(testItem);
    viewHolder.setAdapter(adapter);

    viewHolder.isItemSelected();
    verify(adapter).isItemSelected(viewHolder.getAdapterPosition());

    viewHolder.isInActionMode();
    verify(adapter).isAdapterInActionMode();

    viewHolder.isItemExpanded();
    verify(adapter).isItemExpanded(viewHolder.getAdapterPosition());

    viewHolder.startDrag();
    verify(adapter).onStartDrag(viewHolder);

    viewHolder.toggleItemSelection();
    verify(adapter).onItemSelectionToggled(viewHolder.getAdapterPosition());

    viewHolder.toggleItemExpansion();
    verify(adapter).onItemExpansionToggled(viewHolder.getAdapterPosition());

    viewHolder.toggleSectionExpansion();
    verify(adapter).onSectionExpansionToggled(viewHolder.getAdapterPosition());
  }

  //@Test public void onClickTest() {
  //  viewHolder.setItem(testItem);
  //  viewHolder.setOnItemClickListener(itemClickListener);
  //  viewHolder.onClick(itemView);
  //  verify(itemClickListener).onItemClick(itemView, testItem);
  //}
  //
  //@Test public void onLongClickTest() {
  //  viewHolder.setItem(testItem);
  //  viewHolder.setOnItemLongClickListener(longClickListener);
  //  viewHolder.onLongClick(itemView);
  //  verify(longClickListener).onItemLongClick(itemView, testItem);
  //}

  @Test public void setItemTest() {
    viewHolder.setItem(testItem);
    assertEquals(viewHolder.getItem(), testItem);
  }

  @Before public void setUp() {
    viewHolder = new ItemViewHolder<>(itemView);
  }
}
