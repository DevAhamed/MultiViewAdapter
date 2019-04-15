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
import android.view.View;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.NewsHeader;
import dev.ahamed.mva.sample.data.model.NewsItem;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import java.util.List;
import mva2.adapter.HeaderSection;
import mva2.adapter.ItemSection;
import mva2.adapter.ListSection;
import mva2.adapter.NestedSection;
import mva2.adapter.util.SwipeToDismissListener;

public class NewsFeedFragment extends BaseFragment implements SwipeToDismissListener<NewsItem> {

  private ListSection<NewsItem> offlineNewsSection;
  private ItemSection<String> offlineEmptySection;

  @Override public void initViews(View view) {
    NewsFeedItemBinder newsFeedItemBinder = new NewsFeedItemBinder();
    newsFeedItemBinder.addDecorator(new NewsFeedDecorator(adapter));
    OfflineNewsFeedItemBinder offlineNewsFeedBinder = new OfflineNewsFeedItemBinder();
    newsFeedItemBinder.addDecorator(new NewsFeedDecorator(adapter));
    adapter.registerItemBinders(offlineNewsFeedBinder, newsFeedItemBinder,
        new NewsHeaderItemBinder(new NewsHeaderDecorator(adapter)),
        new NewsGroupItemBinder(recyclerView), new EmptyStateItemBinder());

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(adapter.getItemDecoration());
    recyclerView.setAdapter(adapter);
    adapter.getItemTouchHelper().attachToRecyclerView(recyclerView);

    List<NewsItem> newsItems = dataManager.getNewsList(false);
    ListSection<NewsItem> firstSection = new ListSection<>();
    firstSection.addAll(newsItems);

    HeaderSection<NewsHeader, NewsItem> globalSection =
        new HeaderSection<>(new NewsHeader("Global News"));
    globalSection.getListSection().addAll(dataManager.getNewsList(false));

    NestedSection trendingSection = new NestedSection();
    ItemSection<NewsHeader> trendingItemSection =
        new ItemSection<>(new NewsHeader("Trending News"));
    trendingSection.addSection(trendingItemSection);

    ListSection<NewsItem> trendingListSectionOne = new ListSection<>();
    trendingListSectionOne.addAll(dataManager.getNewsList(false));
    trendingSection.addSection(trendingListSectionOne);

    ItemSection<List<NewsItem>> trendingHorizontalSection =
        new ItemSection<>(dataManager.getNewsList(false));
    trendingSection.addSection(trendingHorizontalSection);

    ListSection<NewsItem> trendingListSectionTwo = new ListSection<>();
    trendingListSectionTwo.addAll(dataManager.getNewsList(false));
    trendingSection.addSection(trendingListSectionTwo);

    NestedSection offlineSection = new NestedSection();
    ItemSection<NewsHeader> offlineHeaderSection =
        new ItemSection<>(new NewsHeader("Offline News"));
    offlineSection.addSection(offlineHeaderSection);

    offlineNewsSection = new ListSection<>();
    offlineNewsSection.addAll(dataManager.getNewsList(true));
    offlineNewsSection.addDecorator(new OfflineNewsDecorator(adapter, getContext()));
    offlineSection.addSection(offlineNewsSection);

    offlineNewsSection.setSwipeToDismissListener(this);

    offlineEmptySection = new ItemSection<>("No Offline Articles!");
    offlineEmptySection.hideSection();
    offlineSection.addSection(offlineEmptySection);

    adapter.addSection(firstSection);
    adapter.addSection(globalSection);
    adapter.addSection(trendingSection);
    adapter.addSection(offlineSection);
  }

  @Override public int layoutId() {
    return R.layout.fragment_news_feed;
  }

  @Override public void resetConfiguration() {
    // No-op
  }

  @Override public void updateConfiguration() {
    // No-op
  }

  @Override public void onItemDismissed(int position, NewsItem item) {
    if (offlineNewsSection.size() == 0) {
      offlineEmptySection.showSection();
    }
  }
}
