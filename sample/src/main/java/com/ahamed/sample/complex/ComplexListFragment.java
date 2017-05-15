package com.ahamed.sample.complex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Article;
import com.ahamed.sample.common.model.Bike;
import com.ahamed.sample.common.model.Car;
import com.ahamed.sample.common.model.GridItem;
import com.ahamed.sample.common.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComplexListFragment extends Fragment {

  public ComplexListFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_base, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_list);
    GridLayoutManager glm = new GridLayoutManager(getContext().getApplicationContext(), 3);

    ComplexListAdapter adapter = new ComplexListAdapter(getActivity());
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

    return view;
  }
}
