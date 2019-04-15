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

package mva2.adapter;

import android.support.v7.widget.SpanSizeLookup;
import mva2.adapter.testconfig.AdapterDataObserver;
import mva2.adapter.testconfig.HashMapCache;

public class TestAdapter extends MultiViewAdapter {

  private final AdapterDataObserver adapterDataObserver;

  public TestAdapter(AdapterDataObserver adapterDataObserver, SpanSizeLookup spanSizeLookup) {
    super(spanSizeLookup, new HashMapCache(), new HashMapCache(), new HashMapCache());
    this.adapterDataObserver = adapterDataObserver;
  }

  @Override void notifyAdapterItemMoved(int fromPosition, int toPosition) {
    onDataSetChanged();
    adapterDataObserver.notifyItemMoved(fromPosition, toPosition);
  }

  @Override void notifyAdapterRangeChanged(int positionStart, int itemCount, Object payload) {
    onDataSetChanged();
    adapterDataObserver.notifyItemRangeChanged(positionStart, itemCount, payload);
  }

  @Override void notifyAdapterRangeInserted(int positionStart, int itemCount) {
    onDataSetChanged();
    adapterDataObserver.notifyItemRangeInserted(positionStart, itemCount);
  }

  @Override void notifyAdapterRangeRemoved(int positionStart, int itemCount) {
    onDataSetChanged();
    adapterDataObserver.notifyItemRangeRemoved(positionStart, itemCount);
  }
}
