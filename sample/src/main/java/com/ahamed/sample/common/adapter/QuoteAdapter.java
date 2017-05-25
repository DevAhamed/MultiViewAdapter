package com.ahamed.sample.common.adapter;

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.sample.common.model.Quote;
import com.ahamed.sample.data.binding.QuoteDataBinder;
import com.ahamed.sample.simple.QuoteBinder;
import java.util.List;

public class QuoteAdapter extends RecyclerAdapter {

  private DataListManager<Quote> dataManager;

  public QuoteAdapter(boolean isDataBinding) {
    this.dataManager = new DataListManager<>(this);
    addDataManager(dataManager);

    if (isDataBinding) {
      registerBinder(new QuoteDataBinder());
    } else {
      registerBinder(new QuoteBinder());
    }
  }

  public void addData(List<Quote> dataList) {
    dataManager.addAll(dataList);
  }
}