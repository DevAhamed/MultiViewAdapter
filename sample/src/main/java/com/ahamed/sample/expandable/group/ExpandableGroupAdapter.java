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

package com.ahamed.sample.expandable.group;

import android.view.View;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.DataGroupManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.ahamed.sample.common.binder.BirdBinder;
import com.ahamed.sample.common.binder.FlowerBinder;
import com.ahamed.sample.common.model.Bird;
import com.ahamed.sample.common.model.Flower;
import com.ahamed.sample.common.model.Header;
import com.ahamed.sample.expandable.ExpandableItemBinder;
import java.util.List;

public class ExpandableGroupAdapter extends RecyclerAdapter
    implements BaseViewHolder.OnItemClickListener<Bird> {

  private DataGroupManager<Header, Bird> birdsGroup;
  private DataGroupManager<Header, Flower> flowerGroup;
  private DataGroupManager<Header, String> expandableItemGroup;

  public ExpandableGroupAdapter(ItemDecorator dividerDecoration) {
    birdsGroup = new DataGroupManager<>(this, new Header("Birds"));
    flowerGroup = new DataGroupManager<>(this, new Header("Flowers"));
    expandableItemGroup = new DataGroupManager<>(this, new Header("Expandable Items"));

    addDataManager(birdsGroup);
    addDataManager(flowerGroup);
    addDataManager(expandableItemGroup);

    registerBinder(new ExpandableHeaderBinder());
    registerBinder(new ExpandableItemBinder());
    registerBinder(new BirdBinder(dividerDecoration, this));
    registerBinder(new FlowerBinder(dividerDecoration));
  }

  void addBirds(List<Bird> dataList) {
    birdsGroup.set(dataList);
  }

  void addFlowers(List<Flower> dataList) {
    flowerGroup.set(dataList);
  }

  void addFlower(Flower dataList) {
    flowerGroup.add(2, dataList);
  }

  void addItems(List<String> dataList) {
    expandableItemGroup.set(dataList);
  }

  @Override public void onItemClick(View view, Bird item) {
    addFlower(new Flower(100, "New Flower"));
  }
}
