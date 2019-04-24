/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.ahamed.mva.sample.view;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.view.advanced.AdvancedFragment;
import dev.ahamed.mva.sample.view.basic.BasicSampleFragment;
import dev.ahamed.mva.sample.view.decoration.DecorationSampleFragment;
import dev.ahamed.mva.sample.view.expansion.ExpansionSampleFragment;
import dev.ahamed.mva.sample.view.home.HomeFragment;
import dev.ahamed.mva.sample.view.newsfeed.NewsFeedFragment;
import dev.ahamed.mva.sample.view.selection.SelectionSampleFragment;

public class SampleActivity extends AppCompatActivity {

  private static final String STATE_SELECTED_POSITION = "state_selected_position";

  public static float DP = 0;
  public static int DP_FOUR = 0;
  public static int DP_EIGHT = 0;

  private Spinner spinner;
  private int selectedPosition;

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    if (spinner.getSelectedItemPosition() == 6) {
      getMenuInflater().inflate(R.menu.menu_home, menu);
    } else {
      getMenuInflater().inflate(R.menu.menu_sample_fragment, menu);
    }
    if (spinner.getSelectedItemPosition() == 5) {
      menu.removeItem(R.id.menu_configure);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_github) {
      Intent browserIntent = new Intent(Intent.ACTION_VIEW,
          Uri.parse("https://github.com/DevAhamed/MultiViewAdapter"));
      startActivity(browserIntent);
    } else if (item.getItemId() == R.id.menu_rate_us) {
      Intent browserIntent = new Intent(Intent.ACTION_VIEW,
          Uri.parse("https://play.google.com/store/apps/details?id=dev.ahamed.mva.sample"));
      startActivity(browserIntent);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
    spinner = findViewById(R.id.sample_selector);

    if (null == savedInstanceState) {
      spinner.setSelection(6);
    } else {
      selectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
    }

    setUpDeviceMetrics();
    setUpToolbar();
    setUpSpinner();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  private void setUpDeviceMetrics() {
    Resources resources = getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    DP = metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
    DP_FOUR = (int) (DP * 4);
    DP_EIGHT = (int) (DP * 8);
  }

  private void replaceFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.content_layout, fragment, "current_fragment")
        .commitAllowingStateLoss();
  }

  private void setUpSpinner() {
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        invalidateOptionsMenu();
        switchFragment(position);
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {
        // No-op
      }
    });
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(STATE_SELECTED_POSITION, selectedPosition);
  }

  private void setUpToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (null != getSupportActionBar()) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
  }

  private void switchFragment(int position) {
    if (selectedPosition == position) {
      return;
    }
    selectedPosition = position;
    Fragment fragment;
    switch (position) {
      case 0:
        fragment = new BasicSampleFragment();
        break;
      case 1:
        fragment = new AdvancedFragment();
        break;
      case 2:
        fragment = new SelectionSampleFragment();
        break;
      case 3:
        fragment = new ExpansionSampleFragment();
        break;
      case 4:
        fragment = new DecorationSampleFragment();
        break;
      case 5:
        fragment = new NewsFeedFragment();
        break;
      case 6:
      default:
        fragment = HomeFragment.newInstance();
        break;
    }
    replaceFragment(fragment);
  }
}
