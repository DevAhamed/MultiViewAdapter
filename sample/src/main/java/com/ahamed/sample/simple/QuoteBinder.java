package com.ahamed.sample.simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Quote;

public class QuoteBinder extends ItemBinder<Quote, QuoteBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_quote, parent, false));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Quote;
  }

  @Override public void bind(ItemViewHolder holder, Quote item) {
    holder.tvQuotes.setText(item.getQuote());
    holder.tvAuthor.setText(item.getAuthor());
  }

  static class ItemViewHolder extends BaseViewHolder<Quote> {

    private TextView tvQuotes;
    private TextView tvAuthor;

    ItemViewHolder(View itemView) {
      super(itemView);
      tvQuotes = (TextView) itemView.findViewById(R.id.tv_quotes);
      tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
    }
  }
}