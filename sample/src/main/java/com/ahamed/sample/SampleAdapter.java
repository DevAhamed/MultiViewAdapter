package com.ahamed.sample;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import java.util.List;

class SampleAdapter extends RecyclerAdapter {

  private DataListManager<String> dataListManager;

  SampleAdapter(BaseViewHolder.OnItemClickListener<String> onItemClickListener) {
    dataListManager = new DataListManager<>(this);
    addDataManager(dataListManager);

    registerBinder(new SampleBinder(onItemClickListener));
  }

  void setDataList(List<String> dataList) {
    dataListManager.set(dataList);
  }
}
