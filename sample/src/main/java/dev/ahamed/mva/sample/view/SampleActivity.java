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
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
import dev.ahamed.mva.sample.view.nested.NestedSectionFragment;
import dev.ahamed.mva.sample.view.newsfeed.NewsFeedFragment;
import dev.ahamed.mva.sample.view.selection.SelectionSampleFragment;

import static android.content.res.Configuration.UI_MODE_NIGHT_YES;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

public class SampleActivity extends AppCompatActivity {

  public static final String PREFS_HINT_ENABLED = "prefs_hint_enabled";
  private static final String STATE_SELECTED_POSITION = "state_selected_position";
  private static final String PREFS_DARK_THEME = "prefs_dark_theme";
  public static int DP = 0;
  public static int TWO_DP = 0;
  public static int FOUR_DP = 0;
  public static int EIGHT_DP = 0;
  public static int SIXTEEN_DP = 0;

  private Spinner spinner;
  private int selectedPosition;

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    if (spinner.getSelectedItemPosition() == 7) {
      getMenuInflater().inflate(R.menu.menu_home, menu);
    } else {
      getMenuInflater().inflate(R.menu.menu_sample_fragment, menu);
    }
    if (spinner.getSelectedItemPosition() == 6) {
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
    } else if (item.getItemId() == R.id.menu_switch_theme) {
      boolean isDarkThemeEnabled =
          PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
              .getBoolean(PREFS_DARK_THEME, false);
      PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
          .edit()
          .putBoolean(PREFS_DARK_THEME, !isDarkThemeEnabled)
          .apply();
      AppCompatDelegate.setDefaultNightMode(
          !isDarkThemeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
      getDelegate().applyDayNight();
    } else if (item.getItemId() == R.id.menu_toggle_hint) {
      boolean isHintEnabled = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
          .getBoolean(PREFS_HINT_ENABLED, true);
      PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
          .edit()
          .putBoolean(PREFS_HINT_ENABLED, !isHintEnabled)
          .apply();
      getDelegate().applyDayNight();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    setSystemBarTheme();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    boolean isDarkThemeEnabled =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
            .getBoolean(PREFS_DARK_THEME, false);
    AppCompatDelegate.setDefaultNightMode(
        isDarkThemeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    setSystemBarTheme();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
    spinner = findViewById(R.id.sample_selector);

    if (null == savedInstanceState) {
      spinner.setSelection(7);
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

  @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(STATE_SELECTED_POSITION, selectedPosition);
  }

  private void setSystemBarTheme() {
    int visibility = SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    int currentNightMode =
        getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
      if (currentNightMode != UI_MODE_NIGHT_YES) {
        visibility |= SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (currentNightMode != UI_MODE_NIGHT_YES) {
        visibility |= SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      }
    }
    getWindow().getDecorView().setSystemUiVisibility(visibility);
  }

  private void setUpDeviceMetrics() {
    Resources resources = getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float dp = metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
    DP = (int) dp;
    TWO_DP = (int) (dp * 2);
    FOUR_DP = (int) (dp * 4);
    EIGHT_DP = (int) (dp * 8);
    SIXTEEN_DP = (int) (dp * 16);
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
        fragment = new NestedSectionFragment();
        break;
      case 6:
        fragment = new NewsFeedFragment();
        break;
      case 7:
      default:
        fragment = new HomeFragment();
        break;
    }
    replaceFragment(fragment);
  }
}
