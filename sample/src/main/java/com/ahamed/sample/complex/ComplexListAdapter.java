package com.ahamed.sample.complex;

import android.content.Context;
import com.ahamed.multiviewadapter.DataItemManager;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.SimpleItemDecoration;
import com.ahamed.sample.common.ArticleItemDecorator;
import com.ahamed.sample.common.binder.AdvertisementBinder;
import com.ahamed.sample.common.binder.ArticleBinder;
import com.ahamed.sample.common.binder.BikeBinder;
import com.ahamed.sample.common.binder.CarBinder;
import com.ahamed.sample.common.binder.FeaturedArticleBinder;
import com.ahamed.sample.common.binder.GridItemBinder;
import com.ahamed.sample.common.binder.HeaderBinder;
import com.ahamed.sample.common.binder.ShufflingHeaderBinder;
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
    registerBinder(new GridItemBinder());
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
}