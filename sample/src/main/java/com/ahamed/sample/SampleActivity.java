package com.ahamed.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.ahamed.sample.complex.ComplexListFragment;
import com.ahamed.sample.grid.GridAdapterFragment;
import com.ahamed.sample.multilist.MultiListFragment;
import com.ahamed.sample.simple.SimpleAdapterFragment;

public class SampleActivity extends AppCompatActivity {

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

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Spinner spinner = (Spinner) findViewById(R.id.spinner_sample);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showFragment(position);
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }

  private void showFragment(int position) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, getFragment(position))
        .commit();
  }

  private Fragment getFragment(int position) {
    switch (position) {
      case 0:
        return new SimpleAdapterFragment();
      case 1:
        return new MultiListFragment();
      case 2:
        return new GridAdapterFragment();
      case 3:
        return new ComplexListFragment();
      default:
        return new SimpleAdapterFragment();
    }
  }
}
