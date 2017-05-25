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

package com.ahamed.sample.common.adapter;

import android.content.Context;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.util.SimpleDividerDecoration;
import com.ahamed.sample.common.binder.BirdBinder;
import com.ahamed.sample.common.binder.FlowerBinder;
import com.ahamed.sample.common.decorator.ThickItemDecorator;
import com.ahamed.sample.common.model.Bird;
import com.ahamed.sample.common.model.Flower;
import java.util.List;

public class MultiListAdapter extends RecyclerAdapter {

  private DataListManager<Bird> birdDataManager;
  private DataListManager<Flower> flowerDataManager;

  public MultiListAdapter(Context context) {
    birdDataManager = new DataListManager<>(this);
    flowerDataManager = new DataListManager<>(this);

    addDataManager(birdDataManager);
    addDataManager(flowerDataManager);

    registerBinder(new BirdBinder(new ThickItemDecorator(context)));
    registerBinder(
        new FlowerBinder(new SimpleDividerDecoration(context, SimpleDividerDecoration.VERTICAL)));
  }

  public void addBirds(List<Bird> dataList) {
    birdDataManager.set(dataList);
  }

  public void addFlowers(List<Flower> dataList) {
    flowerDataManager.addAll(dataList);
  }
}
