package com.ahamed.sample.grid;

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
import com.ahamed.sample.common.model.Header;
import java.util.ArrayList;
import java.util.List;

public class GridAdapterFragment extends Fragment {

  public GridAdapterFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_base, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_list);
    GridLayoutManager glm = new GridLayoutManager(getContext().getApplicationContext(), 3);

    GridAdapter adapter = new GridAdapter();
    adapter.setSpanCount(3);

    glm.setSpanSizeLookup(adapter.getSpanSizeLookup());
    recyclerView.setLayoutManager(glm);
    recyclerView.setAdapter(adapter);

    List<BaseModel> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      if (i % 10 == 0) {
        data.add(new Header("Sample header " + i));
      } else {
        data.add(new GridItem(i, "Item " + i));
      }
    }
    adapter.addData(data);
    return view;
  }
}
