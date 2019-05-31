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

package dev.ahamed.mva.sample.view.common;

import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.DataManager;
import dev.ahamed.mva.sample.data.model.Hint;
import dev.ahamed.mva.sample.view.SampleActivity;
import mva3.adapter.ItemSection;
import mva3.adapter.MultiViewAdapter;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

public abstract class BaseFragment extends Fragment {

  private static int actionBarSize = -1;
  protected final DataManager dataManager = new DataManager();
  protected RecyclerView recyclerView;
  protected MultiViewAdapter adapter;
  private BottomSheetBehavior configurationSheet;
  private Rect rect;

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_configure) {
      toggle(configurationSheet);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  protected @StringRes int getHint() {
    switch (getClass().getSimpleName()) {
      case "BasicSampleFragment":
        return R.string.basic_hint;
      case "AdvancedFragment":
        return R.string.advanced_hint;
      case "SelectionSampleFragment":
        return R.string.selection_hint;
      case "ExpansionSampleFragment":
        return R.string.expansion_hint;
      case "DecorationSampleFragment":
        return R.string.decoration_hint;
      case "NestedSectionFragment":
        return R.string.tree_section_hint;
      case "NewsFeedFragment":
        return R.string.news_feed_hint;
      default:
        return R.string.hint;
    }
  }

  public abstract void initViews(View view);

  public abstract int layoutId();

  public abstract void resetConfiguration();

  public abstract void updateConfiguration();

  @Override public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(layoutId(), container, false);
  }

  @Override
  public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    configurationSheet = BottomSheetBehavior.from(view.findViewById(R.id.sheet_configuration));
    configurationSheet.setPeekHeight(0);
    configurationSheet.setHideable(false);
    configurationSheet.setState(STATE_COLLAPSED);

    recyclerView = view.findViewById(R.id.recycler_view);
    recyclerView.setOnApplyWindowInsetsListener((v, insets) -> {
      if (rect == null) {
        rect = new Rect();
        rect.set(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(),
            recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
      }
      v.setPadding(rect.left, rect.top + insets.getSystemWindowInsetTop() + getActionBarSize(),
          rect.right, rect.bottom + insets.getSystemWindowInsetBottom() + getActionBarSize());
      return insets;
    });
    view.findViewById(R.id.sheet_configuration).setOnApplyWindowInsetsListener((v, insets) -> {
      v.setPadding(0, insets.getSystemWindowInsetTop() + getActionBarSize(), 0,
          insets.getSystemWindowInsetBottom() + getActionBarSize());
      return insets;
    });
    adapter = new MultiViewAdapter();

    if (PreferenceManager.getDefaultSharedPreferences(requireContext().getApplicationContext())
        .getBoolean(SampleActivity.PREFS_HINT_ENABLED, true)) {
      adapter.registerItemBinders(new HintBinder());
      adapter.addSection(new ItemSection<>(new Hint(getHint())));
      adapter.getItemTouchHelper().attachToRecyclerView(recyclerView);
    }

    view.findViewById(R.id.btn_reset).setOnClickListener(v -> {
      resetConfiguration();
      toggle(configurationSheet);
    });
    view.findViewById(R.id.btn_update).setOnClickListener(v -> {
      updateConfiguration();
      toggle(configurationSheet);
    });

    initViews(view);
  }

  @Override public final void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    if (savedInstanceState == null) {
      resetConfiguration();
    } else {
      updateConfiguration();
    }
  }

  private void toggle(BottomSheetBehavior sheetBehavior) {
    sheetBehavior.setState(
        sheetBehavior.getState() != STATE_EXPANDED ? STATE_EXPANDED : STATE_COLLAPSED);
  }

  private int getActionBarSize() {
    if (actionBarSize < 0) {
      TypedValue typedValue = new TypedValue();
      requireContext().getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
      actionBarSize = (int) getResources().getDimension(typedValue.resourceId);
    }
    return actionBarSize;
  }
}
