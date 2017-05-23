package com.ahamed.multiviewadapter.util;

import android.support.annotation.LayoutRes;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.DataItemManager;
import com.ahamed.multiviewadapter.ItemBinder;

public abstract class InfiniteLoadingHelper {

  private DataItemManager<String> dataItemManager;
  private final InfiniteLoadingBinder itemBinder;
  private final InfiniteScrollListener infiniteScrollListener;
  private int totalPageCount;
  private int currentPage;
  private boolean isLoading = false;
  private boolean canLoadMore = false;

  public InfiniteLoadingHelper(@LayoutRes int layoutId) {
    this(layoutId, Integer.MAX_VALUE);
  }

  public InfiniteLoadingHelper(@LayoutRes int layoutId, int totalPageCount) {
    this.itemBinder = new InfiniteLoadingBinder(layoutId);
    this.totalPageCount = totalPageCount;
    this.infiniteScrollListener = new InfiniteScrollListener(this);
  }

  public InfiniteLoadingBinder getItemBinder() {
    return itemBinder;
  }

  private static class InfiniteLoadingBinder extends ItemBinder<String, BaseViewHolder<String>> {

    @LayoutRes private final int layoutId;

    InfiniteLoadingBinder(@LayoutRes int layoutId) {
      this.layoutId = layoutId;
    }

    @Override
    public final BaseViewHolder<String> create(LayoutInflater inflater, ViewGroup parent) {
      return new BaseViewHolder<>(inflater.inflate(layoutId, parent, false));
    }

    @Override public final void bind(BaseViewHolder holder, String item) {
      // No-op
    }

    @Override public final boolean canBindData(Object item) {
      return item instanceof String && item.equals("LoadingItem");
    }

    @Override public int getSpanSize(int maxSpanCount) {
      return maxSpanCount;
    }
  }

  private static class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private InfiniteLoadingHelper loadingHelper;

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
    onAllPagesLoaded();
  }

  public void markCurrentPageLoaded() {
    isLoading = false;
  }

  public void markAllPagesLoaded() {
    completeLoading();
  }

  public RecyclerView.OnScrollListener getScrollListener() {
    return infiniteScrollListener;
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  public void setDataItemManager(DataItemManager<String> dataItemManager) {
    canLoadMore = true;
    this.dataItemManager = dataItemManager;
  }

  public abstract void onLoadNextPage(int page);

  public abstract void onAllPagesLoaded();
}