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

package dev.ahamed.mva.sample.view.basic;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Spinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Hint;
import dev.ahamed.mva.sample.data.model.NumberItem;
import dev.ahamed.mva.sample.view.SampleActivity;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import dev.ahamed.mva.sample.view.common.HintBinder;
import java.util.List;
import mva3.adapter.ItemSection;
import mva3.adapter.ListSection;

public class BasicSampleFragment extends BaseFragment {

  private Spinner spinnerLayoutManager;
  private Spinner spinnerOrientation;
  private Spinner spinnerReverseLayout;
  private Spinner spinnerSpanCount;

  public BasicSampleFragment() {
  }

  @Override public void initViews(View view) {
    spinnerSpanCount = view.findViewById(R.id.spinner_span_count);
    spinnerLayoutManager = view.findViewById(R.id.spinner_layout_manager);
    spinnerOrientation = view.findViewById(R.id.spinner_orientation);
    spinnerReverseLayout = view.findViewById(R.id.spinner_reverse_layout);
  }

  @Override public int layoutId() {
    return R.layout.fragment_basic;
  }

  @Override public void resetConfiguration() {
    spinnerSpanCount.setSelection(0);
    spinnerLayoutManager.setSelection(0);
    spinnerOrientation.setSelection(0);
    spinnerReverseLayout.setSelection(0);
    updateConfiguration();
  }

  @Override public void updateConfiguration() {
    setUpAdapter();
  }

  private void setUpAdapter() {
    int spanCount = (spinnerSpanCount.getSelectedItemPosition() + 2);
    recyclerView.setAdapter(adapter);
    LinearLayoutManager layoutManager;
    if (spinnerLayoutManager.getSelectedItemPosition() == 0) {
      layoutManager = new CustomLayoutManager(getContext());
    } else {
      layoutManager = new GridLayoutManager(getContext(), spanCount);
      adapter.setSpanCount(spanCount);
      ((GridLayoutManager) layoutManager).setSpanSizeLookup(adapter.getSpanSizeLookup());
    }
    layoutManager.setOrientation(
        spinnerOrientation.getSelectedItemPosition() == 0 ? RecyclerView.VERTICAL
            : RecyclerView.HORIZONTAL);
    layoutManager.setReverseLayout(spinnerReverseLayout.getSelectedItemPosition() == 1);

    recyclerView.setLayoutManager(layoutManager);

    if (recyclerView.getItemDecorationCount() != 0) {
      recyclerView.removeItemDecorationAt(0);
    }
    recyclerView.addItemDecoration(adapter.getItemDecoration());

    adapter.removeAllSections();

    if (PreferenceManager.getDefaultSharedPreferences(requireContext().getApplicationContext())
        .getBoolean(SampleActivity.PREFS_HINT_ENABLED, true)
        && spinnerOrientation.getSelectedItemPosition() == 0) {
      adapter.registerItemBinders(new HintBinder());
      adapter.addSection(new ItemSection<>(new Hint(getHint())));
      adapter.getItemTouchHelper().attachToRecyclerView(recyclerView);
    }

    ListSection<NumberItem> listSection = new ListSection<>();
    adapter.addSection(listSection);
    adapter.registerItemBinders(
        new NumberItemBinder(new BasicDividerDecorator(adapter, requireContext())));

    List<NumberItem> list = dataManager.getNumberItems(10000);
    listSection.addAll(list);
  }
}
