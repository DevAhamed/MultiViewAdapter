package com.ahamed.sample.grid;

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.sample.common.binder.GridItemBinder;
import com.ahamed.sample.common.binder.HeaderBinder;
import com.ahamed.sample.common.model.BaseModel;
import java.util.List;

class GridAdapter extends RecyclerAdapter {

  private DataListManager<BaseModel> dataManager;

  GridAdapter() {
    dataManager = new DataListManager<>(this);
    addDataManager(dataManager);

    registerBinder(new HeaderBinder());
    registerBinder(new GridItemBinder());
  }

  void addData(List<BaseModel> data) {
    dataManager.addAll(data);
  }
}