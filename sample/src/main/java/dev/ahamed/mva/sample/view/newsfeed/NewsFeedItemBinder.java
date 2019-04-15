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
import android.widget.ImageView;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.NewsItem;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class NewsFeedItemBinder extends ItemBinder<NewsItem, NewsFeedItemBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_news));
  }

  @Override public void bindViewHolder(ViewHolder holder, NewsItem item) {
    holder.newsSource.setText(item.getSource());
    holder.title.setText(item.getTitle());
    holder.date.setText(item.getTime());
    holder.image.setBackgroundColor(item.getThumbNailColor());
    holder.image.setImageResource(item.getThumbNailId());

    holder.newsSource.setTextColor(item.getSourceColor());
    holder.newsSource.setCompoundDrawablesWithIntrinsicBounds(item.getSourceLogo(), 0, 0, 0);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof NewsItem;
  }

  static class ViewHolder extends ItemViewHolder<NewsItem> {

    private final TextView newsSource;
    private final TextView title;
    private final ImageView image;
    private final TextView date;

    public ViewHolder(View itemView) {
      super(itemView);

      newsSource = itemView.findViewById(R.id.tv_source);
      title = itemView.findViewById(R.id.tv_title);
      image = itemView.findViewById(R.id.image);
      date = itemView.findViewById(R.id.tv_news_date);
    }
  }
}
