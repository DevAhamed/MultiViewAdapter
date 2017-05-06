package com.ahamed.sample.simple;

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import java.util.List;

public class QuoteAdapter extends RecyclerAdapter {

  private DataListManager<Quote> dataManager;

  public QuoteAdapter() {
    this.dataManager = new DataListManager<>(this);
    addDataManager(dataManager);

    registerBinder(new QuoteBinder());
  }

  public void addData(List<Quote> dataList) {
    dataManager.addAll(dataList);
  }
}