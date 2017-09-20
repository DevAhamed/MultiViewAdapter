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

package com.ahamed.multiviewadapter.util;

import android.support.annotation.LayoutRes;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.DataItemManager;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemViewHolder;

/**
 * Class to add infinite loading feature into the adapter
 */
public abstract class InfiniteLoadingHelper {

  private final InfiniteLoadingBinder itemBinder;
  private final InfiniteScrollListener infiniteScrollListener;
  private final int totalPageCount;
  private DataItemManager<String> dataItemManager;
  private int currentPage;
  private boolean isLoading = false;
  private boolean canLoadMore = false;

  /**
   * @param layoutId Layout resource id - The layout which needs to be shown when the page is
   * loading.
   */
  public InfiniteLoadingHelper(@LayoutRes int layoutId) {
    this(layoutId, Integer.MAX_VALUE);
  }

  /**
   * @param layoutId Layout resource id - The layout which needs to be shown when the page is
   * loading.
   * @param totalPageCount - Total pages that needs to be loaded. By default it will be taken as
   * {@code Integer.MAX_VALUE}
   */
  public InfiniteLoadingHelper(@LayoutRes int layoutId, int totalPageCount) {
    this.itemBinder = new InfiniteLoadingBinder(layoutId);
    this.totalPageCount = totalPageCount;
    this.infiniteScrollListener = new InfiniteScrollListener(this);
  }

  /**
   * Internal method. Should not be used by clients.
   *
   * @return ItemBinder which represents the loading indicator
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public ItemBinder<String, ItemViewHolder<String>> getItemBinder() {
    return itemBinder;
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public void setDataItemManager(DataItemManager<String> dataItemManager) {
    canLoadMore = true;
    this.dataItemManager = dataItemManager;
  }

  /**
   * <p>To set the current page as loaded. The helper class will not call {@code loadNextPage(int)},
   * unless the current page is marked as loaded. </p>
   */
  public void markCurrentPageLoaded() {
    isLoading = false;
    if (!canLoadMore) {
      completeLoading();
    }
  }

  /**
   * To stop the infinite loading. This method will remove the loading indicator from the adapter.
   */
  public void markAllPagesLoaded() {
    completeLoading();
  }

  /**
   * @return ScrollListener which should be set as {@link RecyclerView}'s scroll listener
   */
  public RecyclerView.OnScrollListener getScrollListener() {
    return infiniteScrollListener;
  }

  /**
   * Abstract callback when the {@link RecyclerView} is scrolled and the next page has to be loaded
   *
   * @param page Page number
   */
  public abstract void onLoadNextPage(int page);

  ///////////////////////////////////////////
  /////////// Internal API ahead. ///////////
  ///////////////////////////////////////////

  private void loadNextPage() {
    isLoading = true;
    onLoadNextPage(currentPage++);
    if (currentPage == totalPageCount) {
      canLoadMore = false;
    }
  }

  private void completeLoading() {
    canLoadMore = false;
    dataItemManager.removeItem();
  }

  /**
   * ItemBinder class for showing the loading indicator.
   */
  private static class InfiniteLoadingBinder extends ItemBinder<String, ItemViewHolder<String>> {

    @LayoutRes private final int layoutId;

    InfiniteLoadingBinder(@LayoutRes int layoutId) {
      this.layoutId = layoutId;
    }

    @Override
    public final ItemViewHolder<String> create(LayoutInflater inflater, ViewGroup parent) {
      return new ItemViewHolder<>(inflater.inflate(layoutId, parent, false));
    }

    @Override public final void bind(ItemViewHolder holder, String item) {
      // No-op
    }

    @Override public final boolean canBindData(Object item) {
      return item instanceof String && item.equals("LoadingItem");
    }

    @Override public int getSpanSize(int maxSpanCount) {
      return maxSpanCount;
    }
  }

  /**
   * Scroll listener for the recyclerview with necessary callbacks to the adapter.
   */
  private static class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private final InfiniteLoadingHelper loadingHelper;

    InfiniteScrollListener(InfiniteLoadingHelper loadingHelper) {
      this.loadingHelper = loadingHelper;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      if (dy > 0 && !loadingHelper.isLoading && loadingHelper.canLoadMore) {
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int lastVisibleItemPosition = 0;
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
          int[] lastVisibleItemPositions =
              ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPositions(
                  null);
          lastVisibleItemPosition = lastVisibleItemPositions[lastVisibleItemPositions.length - 1];
        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
          lastVisibleItemPosition =
              ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }

        if (lastVisibleItemPosition + 1 >= totalItemCount) {
          loadingHelper.loadNextPage();
        }
      }
    }
  }
}
