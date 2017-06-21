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

package com.ahamed.sample.payload;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.util.PayloadProvider;
import com.ahamed.multiviewadapter.util.SimpleDividerDecoration;
import com.ahamed.sample.R;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.model.Flower;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ahamed.multiviewadapter.util.SimpleDividerDecoration.VERTICAL;

public class PayloadActivity extends BaseActivity {

  private Random random = new Random();

  private DataListManager<Flower> dataListManager;

  private int itemIndex = 0;

  public static void start(Context context) {
    Intent starter = new Intent(context, PayloadActivity.class);
    context.startActivity(starter);
  }

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(R.layout.activity_payload);
  }

  @Override protected void setUpAdapter() {

    RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
    dataListManager = new DataListManager<>(recyclerAdapter, new PayloadProvider<Flower>() {
      @Override public boolean areContentsTheSame(Flower oldItem, Flower newItem) {
        return oldItem.getFlowerId() == newItem.getFlowerId();
      }

      @Override public Object getChangePayload(Flower oldItem, Flower newItem) {
        return newItem.getFlowerName();
      }
    });

    recyclerAdapter.registerBinder(
        new FlowerBinderWithPayload(new SimpleDividerDecoration(this, VERTICAL)));
    recyclerAdapter.addDataManager(dataListManager);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.addItemDecoration(recyclerAdapter.getItemDecorationManager());
    recyclerView.setAdapter(recyclerAdapter);

    List<Flower> dataList = new ArrayList<>();
    for (; itemIndex < 3; itemIndex++) {
      dataList.add(new Flower(itemIndex, "Flower " + itemIndex));
    }
    dataListManager.set(dataList);
  }

  public void add(View view) {
    Flower flower = new Flower(itemIndex, "Flower " + itemIndex);
    //if (random.nextBoolean()) {
      dataListManager.add(flower);
    //} else {
    //  int indexToAdd = random.nextInt(dataListManager.getCount() - 1);
    //  dataListManager.add(indexToAdd, flower);
    //}
    itemIndex++;
  }

  public void remove(View view) {
    if (dataListManager.getCount() == 0) {
      Toast.makeText(this, "No items in adapter to remove!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (random.nextBoolean()) {
      dataListManager.remove(0);
    } else {
      int indexToRemove = random.nextInt(dataListManager.getCount() - 1);
      dataListManager.remove(indexToRemove);
    }
  }

  public void update(View view) {
    if (dataListManager.getCount() == 0) {
      Toast.makeText(this, "No items in adapter to update!", Toast.LENGTH_SHORT).show();
      return;
    }

    int indexToUpdate = random.nextBoolean() ? 0 : random.nextInt(dataListManager.getCount() - 1);

    Flower flower = dataListManager.get(indexToUpdate);
    flower.setFlowerName("Updated Flower " + flower.getFlowerId());

    dataListManager.set(indexToUpdate, flower);
  }
}
