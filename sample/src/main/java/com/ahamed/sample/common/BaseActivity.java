package com.ahamed.sample.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.ahamed.sample.R;

public abstract class BaseActivity extends AppCompatActivity {

  protected RecyclerView recyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    // uncomment while testing performance
    //TinyDancer.create()
    //    .redFlagPercentage(.1f)
    //    .startingGravity(Gravity.TOP)
    //    .startingXPosition(200)
    //    .startingYPosition(600)
    //    .show(this);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);

    recyclerView = (RecyclerView) findViewById(R.id.rcv_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (null != getSupportActionBar()) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    setUpAdapter();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  protected abstract void setUpAdapter();
}
