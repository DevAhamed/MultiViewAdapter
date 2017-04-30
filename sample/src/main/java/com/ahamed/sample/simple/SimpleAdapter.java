package com.ahamed.sample.simple;

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import java.util.List;

public class SimpleAdapter extends RecyclerAdapter {

  private DataListManager<ItemModel> dataManager;

  public SimpleAdapter() {
    this.dataManager = new DataListManager<>(this);
    addDataManager(dataManager);

    registerBinder(new SimpleItemBinder());
  }

  public void addData(List<ItemModel> dataList) {
    dataManager.addAll(dataList);
  }
}