package com.ahamed.sample.infinite.scroll;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import com.ahamed.multiviewadapter.util.InfiniteLoadingHelper;
import com.ahamed.sample.R;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.adapter.MultiListAdapter;
import com.ahamed.sample.common.model.Flower;
import java.util.ArrayList;
import java.util.List;

public class InfiniteScrollActivity extends BaseActivity {

  private MultiListAdapter adapter;
  private InfiniteLoadingHelper infiniteLoadingHelper;
  private Handler handler = new Handler();
  private int currentPage;

  final Runnable runnable = new Runnable() {
    @Override public void run() {
      loadData();
    }
  };

  public static void start(Context context) {
    Intent starter = new Intent(context, InfiniteScrollActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

    adapter = new MultiListAdapter(this);

    // Only 10 pages will be loaded
    infiniteLoadingHelper = new InfiniteLoadingHelper(R.layout.item_loading_footer, 10) {
      @Override public void onLoadNextPage(int page) {
        currentPage = page + 1;
        handler.postDelayed(runnable, 2000);
      }
    };
    adapter.setInfiniteLoadingHelper(infiniteLoadingHelper);

    recyclerView.addOnScrollListener(infiniteLoadingHelper.getScrollListener());
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());
    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    loadData();
  }

  private void loadData() {
    List<Flower> flowers = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      flowers.add(new Flower(i, "Flower " + (i + currentPage * 10)));
    }
    adapter.addFlowers(flowers);
    infiniteLoadingHelper.markCurrentPageLoaded();
  }
}
