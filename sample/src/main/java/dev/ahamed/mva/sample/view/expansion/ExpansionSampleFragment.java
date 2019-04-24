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

package dev.ahamed.mva.sample.view.expansion;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Spinner;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.FaqItem;
import dev.ahamed.mva.sample.data.model.Header;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import dev.ahamed.mva.sample.view.common.CustomItemAnimator;
import mva2.adapter.HeaderSection;
import mva2.adapter.util.Mode;

public class ExpansionSampleFragment extends BaseFragment {

  private HeaderSection<Header, FaqItem> expandableHeaderSectionOne;
  private HeaderSection<Header, FaqItem> expandableHeaderSectionTwo;
  private HeaderSection<Header, FaqItem> expandableHeaderSectionThree;

  private Spinner spinnerAdapterItemMode;
  private Spinner spinnerAdapterSectionMode;
  private Spinner spinnerSectionOneMode;
  private Spinner spinnerSectionTwoMode;
  private Spinner spinnerSectionThreeMode;

  public ExpansionSampleFragment() {
  }

  @Override public void initViews(View view) {
    spinnerAdapterSectionMode = view.findViewById(R.id.spinner_adapter_section_mode);
    spinnerAdapterItemMode = view.findViewById(R.id.spinner_adapter_mode);
    spinnerSectionOneMode = view.findViewById(R.id.spinner_section_one_mode);
    spinnerSectionTwoMode = view.findViewById(R.id.spinner_section_two_mode);
    spinnerSectionThreeMode = view.findViewById(R.id.spinner_section_three_mode);

    setUpAdapter();
  }

  @Override public int layoutId() {
    return R.layout.fragment_expansion;
  }

  @Override public void resetConfiguration() {
    spinnerAdapterSectionMode.setSelection(2);
    spinnerAdapterItemMode.setSelection(2);
    spinnerSectionOneMode.setSelection(3);
    spinnerSectionTwoMode.setSelection(3);
    spinnerSectionThreeMode.setSelection(3);

    updateConfiguration();
  }

  @Override public void updateConfiguration() {
    Mode adapterItemExpansionMode = getMode(spinnerAdapterItemMode.getSelectedItemPosition());
    Mode adapterSectionExpansionMode = getMode(spinnerAdapterSectionMode.getSelectedItemPosition());
    Mode section1ExpansionMode = getMode(spinnerSectionOneMode.getSelectedItemPosition());
    Mode section2ExpansionMode = getMode(spinnerSectionTwoMode.getSelectedItemPosition());
    Mode section3ExpansionMode = getMode(spinnerSectionThreeMode.getSelectedItemPosition());

    adapter.collapseAllSections();
    adapter.collapseAllItems();

    adapter.setSectionExpansionMode(adapterSectionExpansionMode);

    adapter.setExpansionMode(adapterItemExpansionMode);
    expandableHeaderSectionOne.setExpansionMode(section1ExpansionMode);
    expandableHeaderSectionTwo.setExpansionMode(section2ExpansionMode);
    expandableHeaderSectionThree.setExpansionMode(section3ExpansionMode);
  }

  private Mode getMode(int position) {
    if (position == 1) {
      return Mode.SINGLE;
    } else if (position == 2) {
      return Mode.MULTIPLE;
    } else if (position == 3) {
      return Mode.INHERIT;
    } else {
      return Mode.NONE;
    }
  }

  private void setUpAdapter() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(adapter.getItemDecoration());
    recyclerView.setItemAnimator(new CustomItemAnimator());

    expandableHeaderSectionOne = new HeaderSection<>(new Header("Account"));
    expandableHeaderSectionTwo = new HeaderSection<>(new Header("Payment"));
    expandableHeaderSectionThree = new HeaderSection<>(new Header("Life"));

    expandableHeaderSectionOne.getListSection().addAll(dataManager.getAccountFaq());
    expandableHeaderSectionTwo.getListSection().addAll(dataManager.getPaymentFaq());
    expandableHeaderSectionThree.getListSection().addAll(dataManager.getMiscFaq());

    expandableHeaderSectionOne.addDecorator(new SectionSpaceDecorator(adapter));
    expandableHeaderSectionTwo.addDecorator(new SectionSpaceDecorator(adapter));
    expandableHeaderSectionThree.addDecorator(new SectionSpaceDecorator(adapter));

    adapter.registerItemBinders(new ExpandableHeaderItemBinder(), new ExpandableItemBinder());
    adapter.addSection(expandableHeaderSectionOne);
    adapter.addSection(expandableHeaderSectionTwo);
    adapter.addSection(expandableHeaderSectionThree);
  }
}
