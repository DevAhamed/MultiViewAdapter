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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.NewsItem;
import java.util.List;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.ListSection;
import mva2.adapter.MultiViewAdapter;

public class NewsGroupItemBinder
    extends ItemBinder<List<NewsItem>, NewsGroupItemBinder.ViewHolder> {

  private RecyclerView.RecycledViewPool recycledViewPool;

  NewsGroupItemBinder(RecyclerView recyclerView) {
    this.recycledViewPool = recyclerView.getRecycledViewPool();
  }

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    ViewHolder viewHolder = new ViewHolder(inflate(parent, R.layout.item_news_group));
    viewHolder.recyclerView.setRecycledViewPool(recycledViewPool);
    return viewHolder;
  }

  @Override public void bindViewHolder(ViewHolder holder, List<NewsItem> item) {
    MultiViewAdapter adapter = new MultiViewAdapter();
    if (holder.recyclerView.getItemDecorationCount() > 0) {
      holder.recyclerView.removeItemDecorationAt(0);
    }
    holder.recyclerView.addItemDecoration(adapter.getItemDecoration());
    holder.recyclerView.setLayoutManager(
        new LinearLayoutManager(holder.recyclerView.getContext(), LinearLayoutManager.HORIZONTAL,
            false));
    ListSection<NewsItem> newsListSection = new ListSection<>();
    newsListSection.addAll(item);
    newsListSection.addDecorator(new NewsSectionDecorator(adapter));
    adapter.addSection(newsListSection);
    adapter.registerItemBinders(new NewsFeedItemBinder());
    holder.recyclerView.setAdapter(adapter);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof List;
  }

  static class ViewHolder extends ItemViewHolder<List<NewsItem>> {

    RecyclerView recyclerView;

    public ViewHolder(View itemView) {
      super(itemView);
      recyclerView = itemView.findViewById(R.id.nested_recycler_view);
    }
  }
}
