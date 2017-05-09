package com.ahamed.sample.simple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.sample.R;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleAdapterFragment extends Fragment {

  private static final String TAG = "SimpleAdapterFragment";

  public SimpleAdapterFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_base, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_list);
    LinearLayoutManager llm = new LinearLayoutManager(getContext().getApplicationContext());
    recyclerView.addItemDecoration(new DividerItemDecoration(getContext().getApplicationContext(),
        DividerItemDecoration.VERTICAL));

    QuoteAdapter adapter = new QuoteAdapter();

    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<Quote> quotes = getQuotes();
    adapter.addData(quotes);

    return view;
  }

  private String loadJSONFromAsset() {
    String json;
    try {
      InputStream is = getActivity().getAssets().open("quotes.json");
      int size = is.available();
      byte[] buffer = new byte[size];
      //noinspection ResultOfMethodCallIgnored
      is.read(buffer);
      is.close();
      json = new String(buffer, "UTF-8");
    } catch (IOException ex) {
      Log.e(TAG, "loadJSONFromAsset", ex);
      return null;
    }
    return json;
  }

  private List<Quote> getQuotes() {
    try {
      JSONArray array = new JSONArray(loadJSONFromAsset());
      List<Quote> quotes = new ArrayList<>();
      for (int i = 0; i < array.length(); i++) {
        JSONObject branchObject = array.getJSONObject(i);
        String quote = branchObject.getString("quote");
        String author = branchObject.getString("author");
        quotes.add(new Quote(author, quote));
      }
      return quotes;
    } catch (JSONException e) {
      Log.e(TAG, "getJSONFromAsset", e);
    }
    return null;
  }
}
