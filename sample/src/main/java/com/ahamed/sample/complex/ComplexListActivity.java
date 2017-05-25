package com.ahamed.sample.complex;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.model.Article;
import com.ahamed.sample.common.model.Bike;
import com.ahamed.sample.common.model.Car;
import com.ahamed.sample.common.model.GridItem;
import com.ahamed.sample.common.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComplexListActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, ComplexListActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 3);

    ComplexListAdapter adapter = new ComplexListAdapter(this);
    adapter.setSpanCount(3);

    glm.setSpanSizeLookup(adapter.getSpanSizeLookup());
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());
    recyclerView.setLayoutManager(glm);
    recyclerView.setAdapter(adapter);

    // Single list
    List<Article> dataListThree = DummyDataProvider.getArticles();
    adapter.addSingleModelItem(dataListThree);

    // Grid items
    List<GridItem> gridDataList = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      gridDataList.add(GridItem.generateGridItem(i));
    }
    adapter.addGridItem(gridDataList);

    // Single list with two binders
    List<Vehicle> multiItemList = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      if (random.nextBoolean()) {
        multiItemList.add(new Car(i, "Car " + i, "Manufacturer " + i,
            String.valueOf(1900 + random.nextInt(100))));
      } else {
        multiItemList.add(new Bike(i, "Bike " + i, "Description of bike" + i));
      }
    }
    adapter.addMultiItem(multiItemList);
  }
}
