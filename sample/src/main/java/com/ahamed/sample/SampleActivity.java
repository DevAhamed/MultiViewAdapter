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

package com.ahamed.sample;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.complex.ComplexListActivity;
import com.ahamed.sample.contextual.action.mode.ContextualActionModeActivity;
import com.ahamed.sample.data.binding.DataBindingActivity;
import com.ahamed.sample.drag.and.drop.DragAndDropActivity;
import com.ahamed.sample.expandable.group.ExpandableGroupActivity;
import com.ahamed.sample.expandable.item.ExpandableItemActivity;
import com.ahamed.sample.grid.GridAdapterActivity;
import com.ahamed.sample.infinite.scroll.InfiniteScrollActivity;
import com.ahamed.sample.multilist.MultiListActivity;
import com.ahamed.sample.payload.PayloadActivity;
import com.ahamed.sample.simple.SimpleAdapterActivity;
import com.ahamed.sample.swipetodismiss.SwipeToDismissActivity;
import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends BaseActivity {

  private List<String> dataList;

  @Override protected void setUpAdapter() {
    if (null != getSupportActionBar()) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    SampleAdapter sampleAdapter =
        new SampleAdapter(new BaseViewHolder.OnItemClickListener<String>() {
          @Override public void onItemClick(View view, String item) {
            gotoNextActivity(dataList.indexOf(item));
          }
        });

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    recyclerView.setAdapter(sampleAdapter);

    sampleAdapter.setDataList(populateData());
  }

  private List<String> populateData() {
    dataList = new ArrayList<>();

    dataList.add(getString(R.string.label_simple_adapter));
    dataList.add(getString(R.string.label_simple_grid));
    dataList.add(getString(R.string.label_multi_set));
    dataList.add(getString(R.string.label_data_binding));
    dataList.add(getString(R.string.label_swipe_to_dismiss));
    dataList.add(getString(R.string.label_infinite));
    dataList.add(getString(R.string.label_expandable_item));
    dataList.add(getString(R.string.label_expandable_group));
    dataList.add(getString(R.string.label_action_mode));
    dataList.add(getString(R.string.label_drag_drop));
    dataList.add(getString(R.string.label_payload));
    dataList.add(getString(R.string.label_complex));
    return dataList;
  }

  private void gotoNextActivity(int position) {
    switch (position) {
      case 0:
        SimpleAdapterActivity.start(this);
        break;
      case 1:
        GridAdapterActivity.start(this);
        break;
      case 2:
        MultiListActivity.start(this);
        break;
      case 3:
        DataBindingActivity.start(this);
        break;
      case 4:
        SwipeToDismissActivity.start(this);
        break;
      case 5:
        InfiniteScrollActivity.start(this);
        break;
      case 6:
        ExpandableItemActivity.start(this);
        break;
      case 7:
        ExpandableGroupActivity.start(this);
        break;
      case 8:
        ContextualActionModeActivity.start(this);
        break;
      case 9:
        DragAndDropActivity.start(this);
        break;
      case 10:
        PayloadActivity.start(this);
        break;
      case 11:
        ComplexListActivity.start(this);
        break;
    }
  }
}
