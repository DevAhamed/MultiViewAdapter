# Infinite Scrolling

Infinite loading is a composable feature available in MultiViewAdapter ie., you don't need to create a separate adapter. You just need to create an ``InfiniteLoadingHelper`` object and set it to adapter.

### Usage

```java
  protected void setUpAdapter() {
    // Only 10 pages will be loaded
    infiniteLoadingHelper = new InfiniteLoadingHelper(recyclerView, R.layout.item_loading_footer, 10) {
      @Override public void onLoadNextPage(int page) {
        // Load next page
      }
    };
    adapter.setInfiniteLoadingHelper(infiniteLoadingHelper);
  }
```

Page number is an optional parameter. This allows you to have dynamic page numbers.

Once the page is loaded call ``infiniteLoadingHelper.markCurrentPageLoaded()``. If you want to stop infinite loading, call ``infiniteLoadingHelper.markAllPagesLoaded()``.

!> Make sure you are setting the InfiniteLoadingHelper after all the ``Section``'s are added to the adapter.