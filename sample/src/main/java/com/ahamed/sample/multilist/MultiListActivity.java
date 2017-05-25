package com.ahamed.sample.multilist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.adapter.MultiListAdapter;
import com.ahamed.sample.common.model.Bird;
import com.ahamed.sample.common.model.Flower;
import java.util.ArrayList;
import java.util.List;

public class MultiListActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, MultiListActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

    MultiListAdapter adapter = new MultiListAdapter(this);
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());

    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<Bird> birds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      birds.add(new Bird("Bird " + i));
    }
    adapter.addBirds(birds);

    List<Flower> flowers = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      flowers.add(new Flower(i, "Flower " + i));
    }
    adapter.addFlowers(flowers);
  }
}