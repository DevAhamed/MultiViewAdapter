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

package dev.ahamed.mva.sample.view.selection;

import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import androidx.recyclerview.widget.GridLayoutManager;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Header;
import dev.ahamed.mva.sample.data.model.SelectableItem;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import dev.ahamed.mva.sample.view.common.HeaderItemBinder;
import mva3.adapter.HeaderSection;
import mva3.adapter.ItemSection;
import mva3.adapter.ListSection;
import mva3.adapter.NestedSection;
import mva3.adapter.util.Mode;
import mva3.adapter.util.OnSelectionChangedListener;

import static mva3.adapter.util.Mode.SINGLE;

public class SelectionSampleFragment extends BaseFragment {

  private ListSection<SelectableItem> listSectionOne;
  private ListSection<SelectableItem> listSectionTwo;
  private NestedSection nestedSection;
  private ListSection<SelectableItem> listSectionThree;
  private ListSection<SelectableItem> listSectionFour;
  private ListSection<SelectableItem> listSectionFive;

  private Spinner spinnerAdapterSelectionMode;
  private Spinner spinnerLanguageSelectionMode;
  private Spinner spinnerTopicSelectionMode;
  private Spinner spinnerSourcesSelectionMode;
  private Spinner spinnerNewsPapersSelectionMode;
  private Spinner spinnerNewsChannelsSelectionMode;
  private Spinner spinnerWebsitesSelectionMode;

  public SelectionSampleFragment() {
  }

  @Override public void initViews(View view) {
    spinnerAdapterSelectionMode = view.findViewById(R.id.spinner_adapter_mode);
    spinnerLanguageSelectionMode = view.findViewById(R.id.spinner_language_mode);
    spinnerTopicSelectionMode = view.findViewById(R.id.spinner_topics_mode);
    spinnerSourcesSelectionMode = view.findViewById(R.id.spinner_sources_mode);
    spinnerNewsPapersSelectionMode = view.findViewById(R.id.spinner_np_mode);
    spinnerNewsChannelsSelectionMode = view.findViewById(R.id.spinner_channel_mode);
    spinnerWebsitesSelectionMode = view.findViewById(R.id.spinner_web_mode);

    setUpAdapter();
  }

  @Override public int layoutId() {
    return R.layout.fragment_selection;
  }

  @Override public void resetConfiguration() {
    spinnerAdapterSelectionMode.setSelection(1);
    spinnerLanguageSelectionMode.setSelection(1);
    spinnerTopicSelectionMode.setSelection(1);
    spinnerSourcesSelectionMode.setSelection(1);
    spinnerNewsPapersSelectionMode.setSelection(1);
    spinnerNewsChannelsSelectionMode.setSelection(1);
    spinnerWebsitesSelectionMode.setSelection(1);

    updateConfiguration();
  }

  @Override public void updateConfiguration() {
    Mode adapterSelectionMode = getMode(spinnerAdapterSelectionMode.getSelectedItemPosition());
    Mode languageSelectionMode = getMode(spinnerLanguageSelectionMode.getSelectedItemPosition());
    Mode topicSelectionMode = getMode(spinnerTopicSelectionMode.getSelectedItemPosition());
    Mode sourcesSelectionMode = getMode(spinnerSourcesSelectionMode.getSelectedItemPosition());
    Mode newsPapersSelectionMode =
        getMode(spinnerNewsPapersSelectionMode.getSelectedItemPosition());
    Mode newsChannelsSelectionMode =
        getMode(spinnerNewsChannelsSelectionMode.getSelectedItemPosition());
    Mode websitesSelectionMode = getMode(spinnerWebsitesSelectionMode.getSelectedItemPosition());

    adapter.setSelectionMode(adapterSelectionMode);
    listSectionOne.setSelectionMode(languageSelectionMode);
    listSectionTwo.setSelectionMode(topicSelectionMode);
    nestedSection.setSelectionMode(sourcesSelectionMode);
    listSectionThree.setSelectionMode(newsPapersSelectionMode);
    listSectionFour.setSelectionMode(newsChannelsSelectionMode);
    listSectionFive.setSelectionMode(websitesSelectionMode);

    adapter.clearAllSelections();
  }

  private Mode getMode(int position) {
    if (position == 1) {
      return SINGLE;
    } else if (position == 2) {
      return Mode.MULTIPLE;
    } else if (position == 3) {
      return Mode.INHERIT;
    } else {
      return Mode.NONE;
    }
  }

  private void setUpAdapter() {
    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
    adapter.setSpanCount(3);
    layoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(adapter.getItemDecoration());

    HeaderSection headerSectionOne = new HeaderSection<>(new Header("Language"));
    HeaderSection headerSectionTwo = new HeaderSection<>(new Header("Topic"));
    HeaderSection headerSectionThree = new HeaderSection<>(new Header("News Papers"));
    HeaderSection headerSectionFour = new HeaderSection<>(new Header("News Channels"));
    HeaderSection headerSectionFive = new HeaderSection<>(new Header("Websites"));

    listSectionOne = new ListSection<>();
    listSectionTwo = new ListSection<>();
    listSectionThree = new ListSection<>();
    listSectionFour = new ListSection<>();
    listSectionFive = new ListSection<>();

    headerSectionOne.addSection(listSectionOne);
    headerSectionTwo.addSection(listSectionTwo);
    headerSectionThree.addSection(listSectionThree);
    headerSectionFour.addSection(listSectionFour);
    headerSectionFive.addSection(listSectionFive);

    listSectionOne.set(dataManager.getLanguages());
    listSectionTwo.set(dataManager.getTopics());
    listSectionThree.set(dataManager.getNewsPapers());
    listSectionFour.set(dataManager.getTelevisionChannels());
    listSectionFive.set(dataManager.getWebsites());

    ItemSection<Header> headerSection = new ItemSection<>(new Header("Sources"));
    nestedSection = new NestedSection();
    nestedSection.addSection(headerSectionThree);
    nestedSection.addSection(headerSectionFour);
    nestedSection.addSection(headerSectionFive);

    adapter.registerItemBinders(new SelectableItemBinder(), new HeaderItemBinder());
    adapter.addSection(headerSectionOne);
    adapter.addSection(headerSectionTwo);
    adapter.addSection(headerSection);
    adapter.addSection(nestedSection);

    OnSelectionChangedListener<SelectableItem> selectionChangedListener =
        (item, isSelected, selectedItems) -> Log.d("SelectionChanged",
            "Item " + item.toString() + " was " + (isSelected ? "" : "un") + "selected");

    listSectionOne.setOnSelectionChangedListener(selectionChangedListener);
    listSectionTwo.setOnSelectionChangedListener(selectionChangedListener);
    listSectionThree.setOnSelectionChangedListener(selectionChangedListener);
    listSectionFour.setOnSelectionChangedListener(selectionChangedListener);
    listSectionFive.setOnSelectionChangedListener(selectionChangedListener);
  }
}
