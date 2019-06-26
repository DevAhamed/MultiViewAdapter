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

package dev.ahamed.mva.sample.view.decoration;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckedTextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Header;
import dev.ahamed.mva.sample.data.model.Person;
import dev.ahamed.mva.sample.view.SampleActivity;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import mva2.adapter.HeaderSection;
import mva2.adapter.ListSection;

public class DecorationSampleFragment extends BaseFragment {

  private ListSection<Person> castSection;
  private ListSection<Person> crewSection;
  private ListSection<Person> producerSection;
  private CheckedTextView cbEnableSectionDecoration;
  private CheckedTextView cbEnableItemDecoration;
  private CheckedTextView cbEnableHeaderDecoration;
  private PersonBinder personBinder;
  private HeaderBinder headerBinder;

  public DecorationSampleFragment() {
  }

  @Override public void initViews(View view) {
    cbEnableSectionDecoration = view.findViewById(R.id.cb_section_decoration);
    cbEnableItemDecoration = view.findViewById(R.id.cb_item_decoration);
    cbEnableHeaderDecoration = view.findViewById(R.id.cb_header_decoration);

    View.OnClickListener onClickListener = v -> {
      CheckedTextView checkedTextView = (CheckedTextView) v;
      checkedTextView.setChecked(!checkedTextView.isChecked());
    };

    cbEnableSectionDecoration.setOnClickListener(onClickListener);
    cbEnableItemDecoration.setOnClickListener(onClickListener);
    cbEnableHeaderDecoration.setOnClickListener(onClickListener);

    setUpAdapter();
  }

  @Override public int layoutId() {
    return R.layout.fragment_decoration;
  }

  @Override public void resetConfiguration() {
    cbEnableSectionDecoration.setChecked(true);
    cbEnableItemDecoration.setChecked(true);
    cbEnableHeaderDecoration.setChecked(true);

    updateConfiguration();
  }

  @Override public void updateConfiguration() {
    recyclerView.invalidateItemDecorations();

    castSection.removeAllDecorators();
    crewSection.removeAllDecorators();
    producerSection.removeAllDecorators();

    if (cbEnableSectionDecoration.isChecked()) {
      castSection.addDecorator(
          new SectionDecorator(adapter, getContext(), SampleActivity.EIGHT_DP));
      crewSection.addDecorator(
          new SectionDecorator(adapter, getContext(), SampleActivity.EIGHT_DP));
      producerSection.addDecorator(
          new SectionDecorator(adapter, getContext(), SampleActivity.EIGHT_DP));
    }

    personBinder.removeAllDecorators();
    if (cbEnableItemDecoration.isChecked()) {
      personBinder.addDecorator(
          new DividerDecorator(adapter, getContext(), false, SampleActivity.TWO_DP * 36));
    }

    headerBinder.removeAllDecorators();
    if (cbEnableHeaderDecoration.isChecked()) {
      headerBinder.addDecorator(new DividerDecorator(adapter, getContext()));
    }
  }

  private void setUpAdapter() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(adapter.getItemDecoration());

    HeaderSection<Header> castHeaderSection = new HeaderSection<>(new Header("Cast"));
    HeaderSection<Header> crewHeaderSection = new HeaderSection<>(new Header("Crew"));
    HeaderSection<Header> producerHeaderSection = new HeaderSection<>(new Header("Producers"));

    castSection = new ListSection<>();
    crewSection = new ListSection<>();
    producerSection = new ListSection<>();

    castHeaderSection.addSection(castSection);
    crewHeaderSection.addSection(crewSection);
    producerHeaderSection.addSection(producerSection);

    headerBinder = new HeaderBinder();
    personBinder = new PersonBinder();

    adapter.registerItemBinders(personBinder, headerBinder);
    adapter.addSection(castSection);
    adapter.addSection(crewSection);
    adapter.addSection(producerSection);

    castSection.set(dataManager.getCast());
    crewSection.set(dataManager.getCrew());
    producerSection.set(dataManager.getProducers());
  }
}
