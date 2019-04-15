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

package dev.ahamed.mva.sample.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.view.SampleActivity;
import java.util.ArrayList;
import java.util.List;
import mva2.adapter.ItemSection;
import mva2.adapter.ListSection;
import mva2.adapter.MultiViewAdapter;

public class HomeFragment extends Fragment {

  public HomeFragment() {
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
    MultiViewAdapter adapter = new MultiViewAdapter();

    recyclerView.addItemDecoration(adapter.getItemDecoration());
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);

    adapter.registerItemBinders(
        new FeatureItemBinder(new FeatureDecoration(adapter, SampleActivity.DP_EIGHT)),
        new IntroItemBinder());

    ItemSection<String> itemSection = new ItemSection<>("Introduction");
    adapter.addSection(itemSection);

    ListSection<Feature> featureListSection = new ListSection<>();
    adapter.addSection(featureListSection);

    featureListSection.set(getFeatures());
  }

  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  @Override public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  private List<Feature> getFeatures() {
    List<Feature> features = new ArrayList<>();

    features.add(new Feature("Selection Mode", R.drawable.ic_check_box,
        "Library provides single or multi selection modes. When the selection modes are"
            + " combined with sections, you get lot of combinations.\nFor example, inside a single "
            + "adapter you can have one section with multi-selection and one section with "
            + "single-selection mode."));

    features.add(new Feature("Expansion Mode", R.drawable.ic_expand_less,
        "Library allows you to expand or collapse individual items. Also you can expand "
            + "or collapse entire sections. \nSimilar to selection mode, when the expansion modes are"
            + " combined with sections, you get lot of combinations."));

    features.add(new Feature("Decoration", R.drawable.ic_decoration,
        "Decoration is one of the powerful feature of the library. On top of the "
            + "framework's ItemDecoration api, the library provides you more customization support "
            + "for decorations.\nYou can add decoration to an individual item or entire section"));

    features.add(new Feature("Spans", R.drawable.ic_span_count,
        "Having recyclerview items with multiple span counts? Is managing the span count"
            + " based on the item position is a nightmare? Fret Not.\nThe library allows you to add"
            + " multiple span counts to the adapter / section / individual item"));

    features.add(new Feature("DiffUtil", R.drawable.ic_diff_util,
        "The library supports the diffutil out-of-the-box. The diffutil can calculate "
            + "all the changes and apply it to your adapter"));

    features.add(new Feature("Swipe to Dismiss", R.drawable.ic_swipe_to_dismiss,
        "Want to add swipe to dismiss feature? Its as easy as it gets. You can add swipe"
            + " feature, with listener as well."));

    features.add(new Feature("Drag and Drop", R.drawable.ic_drag,
        "Its easy to add drag and drop support into your adapter. If the view type "
            + "matches, you can move the item between different sections as well."));

    features.add(new Feature("Infinite Scrolling", R.drawable.ic_infinite_loading,
        "You don't need to create a separate adapter for infinite scrolling feature. "
            + "Just add the scroll-listener and an helper object."));

    features.add(new Feature("Data Binding", R.drawable.ic_data_binding,
        "Data binding is an extension feature that can be added to the adapter. A single"
            + " adapter can display both data-binded items and non data-binded items.\nThere is no "
            + "changes needed in adapter or section. Just extend your item binder from "
            + "DBItemBinder"));

    features.add(new Feature("Extensions", R.drawable.ic_extension,
        "Do you want a section which directly accepts observable? Do you want your "
            + "viewholder to directly support ButterKnife?\nNow write your own extension. The "
            + "library has very good api set to utilise and build your own extension.\nTake a look "
            + "at DataBinding extension for getting started."));

    return features;
  }
}
