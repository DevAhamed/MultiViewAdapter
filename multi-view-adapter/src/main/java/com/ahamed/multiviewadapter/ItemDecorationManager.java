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

package com.ahamed.multiviewadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ahamed.multiviewadapter.annotation.PositionType;
import com.ahamed.multiviewadapter.util.ItemDecorator;

import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_BOTTOM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_LEFT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_MIDDLE;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_RIGHT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_TOP;

/**
 * This is an internal class. Should not be extended by client code. Used to manage the different
 * {@link RecyclerView.ItemDecoration} for different {@link ItemBinder}. It will delegate the {@link
 * RecyclerView.ItemDecoration}
 */
class ItemDecorationManager extends RecyclerView.ItemDecoration {

  private final CoreRecyclerAdapter adapter;

  ItemDecorationManager(CoreRecyclerAdapter adapter) {
    this.adapter = adapter;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    boolean isReverseLayout = getIsReverseLayout(parent);
    int adapterPosition = parent.getChildAdapterPosition(view);
    if (adapterPosition < 0) {
      return;
    }
    ItemBinder binder = adapter.getBinderForPosition(adapterPosition);
    if (binder.isItemDecorationEnabled()) {
      int itemPosition = adapter.getItemPositionInManager(parent.getChildAdapterPosition(view));
      int positionType =
          getPositionType(parent, view, adapterPosition, itemPosition, isReverseLayout);
      binder.getItemOffsets(outRect, itemPosition, positionType);
    }
  }

  @Override public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
    boolean isReverseLayout = getIsReverseLayout(parent);
    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);
      int adapterPosition = parent.getChildAdapterPosition(child);
      if (adapterPosition < 0) {
        return;
      }
      ItemBinder binder = adapter.getBinderForPosition(adapterPosition);
      if (binder.isItemDecorationEnabled()) {
        int itemPosition = adapter.getItemPositionInManager(parent.getChildAdapterPosition(child));
        int positionType =
            getPositionType(parent, child, adapterPosition, itemPosition, isReverseLayout);
        binder.onDraw(canvas, parent, child, itemPosition, positionType);
      }
    }
  }

  private boolean getIsReverseLayout(RecyclerView parent) {
    return parent.getLayoutManager() instanceof LinearLayoutManager && ((LinearLayoutManager) parent
        .getLayoutManager()).getReverseLayout();
  }

  private @PositionType int getPositionType(RecyclerView parent, View child, int adapterPosition,
      int itemPosition, boolean isReverseLayout) {
    boolean isFirstItem =
        isReverseLayout ? adapter.isLastItemInManager(parent.getChildAdapterPosition(child))
            : itemPosition == 0;
    boolean isLastItem = isReverseLayout ? itemPosition == 0
        : adapter.isLastItemInManager(parent.getChildAdapterPosition(child));
    if (parent.getLayoutManager() instanceof GridLayoutManager) {
      GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
      int totalSpanSize = gridLayoutManager.getSpanCount();

      BaseDataManager dataManager = adapter.getDataManager(adapterPosition);
      int itemPositionType = 0;
      int spanCount = 0;
      boolean isFirstRow = true;
      for (int looper = 0, item = adapterPosition - itemPosition; looper < dataManager.size();
          looper++, item++) {
        int currentSpanCount = gridLayoutManager.getSpanSizeLookup().getSpanSize(item);
        if (spanCount + currentSpanCount > totalSpanSize) {
          spanCount = currentSpanCount;
          isFirstRow = false;
        } else {
          spanCount += currentSpanCount;
        }
        if (item == itemPosition) {
          if (spanCount - currentSpanCount == 0) {
            itemPositionType |= POSITION_LEFT;
          }
          if (spanCount == totalSpanSize) {
            itemPositionType |= POSITION_RIGHT;
          }
          if (isFirstItem || isFirstRow) {
            itemPositionType |= POSITION_TOP;
          }
          if (isLastItem) {
            itemPositionType |= POSITION_BOTTOM;
          }
          // FIXME
          // Third for-loop - Find a better implementation
          if (!isLastItem && (currentSpanCount != totalSpanSize)) {
            int innerLooper = looper + 1;
            int innerSpanCount = spanCount;
            for (; innerLooper < dataManager.size(); innerLooper++) {
              int nextSpanCount = gridLayoutManager.getSpanSizeLookup().getSpanSize(innerLooper);
              if (innerSpanCount + nextSpanCount > totalSpanSize) {
                return itemPositionType;
              }
              innerSpanCount += nextSpanCount;
            }
            itemPositionType |= POSITION_BOTTOM;
          }
          if (itemPositionType == 0) {
            itemPositionType = POSITION_MIDDLE;
          }
          return itemPositionType;
        }
      }
    } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
      return isFirstItem ? ItemDecorator.POSITION_FIRST_ITEM
          : isLastItem ? ItemDecorator.POSITION_LAST_ITEM : ItemDecorator.POSITION_MIDDLE_ITEM;
    }
    return ItemDecorator.POSITION_MIDDLE;
  }
}