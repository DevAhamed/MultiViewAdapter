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

package dev.ahamed.mva.sample.view.advanced;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.ColoredItem;
import dev.ahamed.mva.sample.data.model.Header;
import dev.ahamed.mva.sample.data.model.NumberItem;
import dev.ahamed.mva.sample.data.model.ShufflingHeader;
import dev.ahamed.mva.sample.data.model.TextItem;
import dev.ahamed.mva.sample.view.SampleActivity;
import dev.ahamed.mva.sample.view.basic.NumberItemBinder;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import dev.ahamed.mva.sample.view.common.HeaderItemBinder;
import dev.ahamed.mva.sample.view.common.ShufflingHeaderItemBinder;
import java.util.Collections;
import java.util.List;
import mva2.adapter.ItemSection;
import mva2.adapter.ListSection;
import mva2.adapter.util.InfiniteLoadingHelper;
import mva2.extension.decorator.InsetDecoration;

public class AdvancedFragment extends BaseFragment
    implements ShufflingHeaderItemBinder.ShuffleListener, TextItemBinder.SettingsProvider {

  private ListSection<ColoredItem> listSectionOne;
  private ListSection<ColoredItem> listSectionTwo;
  private ListSection<NumberItem> infiniteSection;
  private InfiniteLoadingHelper infiniteLoadingHelper;

  private Runnable bgLoadingProcess;
  @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
    @Override public void handleMessage(Message msg) {
      Bundle bundle = msg.getData();
      int page = bundle.getInt("page");
      bgLoadingProcess = null;
      infiniteSection.addAll(dataManager.getNumberItems((page - 1) * 10 + 1, 10));
      infiniteLoadingHelper.markCurrentPageLoaded();
    }
  };
  private Spinner sectionOneSpanCount;
  private Spinner sectionTwoSpanCount;
  private CheckedTextView enableDragAndDrop;
  private CheckedTextView enableSwipeToDismiss;
  private CheckedTextView enableInfiniteLoading;
  private Spinner totalPagesCount;
  private GridLayoutManager layoutManager;

  @Override public void onStop() {
    super.onStop();
    if (null != bgLoadingProcess) {
      handler.removeCallbacks(bgLoadingProcess);
    }
  }

  @Override public void initViews(View view) {
    sectionOneSpanCount = view.findViewById(R.id.spinner_section_one_span);
    sectionTwoSpanCount = view.findViewById(R.id.spinner_section_two_span);
    enableDragAndDrop = view.findViewById(R.id.ctv_drag_drop);
    enableSwipeToDismiss = view.findViewById(R.id.ctv_swipe_to_dismiss);
    enableInfiniteLoading = view.findViewById(R.id.ctv_infinite_loading);
    totalPagesCount = view.findViewById(R.id.spinner_page_count);

    View.OnClickListener onClickListener = v -> {
      CheckedTextView checkedTextView = (CheckedTextView) v;
      checkedTextView.setChecked(!checkedTextView.isChecked());
    };

    enableDragAndDrop.setOnClickListener(onClickListener);
    enableSwipeToDismiss.setOnClickListener(onClickListener);
    enableInfiniteLoading.setOnClickListener(onClickListener);

    recyclerView.setAdapter(adapter);

    layoutManager = new GridLayoutManager(getContext(), 12);
    layoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(adapter.getItemDecoration());

    adapter.registerItemBinders(
        new ColoredItemBinder(new InsetDecoration(adapter, SampleActivity.DP_EIGHT)),
        new ShufflingHeaderItemBinder(this), new HeaderItemBinder(),
        new TextItemBinder(new TextItemDecorator(adapter), this),
        new NumberItemBinder(new InsetDecoration(adapter, SampleActivity.DP_EIGHT)));
    adapter.setSpanCount(layoutManager.getSpanCount());
    adapter.getItemTouchHelper().attachToRecyclerView(recyclerView);

    ItemSection<ShufflingHeader> itemSectionOne =
        new ItemSection<>(new ShufflingHeader("Section One"));

    listSectionOne = new ListSection<>();
    listSectionOne.addAll(dataManager.getColoredItems(6));

    ItemSection<ShufflingHeader> itemSectionTwo =
        new ItemSection<>(new ShufflingHeader("Section Two"));

    listSectionTwo = new ListSection<>();
    listSectionTwo.addAll(dataManager.getColoredItems(10));

    ItemSection<Header> itemSectionThree = new ItemSection<>(new Header("Swipe and Drag Section"));
    ListSection<TextItem> textItemsSection = new ListSection<>();
    textItemsSection.addAll(dataManager.getTextItems(10));

    ItemSection<Header> itemSectionFour = new ItemSection<>(new Header("Infinite Section"));
    infiniteSection = new ListSection<>();
    infiniteSection.setSpanCount(1);

    listSectionTwo.setSwipeToDismissListener((position, item) -> {

    });

    adapter.addSection(itemSectionOne);
    adapter.addSection(listSectionOne);
    adapter.addSection(itemSectionTwo);
    adapter.addSection(listSectionTwo);
    adapter.addSection(itemSectionThree);
    adapter.addSection(textItemsSection);
    adapter.addSection(itemSectionFour);
    adapter.addSection(infiniteSection);

    infiniteLoadingHelper = new InfiniteLoadingHelper(recyclerView, R.layout.item_progress) {
      @Override public void onLoadNextPage(int page) {
        loadMore(page);
      }
    };
    adapter.setInfiniteLoadingHelper(infiniteLoadingHelper);

    adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override public void onChanged() {
        recyclerView.invalidateItemDecorations();
      }

      @Override public void onItemRangeChanged(int positionStart, int itemCount) {
        recyclerView.invalidateItemDecorations();
      }

      @Override
      public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        recyclerView.invalidateItemDecorations();
      }

      @Override public void onItemRangeInserted(int positionStart, int itemCount) {
        recyclerView.invalidateItemDecorations();
      }

      @Override public void onItemRangeRemoved(int positionStart, int itemCount) {
        recyclerView.invalidateItemDecorations();
      }

      @Override public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        recyclerView.invalidateItemDecorations();
      }
    });
  }

  @Override public int layoutId() {
    return R.layout.fragment_advanced;
  }

  @Override public void resetConfiguration() {
    sectionOneSpanCount.setSelection(2);
    sectionTwoSpanCount.setSelection(1);

    enableDragAndDrop.setChecked(true);
    enableSwipeToDismiss.setChecked(true);
    enableInfiniteLoading.setChecked(true);

    totalPagesCount.setSelection(1);

    updateConfiguration();
  }

  @Override public void updateConfiguration() {
    int sectionOneSpan = sectionOneSpanCount.getSelectedItemPosition() + 2;
    int sectionTwoSpan = sectionTwoSpanCount.getSelectedItemPosition() + 2;

    layoutManager.setSpanCount(sectionOneSpan * sectionTwoSpan);
    adapter.setSpanCount(sectionOneSpan * sectionTwoSpan);

    listSectionOne.setSpanCount(sectionOneSpan);
    listSectionTwo.setSpanCount(sectionTwoSpan);

    if (enableInfiniteLoading.isChecked()) {
      infiniteLoadingHelper.setPageCount(getPageCount(totalPagesCount.getSelectedItemPosition()));
    } else {
      infiniteLoadingHelper.markAllPagesLoaded();
    }

    adapter.notifyDataSetChanged();
  }

  @Override public void onShuffle(int adapterPosition) {
    if (adapterPosition == 0) {
      List<ColoredItem> list = listSectionOne.getData();
      Collections.shuffle(list);
      listSectionOne.set(list);
    } else {
      List<ColoredItem> list = listSectionTwo.getData();
      Collections.shuffle(list);
      listSectionTwo.set(list);
    }
  }

  @Override public boolean isDragAndDropEnabled() {
    return enableDragAndDrop.isChecked();
  }

  @Override public boolean isSwipeToDismissEnabled() {
    return enableSwipeToDismiss.isEnabled();
  }

  private void loadMore(final int page) {
    bgLoadingProcess = () -> {
      Message msg = handler.obtainMessage();
      Bundle bundle = new Bundle();
      bundle.putInt("page", page);
      msg.setData(bundle);
      handler.sendMessage(msg);
    };
    handler.postDelayed(bgLoadingProcess, 1000);
  }

  private int getPageCount(int position) {
    if (position == 1) {
      return 2;
    } else if (position == 2) {
      return 5;
    } else if (position == 3) {
      return 10;
    } else if (position == 4) {
      return 15;
    } else if (position == 5) {
      return 20;
    }
    return Integer.MAX_VALUE;
  }
}
