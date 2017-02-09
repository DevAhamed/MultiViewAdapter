package com.ahamed.multiviewadapter;

import android.support.v7.widget.GridLayoutManager;

public class RecyclerGridAdapter extends RecyclerListAdapter {

  private int maxSpanCount = 1;

  private final GridLayoutManager.SpanSizeLookup spanSizeLookup =
      new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          return getBinderForPosition(position).getSpanSize(maxSpanCount);
        }
      };

  public final void setSpanCount(int maxSpanCount) {
    this.maxSpanCount = maxSpanCount;
  }

  public final GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
    return spanSizeLookup;
  }
}