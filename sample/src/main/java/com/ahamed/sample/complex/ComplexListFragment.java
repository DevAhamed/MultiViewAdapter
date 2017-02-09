package com.ahamed.sample.complex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.BaseModel;
import com.ahamed.sample.common.model.GridItem;
import com.ahamed.sample.common.model.ItemOne;
import com.ahamed.sample.common.model.ItemThree;
import com.ahamed.sample.common.model.ItemTwo;
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

    ComplexListAdapter adapter = new ComplexListAdapter();
    adapter.setSpanCount(3);

    glm.setSpanSizeLookup(adapter.getSpanSizeLookup());
    recyclerView.setLayoutManager(glm);
    recyclerView.setAdapter(adapter);

    List<ItemThree> dataListThree = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      dataListThree.add(new ItemThree(i, "Item model three " + i));
    }
    adapter.addSingleModelItem(dataListThree);

    List<GridItem> gridDataList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      gridDataList.add(new GridItem(i, "Grid item " + i));
    }
    adapter.addGridItem(gridDataList);

    List<BaseModel> multiItemList = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < 15; i++) {
      if (random.nextBoolean()) {
        multiItemList.add(new ItemOne(i, "Mixed item one " + i));
      } else {
        multiItemList.add(new ItemTwo(i, "Mixed item two " + i));
      }
    }
    adapter.addMultiItem(multiItemList);

    return view;
  }
}
