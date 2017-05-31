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

package com.ahamed.sample.contextual.action.mode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.SelectableAdapter;
import com.ahamed.multiviewadapter.listener.MultiSelectionChangedListener;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.ahamed.multiviewadapter.util.SimpleDividerDecoration;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.binder.SelectableBinder;
import com.ahamed.sample.common.model.SelectableItem;
import java.util.ArrayList;
import java.util.List;

public class ContextualActionModeActivity extends BaseActivity {

  private ActionMode actionMode;
  private SelectableAdapter adapter;
  private DataListManager<SelectableItem> selectableItemDataListManager;

  public static void start(Context context) {
    Intent starter = new Intent(context, ContextualActionModeActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    ItemDecorator itemDecorator =
        new SimpleDividerDecoration(this, SimpleDividerDecoration.VERTICAL);

    adapter = new SelectableAdapter();
    selectableItemDataListManager = new DataListManager<>(adapter);
    selectableItemDataListManager.setMultiSelectionChangedListener(
        new MultiSelectionChangedListener<SelectableItem>() {
          @Override
          public void onMultiSelectionChangedListener(List<SelectableItem> selectedItems) {
            if (selectedItems.size() == 0) {
              toggleActionMode();
            } else if (actionMode != null) {
              actionMode.setTitle(selectedItems.size() + " selected items");
            }
          }
        });

    adapter.addDataManager(selectableItemDataListManager);
    adapter.registerBinder(new SelectableBinder(itemDecorator,
        new BaseViewHolder.OnItemLongClickListener<SelectableItem>() {
          @Override public boolean onItemLongClick(View view, SelectableItem item) {
            toggleActionMode();
            return true;
          }
        }));

    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_MULTIPLE);

    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());
    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<SelectableItem> dataList = new ArrayList<>();
    for (int i = 1; i <= 35; i++) {
      dataList.add(new SelectableItem(i, "Item " + i));
    }
    selectableItemDataListManager.set(dataList);
  }

  private void toggleActionMode() {
    if (null == actionMode) {
      adapter.startActionMode();
      actionMode = startSupportActionMode(new ActionMode.Callback() {
        @Override public boolean onCreateActionMode(ActionMode mode, Menu menu) {
          mode.setTitle("1 selected item");
          return true;
        }

        @Override public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
          return true;
        }

        @Override public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
          return false;
        }

        @Override public void onDestroyActionMode(ActionMode mode) {
          adapter.stopActionMode();
          selectableItemDataListManager.clearSelectedItems();
          actionMode = null;
        }
      });
    } else {
      actionMode.finish();
      actionMode = null;
      adapter.stopActionMode();
      selectableItemDataListManager.clearSelectedItems();
    }
  }
}
