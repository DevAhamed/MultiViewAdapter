package com.ahamed.sample.data.binding;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.BindingViewHolder;
import com.ahamed.multiviewadapter.ItemDataBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Quote;
import com.ahamed.sample.databinding.ItemBindingQuoteBinding;

public class QuoteDataBinder
    extends ItemDataBinder<Quote, ItemBindingQuoteBinding, QuoteDataBinder.ViewHolder> {

  @Override public ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
    ItemBindingQuoteBinding quoteBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_binding_quote, parent, false);
    return new ViewHolder(quoteBinding);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Quote;
  }

  static class ViewHolder extends BindingViewHolder<Quote, ItemBindingQuoteBinding> {

    public ViewHolder(ItemBindingQuoteBinding binding) {
      super(binding);
    }

    @Override public void bind(ItemBindingQuoteBinding binding, Quote item) {
      binding.setQuoteModel(item);
    }
  }
}
