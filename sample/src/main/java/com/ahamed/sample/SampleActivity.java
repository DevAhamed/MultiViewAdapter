package com.ahamed.sample;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.data.binding.DataBindingActivity;
import com.ahamed.sample.grid.GridAdapterActivity;
import com.ahamed.sample.infinite.scroll.InfiniteScrollActivity;
import com.ahamed.sample.multilist.MultiListActivity;
import com.ahamed.sample.simple.SimpleAdapterActivity;
import com.ahamed.sample.swipetodismiss.SwipeToDismissActivity;
import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends BaseActivity {

  private List<String> dataList;

  @Override protected void setUpAdapter() {
    if (null != getSupportActionBar()) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    SampleAdapter sampleAdapter =
        new SampleAdapter(new BaseViewHolder.OnItemClickListener<String>() {
          @Override public void onItemClick(View view, String item) {
            gotoNextActivity(dataList.indexOf(item));
          }
        });

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    recyclerView.setAdapter(sampleAdapter);

    sampleAdapter.setDataList(populateData());
  }

  private List<String> populateData() {
    dataList = new ArrayList<>();

    dataList.add(getString(R.string.label_simple_adapter));
    dataList.add(getString(R.string.label_simple_grid));
    dataList.add("Multiple data set");
    dataList.add(getString(R.string.label_data_binding));
    dataList.add("Swipe to dismiss");
    dataList.add("Infinite scrolling");
    dataList.add("Contextual action mode");
    return dataList;
  }

  private void gotoNextActivity(int position) {
    switch (position) {
      case 0:
        SimpleAdapterActivity.start(this);
        break;
      case 1:
        GridAdapterActivity.start(this);
        break;
      case 2:
        MultiListActivity.start(this);
        break;
      case 3:
        DataBindingActivity.start(this);
        break;
      case 4:
        SwipeToDismissActivity.start(this);
        break;
      case 5:
        InfiniteScrollActivity.start(this);
        break;
    }
  }
}
