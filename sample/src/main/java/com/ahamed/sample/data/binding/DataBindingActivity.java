package com.ahamed.sample.data.binding;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.adapter.QuoteAdapter;
import com.ahamed.sample.common.model.Quote;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataBindingActivity extends BaseActivity {

  private static final String TAG = "DataBindingActivity";

  public static void start(Context context) {
    Intent starter = new Intent(context, DataBindingActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

    QuoteAdapter adapter = new QuoteAdapter(true);

    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);

    List<Quote> quotes = getQuotes();
    adapter.addData(quotes);
  }

  private String loadJSONFromAsset() {
    String json;
    try {
      InputStream is = getAssets().open("quotes.json");
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
