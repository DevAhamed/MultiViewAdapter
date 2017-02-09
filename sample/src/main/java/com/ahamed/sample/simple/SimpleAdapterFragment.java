package com.ahamed.sample.simple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.sample.R;
import java.util.ArrayList;
import java.util.List;

public class SimpleAdapterFragment extends Fragment {

  public SimpleAdapterFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_base, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_list);
    LinearLayoutManager llm = new LinearLayoutManager(getContext().getApplicationContext());
    recyclerView.addItemDecoration(new DividerItemDecoration(getContext().getApplicationContext(),
        DividerItemDecoration.VERTICAL));

    SimpleAdapter adapter = new SimpleAdapter();

    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<ItemModel> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      data.add(new ItemModel(i, "Item " + i));
    }
    adapter.addData(data);
    return view;
  }
}
