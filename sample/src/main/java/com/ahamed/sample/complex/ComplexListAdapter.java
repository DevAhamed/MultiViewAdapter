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

package com.ahamed.sample.complex;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.ahamed.multiviewadapter.DataItemManager;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.SimpleItemDecoration;
import com.ahamed.sample.common.binder.AdvertisementBinder;
import com.ahamed.sample.common.binder.ArticleBinder;
import com.ahamed.sample.common.binder.BikeBinder;
import com.ahamed.sample.common.binder.CarBinder;
import com.ahamed.sample.common.binder.FeaturedArticleBinder;
import com.ahamed.sample.common.binder.GridItemBinder;
import com.ahamed.sample.common.binder.HeaderBinder;
import com.ahamed.sample.common.binder.ShufflingHeaderBinder;
import com.ahamed.sample.common.decorator.ArticleItemDecorator;
import com.ahamed.sample.common.model.Article;
import com.ahamed.sample.common.model.GridItem;
import com.ahamed.sample.common.model.Header;
import com.ahamed.sample.common.model.Vehicle;
import java.util.Collections;
import java.util.List;

public class ComplexListAdapter extends RecyclerAdapter {

  private DataListManager<Article> singleModelManager;
  private DataListManager<GridItem> gridItemsManager;
  private DataListManager<Vehicle> multiItemsManager;

  private List<GridItem> gridDataList;

  private SimpleItemDecoration simpleItemDecoration;

  public ComplexListAdapter(Context context) {
    simpleItemDecoration = new SimpleItemDecoration(context, SimpleItemDecoration.VERTICAL);

    singleModelManager = new DataListManager<>(this);
    gridItemsManager = new DataListManager<>(this);
    multiItemsManager = new DataListManager<>(this);

    addDataManager(new DataItemManager<>(this, new Header("Articles")));
    addDataManager(singleModelManager);
    addDataManager(new DataItemManager<>(this, DummyDataProvider.getAdvertisementOne()));
    addDataManager(new DataItemManager<>(this, new Header("Grid", true)));
    addDataManager(gridItemsManager);
    addDataManager(new DataItemManager<>(this, DummyDataProvider.getAdvertisementTwo()));
    addDataManager(new DataItemManager<>(this, new Header("Multiple ViewTypes")));
    addDataManager(multiItemsManager);
    addDataManager(new DataItemManager<>(this, DummyDataProvider.getAdvertisementThree()));

    registerBinder(new HeaderBinder());
    registerBinder(new GridItemBinder(convertDpToPixel(4, context)));
    registerBinder(new CarBinder(simpleItemDecoration));
    registerBinder(new BikeBinder(simpleItemDecoration));
    registerBinder(new FeaturedArticleBinder(new ArticleItemDecorator()));
    registerBinder(new ArticleBinder(new ArticleItemDecorator()));
    registerBinder(new AdvertisementBinder());
    registerBinder(new ShufflingHeaderBinder(new ShufflingHeaderBinder.ShuffleListener() {
      @Override public void onShuffleClicked() {
        Collections.shuffle(gridDataList);
        gridItemsManager.set(gridDataList);
      }
    }));
  }

  public void addMultiItem(List<Vehicle> dataList) {
    multiItemsManager.addAll(dataList);
  }

  public void addGridItem(List<GridItem> dataList) {
    this.gridDataList = dataList;
    gridItemsManager.addAll(gridDataList);
  }

  public void addSingleModelItem(List<Article> dataList) {
    singleModelManager.addAll(dataList);
  }

  public static int convertDpToPixel(float dp, Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    return (int) (dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
  }
}