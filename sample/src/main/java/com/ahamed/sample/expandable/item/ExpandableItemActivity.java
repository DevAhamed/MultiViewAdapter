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

package com.ahamed.sample.expandable.item;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.expandable.ExpandableItemBinder;
import java.util.ArrayList;
import java.util.List;

public class ExpandableItemActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, ExpandableItemActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    RecyclerAdapter adapter = new RecyclerAdapter();
    DataListManager<String> expandableItemDataListManager = new DataListManager<>(adapter);
    adapter.addDataManager(expandableItemDataListManager);
    adapter.registerBinder(new ExpandableItemBinder());

    adapter.setExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);

    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<String> dataList = new ArrayList<>();
    for (int i = 1; i <= 35; i++) {
      dataList.add("Item " + i);
    }
    expandableItemDataListManager.set(dataList);
  }
}
