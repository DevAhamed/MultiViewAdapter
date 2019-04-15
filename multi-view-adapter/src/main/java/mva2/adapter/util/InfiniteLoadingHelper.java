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

package mva2.adapter.util;

import android.view.ViewGroup;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemSection;
import mva2.adapter.ItemViewHolder;

/**
 * Class to add infinite loading feature into the adapter
 */
@SuppressWarnings("WeakerAccess") public abstract class InfiniteLoadingHelper {

  private final InfiniteLoadingItemBinder itemBinder;
  private int totalPageCount;
  private ItemSection<String> footerSection;
  private int currentPage = 0;
  private boolean isLoading = false;
  private boolean canLoadMore = false;

  /**
   * @param layoutId Layout resource id - The layout which needs to be shown when the page is
   *                 loading.
   */
  public InfiniteLoadingHelper(RecyclerView recyclerView, @LayoutRes int layoutId) {
    this(recyclerView, layoutId, Integer.MAX_VALUE);
  }

  /**
   * @param layoutId       Layout resource id - The layout which needs to be shown when the page is
   *                       loading.
   * @param totalPageCount - Total pages that needs to be loaded. By default it will be taken as
   *                       {@code Integer.MAX_VALUE}
   */
  public InfiniteLoadingHelper(RecyclerView recyclerView, @LayoutRes int layoutId,
      int totalPageCount) {
    this.itemBinder = new InfiniteLoadingItemBinder(layoutId);
    this.totalPageCount = totalPageCount;
    InfiniteScrollListener infiniteScrollListener = new InfiniteScrollListener(this);
    recyclerView.addOnScrollListener(infiniteScrollListener);
  }

  /**
   * To stop the infinite loading. This method will remove the loading indicator from the adapter.
   */
  public void markAllPagesLoaded() {
    completeLoading();
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
   * Set the page count for infinite loading progress. Pages indicates that how many times the
   * infinite loading should be called. If the page count is unknown, infinite loading will happen
   * continuously.
   *
   * @param totalPageCount Page count for the infinite loading progress
   */
  public void setPageCount(int totalPageCount) {
    if (totalPageCount > currentPage && !canLoadMore) {
      canLoadMore = true;
      footerSection.showSection();
    }

    if (totalPageCount < this.currentPage) {
      completeLoading();
    }

    this.totalPageCount = totalPageCount;
  }

  /**
   * Abstract callback when the {@link RecyclerView} is scrolled and the next page has to be loaded
   *
   * @param page Page number
   */
  public abstract void onLoadNextPage(int page);

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  /**
   * Internal method. Should not be used by clients.
   *
   * @return ItemBinder which represents the loading indicator
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public final ItemBinder<String, ItemViewHolder<String>> getItemBinder() {
    return itemBinder;
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public final void setFooterSection(ItemSection<String> footerSection) {
    canLoadMore = true;
    this.footerSection = footerSection;
  }

  private void completeLoading() {
    canLoadMore = false;
    footerSection.hideSection();
  }

  private void loadNextPage() {
    isLoading = true;
    onLoadNextPage(++currentPage);
    if (currentPage >= totalPageCount) {
      canLoadMore = false;
    }
  }

  /**
   * ItemBinder class for showing the loading indicator.
   */
  private static class InfiniteLoadingItemBinder
      extends ItemBinder<String, ItemViewHolder<String>> {

    @LayoutRes private final int layoutId;

    InfiniteLoadingItemBinder(@LayoutRes int layoutId) {
      this.layoutId = layoutId;
    }

    @Override public int getSpanSize(int maxSpanCount) {
      return maxSpanCount;
    }

    @Override public final ItemViewHolder<String> createViewHolder(ViewGroup parent) {
      return new ItemViewHolder<>(inflate(parent, layoutId));
    }

    @Override public final void bindViewHolder(ItemViewHolder holder, String item) {
      // No-op
    }

    @Override public final boolean canBindData(Object item) {
      return item instanceof String && item.equals("LoadingItem");
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

    @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
      if (dy > 0 && !loadingHelper.isLoading && loadingHelper.canLoadMore) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
          return;
        }
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
