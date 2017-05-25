/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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