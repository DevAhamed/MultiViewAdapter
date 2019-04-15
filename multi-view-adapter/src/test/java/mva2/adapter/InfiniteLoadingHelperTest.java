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

package mva2.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SpanSizeLookup;
import android.support.v7.widget.StaggeredGridLayoutManager;
import mva2.adapter.testconfig.AdapterDataObserver;
import mva2.adapter.util.InfiniteLoadingHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class InfiniteLoadingHelperTest {

  private InfiniteLoadingHelper infiniteLoadingHelper;

  private MultiViewAdapter adapter;
  @Mock private AdapterDataObserver adapterDataObserver;
  @Mock private RecyclerView recyclerView;
  @Mock private PageLoadTester pageLoadTester;
  @Mock private SpanSizeLookup spanSizeLookup;

  @Test public void maxCount_constructor() {
    infiniteLoadingHelper = new InfiniteLoadingHelper(recyclerView, 0, 2) {
      @Override public void onLoadNextPage(int page) {
        pageLoadTester.onLoadNextPage(page);
        infiniteLoadingHelper.markCurrentPageLoaded();
      }
    };

    StaggeredGridLayoutManager layoutManager = Mockito.mock(StaggeredGridLayoutManager.class);
    ArgumentCaptor<RecyclerView.OnScrollListener> captor =
        ArgumentCaptor.forClass(RecyclerView.OnScrollListener.class);
    adapter.setInfiniteLoadingHelper(infiniteLoadingHelper);
    verify(recyclerView).addOnScrollListener(captor.capture());

    RecyclerView.OnScrollListener scrollListener = captor.getValue();

    when(recyclerView.getLayoutManager()).thenReturn(layoutManager);
    when(layoutManager.findLastVisibleItemPositions(null)).thenReturn(new int[] { 9 });
    when(layoutManager.getItemCount()).thenReturn(10);

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester).onLoadNextPage(1);
    infiniteLoadingHelper.markCurrentPageLoaded();

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester).onLoadNextPage(2);
    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester, never()).onLoadNextPage(3);
    infiniteLoadingHelper.markCurrentPageLoaded();

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester, never()).onLoadNextPage(3);
  }

  @Test public void maxCount_set() {
    infiniteLoadingHelper = new InfiniteLoadingHelper(recyclerView, 0) {
      @Override public void onLoadNextPage(int page) {
        pageLoadTester.onLoadNextPage(page);
        infiniteLoadingHelper.markCurrentPageLoaded();
      }
    };

    StaggeredGridLayoutManager layoutManager = Mockito.mock(StaggeredGridLayoutManager.class);
    ArgumentCaptor<RecyclerView.OnScrollListener> captor =
        ArgumentCaptor.forClass(RecyclerView.OnScrollListener.class);
    adapter.setInfiniteLoadingHelper(infiniteLoadingHelper);
    verify(recyclerView).addOnScrollListener(captor.capture());

    RecyclerView.OnScrollListener scrollListener = captor.getValue();

    when(recyclerView.getLayoutManager()).thenReturn(layoutManager);
    when(layoutManager.findLastVisibleItemPositions(null)).thenReturn(new int[] { 9 });
    when(layoutManager.getItemCount()).thenReturn(10);

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester).onLoadNextPage(1);
    infiniteLoadingHelper.markCurrentPageLoaded();

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester).onLoadNextPage(2);
    infiniteLoadingHelper.markCurrentPageLoaded();

    infiniteLoadingHelper.setPageCount(3);

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester).onLoadNextPage(3);

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester, never()).onLoadNextPage(4);
    infiniteLoadingHelper.markCurrentPageLoaded();

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester, never()).onLoadNextPage(4);
  }

  @Test public void noMaxCountTest() {
    infiniteLoadingHelper = new InfiniteLoadingHelper(recyclerView, 0) {
      @Override public void onLoadNextPage(int page) {
        pageLoadTester.onLoadNextPage(page);
      }
    };

    LinearLayoutManager layoutManager = Mockito.mock(LinearLayoutManager.class);
    ArgumentCaptor<RecyclerView.OnScrollListener> captor =
        ArgumentCaptor.forClass(RecyclerView.OnScrollListener.class);
    adapter.setInfiniteLoadingHelper(infiniteLoadingHelper);
    verify(recyclerView).addOnScrollListener(captor.capture());

    RecyclerView.OnScrollListener scrollListener = captor.getValue();

    when(recyclerView.getLayoutManager()).thenReturn(layoutManager);
    when(layoutManager.findLastVisibleItemPosition()).thenReturn(9);
    when(layoutManager.getItemCount()).thenReturn(10);

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester).onLoadNextPage(1);
    infiniteLoadingHelper.markCurrentPageLoaded();

    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester).onLoadNextPage(2);
    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester, never()).onLoadNextPage(3);
    infiniteLoadingHelper.markCurrentPageLoaded();

    infiniteLoadingHelper.markAllPagesLoaded();
    scrollListener.onScrolled(recyclerView, 0, 10);
    verify(pageLoadTester, never()).onLoadNextPage(3);
  }

  @Before public void setUp() {
    adapter = new TestAdapter(adapterDataObserver, spanSizeLookup);
  }

  static class PageLoadTester {
    void onLoadNextPage(int page) {
      // Do Nothing
    }
  }
}
