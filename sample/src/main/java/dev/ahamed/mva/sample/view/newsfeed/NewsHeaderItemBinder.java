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

package dev.ahamed.mva.sample.view.newsfeed;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.NewsHeader;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.decorator.Decorator;

public class NewsHeaderItemBinder extends ItemBinder<NewsHeader, NewsHeaderItemBinder.ViewHolder> {

  public NewsHeaderItemBinder(Decorator decorator) {
    super(decorator);
  }

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_news_header));
  }

  @Override public void bindViewHolder(ViewHolder holder, NewsHeader item) {
    holder.header.setText(item.getHeader());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof NewsHeader;
  }

  static class ViewHolder extends ItemViewHolder<NewsHeader> {

    private TextView header;

    public ViewHolder(View itemView) {
      super(itemView);
      header = itemView.findViewById(R.id.tv_news_header);
    }
  }
}
