package com.ahamed.sample.grid;

import android.util.Log;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.SelectableAdapter;
import com.ahamed.multiviewadapter.listener.ItemSelectionChangedListener;
import com.ahamed.sample.common.binder.GridItemBinder;
import com.ahamed.sample.common.binder.HeaderBinder;
import com.ahamed.sample.common.model.BaseModel;
import com.ahamed.sample.common.model.GridItem;
import java.util.List;

class GridAdapter extends SelectableAdapter {

  private DataListManager<BaseModel> dataManager;

  GridAdapter() {
    dataManager = new DataListManager<>(this);
    addDataManager(dataManager);

    registerBinder(new HeaderBinder());
    registerBinder(new GridItemBinder());

    setSelectionMode(SELECTION_MODE_SINGLE);

    dataManager.setItemSelectionChangedListener(new ItemSelectionChangedListener<BaseModel>() {
      @Override public void onItemSelectionChangedListener(BaseModel item, boolean isSelected) {
        Log.d("GridAdapter",
            "Item toggled : " + ((GridItem) item).getData() + "\nSelection : " + isSelected);
      }
    });
  }

  void addData(List<BaseModel> data) {
    dataManager.addAll(data);
    dataManager.setSelectedItem(dataManager.get(2));
  }
}
