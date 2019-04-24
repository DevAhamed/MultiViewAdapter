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

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.CheckedTextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Header;
import dev.ahamed.mva.sample.data.model.MoviePosterItem;
import dev.ahamed.mva.sample.view.SampleActivity;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import dev.ahamed.mva.sample.view.common.HeaderItemBinder;
import mva2.adapter.HeaderSection;
import mva2.extension.decorator.InsetDecoration;

public class DecorationSampleFragment extends BaseFragment {

  private final MoviePosterItemBinder moviePosterBinder = new MoviePosterItemBinder();
  private final HeaderItemBinder decoratedHeaderBinder = new HeaderItemBinder();
  private HeaderSection<Header, MoviePosterItem> trendingMovies;
  private HeaderSection<Header, MoviePosterItem> latestMovies;
  private HeaderSection<Header, MoviePosterItem> upcomingMovies;
  private CheckedTextView cbEnableTrendingSectionDecoration;
  private CheckedTextView cbEnableLatestSectionDecoration;
  private CheckedTextView cbEnableUpcomingSectionDecoration;
  private CheckedTextView cbEnableItemDecoration;
  private CheckedTextView cbEnableHeaderDecoration;
  private CheckedTextView cbEnableBorder;
  private CheckedTextView cbEnableInset;

  public DecorationSampleFragment() {
  }

  @Override public void initViews(View view) {
    cbEnableTrendingSectionDecoration = view.findViewById(R.id.cb_trending_decoration);
    cbEnableLatestSectionDecoration = view.findViewById(R.id.cb_latest_decoration);
    cbEnableUpcomingSectionDecoration = view.findViewById(R.id.cb_upcoming_decoration);

    cbEnableItemDecoration = view.findViewById(R.id.cb_movie_decoration);
    cbEnableHeaderDecoration = view.findViewById(R.id.cb_header_decoration);

    cbEnableBorder = view.findViewById(R.id.cb_enable_borders);
    cbEnableInset = view.findViewById(R.id.cb_enable_inset);

    View.OnClickListener onClickListener = v -> {
      CheckedTextView checkedTextView = (CheckedTextView) v;
      checkedTextView.setChecked(!checkedTextView.isChecked());
    };

    cbEnableTrendingSectionDecoration.setOnClickListener(onClickListener);
    cbEnableLatestSectionDecoration.setOnClickListener(onClickListener);
    cbEnableUpcomingSectionDecoration.setOnClickListener(onClickListener);
    cbEnableItemDecoration.setOnClickListener(onClickListener);
    cbEnableHeaderDecoration.setOnClickListener(onClickListener);
    cbEnableBorder.setOnClickListener(onClickListener);
    cbEnableInset.setOnClickListener(onClickListener);

    setUpAdapter();
  }

  @Override public int layoutId() {
    return R.layout.fragment_decoration;
  }

  @Override public void resetConfiguration() {
    cbEnableTrendingSectionDecoration.setChecked(true);
    cbEnableLatestSectionDecoration.setChecked(true);
    cbEnableUpcomingSectionDecoration.setChecked(true);
    cbEnableItemDecoration.setChecked(true);
    cbEnableHeaderDecoration.setChecked(true);
    cbEnableBorder.setChecked(true);
    cbEnableInset.setChecked(true);

    updateConfiguration();
  }

  @Override public void updateConfiguration() {
    recyclerView.invalidateItemDecorations();

    trendingMovies.removeAllDecorators();
    latestMovies.removeAllDecorators();
    upcomingMovies.removeAllDecorators();
    moviePosterBinder.removeAllDecorators();
    decoratedHeaderBinder.removeAllDecorators();

    boolean enableTrendingMoviesDecoration = cbEnableTrendingSectionDecoration.isChecked();
    boolean enableLatestMoviesDecoration = cbEnableLatestSectionDecoration.isChecked();
    boolean enableUpcomingMoviesDecoration = cbEnableUpcomingSectionDecoration.isChecked();
    boolean enableItemDecoration = cbEnableItemDecoration.isChecked();
    boolean enableHeaderDecoration = cbEnableHeaderDecoration.isChecked();
    boolean showBorders = cbEnableBorder.isChecked();
    boolean addInset = cbEnableInset.isChecked();

    if (enableTrendingMoviesDecoration) {
      trendingMovies.addDecorator(new MovieSectionDecoration(adapter, addInset, showBorders));
    }
    if (enableLatestMoviesDecoration) {
      latestMovies.addDecorator(new MovieSectionDecoration(adapter, addInset, showBorders));
    }
    if (enableUpcomingMoviesDecoration) {
      upcomingMovies.addDecorator(new MovieSectionDecoration(adapter, addInset, showBorders));
    }

    if (enableItemDecoration) {
      moviePosterBinder.addDecorator(new InsetDecoration(adapter, SampleActivity.DP_EIGHT));
    }

    if (enableHeaderDecoration) {
      decoratedHeaderBinder.addDecorator(new InsetDecoration(adapter, SampleActivity.DP_EIGHT));
    }
  }

  private void setUpAdapter() {
    moviePosterBinder.addDecorator(new InsetDecoration(adapter, SampleActivity.DP_EIGHT));

    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
    adapter.setSpanCount(2);
    layoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(adapter.getItemDecoration());

    trendingMovies = new HeaderSection<>(new Header("Trending Movies"));
    latestMovies = new HeaderSection<>(new Header("Latest Movies"));
    upcomingMovies = new HeaderSection<>(new Header("Upcoming Movies"));

    adapter.registerItemBinders(moviePosterBinder, decoratedHeaderBinder);
    adapter.addSection(trendingMovies);
    adapter.addSection(latestMovies);
    adapter.addSection(upcomingMovies);

    trendingMovies.getListSection().set(dataManager.getMovies());
    latestMovies.getListSection().set(dataManager.getMovies());
    upcomingMovies.getListSection().set(dataManager.getMovies());
  }
}
