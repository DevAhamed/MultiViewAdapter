package com.ahamed.sample.grid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.model.BaseModel;
import com.ahamed.sample.common.model.GridItem;
import com.ahamed.sample.common.model.Header;
import java.util.ArrayList;
import java.util.List;

public class GridAdapterActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, GridAdapterActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 3);

    GridAdapter adapter = new GridAdapter();
    adapter.setSpanCount(3);

    recyclerView.addItemDecoration(adapter.getItemDecorationManager());

    glm.setSpanSizeLookup(adapter.getSpanSizeLookup());
    recyclerView.setLayoutManager(glm);
    recyclerView.setAdapter(adapter);

    List<BaseModel> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      if (i % 10 == 0) {
        data.add(new Header("Sample header " + i));
      } else {
        data.add(GridItem.generateGridItem(i));
      }
    }
    adapter.addData(data);
  }
}