package com.ahamed.sample.multilist;

import android.content.Context;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.sample.common.ThickItemDecorator;
import com.ahamed.sample.common.binder.ItemOneBinder;
import com.ahamed.sample.common.binder.ItemThreeBinder;
import com.ahamed.sample.common.binder.ItemTwoBinder;
import com.ahamed.sample.common.model.ItemOne;
import com.ahamed.sample.common.model.ItemThree;
import com.ahamed.sample.common.model.ItemTwo;
import java.util.List;

public class MultiListAdapter extends RecyclerAdapter {

  private DataListManager<ItemOne> modelOneDataManager;
  private DataListManager<ItemTwo> modelTwoDataManager;
  private DataListManager<ItemThree> modelThreeDataManager;

  public MultiListAdapter(Context context) {
    modelOneDataManager = new DataListManager<>(this);
    modelTwoDataManager = new DataListManager<>(this);
    modelThreeDataManager = new DataListManager<>(this);

    addDataManager(modelOneDataManager);
    addDataManager(modelTwoDataManager);
    addDataManager(modelThreeDataManager);

    registerBinder(new ItemOneBinder(new ThickItemDecorator(context)));
    registerBinder(new ItemTwoBinder());
    registerBinder(new ItemThreeBinder());
  }

  public void addDataOne(List<ItemOne> dataList) {
    modelOneDataManager.addAll(dataList);
  }

  public void addDataTwo(List<ItemTwo> dataList) {
    modelTwoDataManager.addAll(dataList);
  }

  public void addDataThree(List<ItemThree> dataList) {
    modelThreeDataManager.addAll(dataList);
  }
}
