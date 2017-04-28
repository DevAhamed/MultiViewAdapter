package com.ahamed.sample.complex;

import android.content.Context;
import com.ahamed.multiviewadapter.DataItemManager;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.SimpleItemDecoration;
import com.ahamed.sample.common.binder.GridItemBinder;
import com.ahamed.sample.common.binder.HeaderBinder;
import com.ahamed.sample.common.binder.ItemOneBinder;
import com.ahamed.sample.common.binder.ItemThreeBinder;
import com.ahamed.sample.common.binder.ItemTwoBinder;
import com.ahamed.sample.common.model.BaseModel;
import com.ahamed.sample.common.model.GridItem;
import com.ahamed.sample.common.model.Header;
import com.ahamed.sample.common.model.ItemThree;
import java.util.List;

public class ComplexListAdapter extends RecyclerAdapter {

  private DataListManager<ItemThree> singleModelManager;
  private DataListManager<GridItem> gridItemsManager;
  private DataListManager<BaseModel> multiItemsManager;

  public ComplexListAdapter(Context context) {
    singleModelManager = new DataListManager<>(this);
    gridItemsManager = new DataListManager<>(this);
    multiItemsManager = new DataListManager<>(this);

    addDataManager(new DataItemManager<>(this, new Header("Single model")));
    addDataManager(singleModelManager);
    addDataManager(new DataItemManager<>(this, new Header("Grid")));
    addDataManager(gridItemsManager);
    addDataManager(new DataItemManager<>(this, new Header("Models mixed")));
    addDataManager(multiItemsManager);

    registerBinder(new HeaderBinder());
    registerBinder(new GridItemBinder());
    registerBinder(
        new ItemOneBinder(new SimpleItemDecoration(context, SimpleItemDecoration.VERTICAL)));
    registerBinder(new ItemTwoBinder());
    registerBinder(new ItemThreeBinder());
  }

  public void addMultiItem(List<BaseModel> dataList) {
    multiItemsManager.addAll(dataList);
  }

  public void addGridItem(List<GridItem> dataList) {
    gridItemsManager.addAll(dataList);
  }

  public void addSingleModelItem(List<ItemThree> dataList) {
    singleModelManager.addAll(dataList);
  }
}