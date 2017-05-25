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

package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemDecorator;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Article;

public class FeaturedArticleBinder extends ItemBinder<Article, FeaturedArticleBinder.ViewHolder> {

  public FeaturedArticleBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_article_featured, parent, false));
  }

  @Override public void bind(ViewHolder holder, Article item) {
    holder.tvTitle.setText(item.getTitle());
    holder.tvTime.setText(item.getLastUpdated());
    holder.tvCategory.setText(item.getCategory());
    holder.ivCover.setBackgroundColor(item.getCategoryColor());
    holder.ivCover.setImageResource(item.getCoverImageId());
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Article && ((Article) item).isFeatured();
  }

  static class ViewHolder extends BaseViewHolder<Article> {

    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvCategory;
    private ImageView ivCover;

    ViewHolder(View itemView) {
      super(itemView);
      tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
      ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
    }
  }
}