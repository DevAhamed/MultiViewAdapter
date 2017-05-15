package com.ahamed.sample.multilist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Bird;
import com.ahamed.sample.common.model.Flower;
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

    return view;
  }
}