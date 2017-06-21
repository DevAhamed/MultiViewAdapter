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

import android.view.View;
import com.ahamed.multiviewadapter.listener.ItemActionListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class BaseViewHolderTest {

  @Mock View view;
  @Mock ItemActionListener actionListener;
  @Mock BaseViewHolder.OnItemClickListener<String> itemClickListener;
  @Mock BaseViewHolder.OnItemLongClickListener<String> longClickListener;
  private BaseViewHolder<String> viewHolder;

  @Before public void setUp() {
    viewHolder = new BaseViewHolder<>(view);
  }

  @Test public void setItemTest() {
    viewHolder.setItem("Some data");
    assertEquals(viewHolder.getItem(), "Some data");
  }

  @Test public void onClickTest() {
    viewHolder.setItem("Some data");
    viewHolder.setItemClickListener(itemClickListener);
    viewHolder.onClick(view);
    verify(itemClickListener).onItemClick(view, "Some data");
  }

  @Test public void onLongClickTest() {
    viewHolder.setItem("Some data");
    viewHolder.setItemLongClickListener(longClickListener);
    viewHolder.onLongClick(view);
    verify(longClickListener).onItemLongClick(view, "Some data");
  }

  @Test public void itemActionListenerTest() {
    viewHolder.setItem("Some data");
    viewHolder.setItemActionListener(actionListener);

    viewHolder.isItemSelected();
    verify(actionListener).isItemSelected(viewHolder.getAdapterPosition());

    viewHolder.isInActionMode();
    verify(actionListener).isAdapterInActionMode();

    viewHolder.isItemExpanded();
    verify(actionListener).isItemExpanded(viewHolder.getAdapterPosition());

    viewHolder.startDrag();
    verify(actionListener).onStartDrag(viewHolder);

    viewHolder.toggleItemSelection();
    verify(actionListener).onItemSelectionToggled(viewHolder.getAdapterPosition());

    viewHolder.toggleItemExpansion();
    verify(actionListener).onItemExpansionToggled(viewHolder.getAdapterPosition());

    viewHolder.toggleGroupExpansion();
    verify(actionListener).onGroupExpansionToggled(viewHolder.getAdapterPosition());
  }
}
