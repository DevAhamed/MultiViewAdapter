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

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Header;
import dev.ahamed.mva.sample.data.model.SelectableItem;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import dev.ahamed.mva.sample.view.common.HeaderItemBinder;
import mva2.adapter.HeaderSection;
import mva2.adapter.ItemSection;
import mva2.adapter.NestedSection;
import mva2.adapter.util.Mode;
import mva2.adapter.util.OnSelectionChangedListener;

import static mva2.adapter.util.Mode.SINGLE;

public class SelectionSampleFragment extends BaseFragment {

  private HeaderSection<Header, SelectableItem> listSectionOne;
  private HeaderSection<Header, SelectableItem> listSectionTwo;
  private ItemSection<Header> headerSection;
  private NestedSection nestedSection;
  private HeaderSection<Header, SelectableItem> listSectionThree;
  private HeaderSection<Header, SelectableItem> listSectionFour;
  private HeaderSection<Header, SelectableItem> listSectionFive;

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

    listSectionOne = new HeaderSection<>(new Header("Language"));
    listSectionTwo = new HeaderSection<>(new Header("Topic"));
    listSectionThree = new HeaderSection<>(new Header("News Papers"));
    listSectionFour = new HeaderSection<>(new Header("News Channels"));
    listSectionFive = new HeaderSection<>(new Header("Websites"));

    listSectionOne.getListSection().set(dataManager.getLanguages());
    listSectionTwo.getListSection().set(dataManager.getTopics());
    listSectionThree.getListSection().set(dataManager.getNewsPapers());
    listSectionFour.getListSection().set(dataManager.getTelevisionChannels());
    listSectionFive.getListSection().set(dataManager.getWebsites());

    headerSection = new ItemSection<>(new Header("Sources"));
    nestedSection = new NestedSection();
    nestedSection.addSection(listSectionThree);
    nestedSection.addSection(listSectionFour);
    nestedSection.addSection(listSectionFive);

    adapter.registerItemBinders(new SelectableItemBinder(), new HeaderItemBinder());
    adapter.addSection(listSectionOne);
    adapter.addSection(listSectionTwo);
    adapter.addSection(headerSection);
    adapter.addSection(nestedSection);

    OnSelectionChangedListener<SelectableItem> selectionChangedListener =
        (item, isSelected, selectedItems) -> Log.d("SelectionChanged",
            "Item " + item.toString() + " was " + (isSelected ? "" : "un") + "selected");

    listSectionOne.getListSection().setOnSelectionChangedListener(selectionChangedListener);
    listSectionTwo.getListSection().setOnSelectionChangedListener(selectionChangedListener);
    listSectionThree.getListSection().setOnSelectionChangedListener(selectionChangedListener);
    listSectionFour.getListSection().setOnSelectionChangedListener(selectionChangedListener);
    listSectionFive.getListSection().setOnSelectionChangedListener(selectionChangedListener);
  }
}
