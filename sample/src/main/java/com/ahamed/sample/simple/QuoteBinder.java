package com.ahamed.sample.simple;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.BindingViewHolder;
import com.ahamed.multiviewadapter.DataBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.databinding.ItemQuoteBinding;

public class QuoteBinder extends DataBinder<Quote, ItemQuoteBinding, QuoteBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    ItemQuoteBinding binding =
        DataBindingUtil.inflate(layoutInflater, R.layout.item_quote, parent, false);
    return new ItemViewHolder(binding);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Quote;
  }

  static class ItemViewHolder extends BindingViewHolder<Quote, ItemQuoteBinding> {

    ItemViewHolder(ItemQuoteBinding binding) {
      super(binding);
    }

    @Override public void bind(ItemQuoteBinding binding, Quote item) {
      binding.setQuote(item);
    }
  }
}