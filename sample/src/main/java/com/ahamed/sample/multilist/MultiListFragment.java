package com.ahamed.sample.multilist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.ItemOne;
import com.ahamed.sample.common.model.ItemThree;
import com.ahamed.sample.common.model.ItemTwo;
import java.util.ArrayList;
import java.util.List;

public class MultiListFragment extends Fragment {

  public MultiListFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_base, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_list);
    LinearLayoutManager llm = new LinearLayoutManager(getContext().getApplicationContext());

    MultiListAdapter adapter = new MultiListAdapter(getActivity());
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());

    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<ItemOne> dataListOne = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      dataListOne.add(new ItemOne(i, "Item model one " + i));
    }
    adapter.addDataOne(dataListOne);

    List<ItemTwo> dataListTwo = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      dataListTwo.add(new ItemTwo(i, "Item model two " + i));
    }
    adapter.addDataTwo(dataListTwo);

    List<ItemThree> dataListThree = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      dataListThree.add(new ItemThree(i, "Item model three " + i));
    }
    adapter.addDataThree(dataListThree);

    return view;
  }
}
