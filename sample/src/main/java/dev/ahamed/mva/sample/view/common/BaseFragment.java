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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.DataManager;
import mva2.adapter.MultiViewAdapter;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;

public abstract class BaseFragment extends Fragment {

  protected final DataManager dataManager = new DataManager();
  protected RecyclerView recyclerView;
  protected MultiViewAdapter adapter;
  private BottomSheetBehavior configurationSheet;

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_configure) {
      toggle(configurationSheet);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  protected void toggle(BottomSheetBehavior sheetBehavior) {
    sheetBehavior.setState(
        sheetBehavior.getState() != STATE_EXPANDED ? STATE_EXPANDED : STATE_COLLAPSED);
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
    adapter = new MultiViewAdapter();

    view.findViewById(R.id.btn_reset).setOnClickListener(v -> {
      resetConfiguration();
      toggle(configurationSheet);
    });
    view.findViewById(R.id.btn_update).setOnClickListener(v -> {
      updateConfiguration();
      toggle(configurationSheet);
    });

    initViews(view);
    if(savedInstanceState == null) {
      resetConfiguration();
    } else {
      updateConfiguration();
    }
  }
}
