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

package com.ahamed.sample.selectable.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.SelectableAdapter;
import com.ahamed.multiviewadapter.listener.ItemSelectionChangedListener;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.ahamed.multiviewadapter.util.SimpleDividerDecoration;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.model.SelectableItem;
import java.util.ArrayList;
import java.util.List;

public class SelectionModeActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, SelectionModeActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    ItemDecorator itemDecorator =
        new SimpleDividerDecoration(this, SimpleDividerDecoration.VERTICAL);

    SelectableAdapter adapter = new SelectableAdapter();
    DataListManager<SelectableItem> selectableItemsManager = new DataListManager<>(adapter);
    selectableItemsManager.setItemSelectionChangedListener(
        new ItemSelectionChangedListener<SelectableItem>() {
          @Override
          public void onItemSelectionChangedListener(SelectableItem item, boolean isSelected) {
            Toast.makeText(getApplicationContext(), "Selected : " + item.getContent(),
                Toast.LENGTH_SHORT).show();
          }
        });

    adapter.addDataManager(selectableItemsManager);
    adapter.registerBinder(new SingleSelectionBinder(itemDecorator));

    adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_SINGLE);

    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());
    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<SelectableItem> dataList = new ArrayList<>();
    for (int i = 1; i <= 25; i++) {
      dataList.add(new SelectableItem(i, "Item " + i));
    }
    selectableItemsManager.set(dataList);

    selectableItemsManager.setSelectedItem(dataList.get(4));
  }
}
