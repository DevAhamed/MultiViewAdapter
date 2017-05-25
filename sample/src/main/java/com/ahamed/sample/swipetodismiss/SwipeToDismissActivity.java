package com.ahamed.sample.swipetodismiss;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.adapter.MultiListAdapter;
import com.ahamed.sample.common.model.Flower;
import java.util.ArrayList;
import java.util.List;

public class SwipeToDismissActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, SwipeToDismissActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

    MultiListAdapter adapter = new MultiListAdapter(this);
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());

    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);
    adapter.getItemTouchHelper().attachToRecyclerView(recyclerView);

    List<Flower> flowers = new ArrayList<>();
    for (int i = 0; i < 25; i++) {
      flowers.add(new Flower(i, "Flower " + i));
    }
    adapter.addFlowers(flowers);
  }
}
