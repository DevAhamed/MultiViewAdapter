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

package android.support.v7.widget;

import mva2.adapter.MultiViewAdapter;
import mva2.adapter.internal.Cache;
import mva2.adapter.internal.SparseIntArrayCache;

/**
 * Wrapper class around {@link GridLayoutManager.SpanSizeLookup}. This exposes an package-private
 * method as public method used inside the library.
 *
 * Also it adds caching for {@link GridLayoutManager.SpanSizeLookup#getSpanGroupIndex(int, int)}
 * method. This method os called often by the library hence the caching.
 */
public class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

  private MultiViewAdapter multiViewAdapter;
  private final Cache groupIndexCache;

  /**
   * Constructor to initialize the SpanSizeLookup class.
   */
  public SpanSizeLookup(Cache cache) {
    setSpanIndexCacheEnabled(true);
    groupIndexCache = cache;
  }

  /**
   * When the adapter's data set changes, this method will be called to clear the cache.
   */
  public void clearCache() {
    groupIndexCache.clear();
    if (groupIndexCache instanceof SparseIntArrayCache) {
      super.invalidateSpanIndexCache();
    }
  }

  /**
   * Attach the adapter to the SpanSizeLookup
   *
   * @param multiViewAdapter Adapter to be attached
   */
  public void setAdapter(MultiViewAdapter multiViewAdapter) {
    this.multiViewAdapter = multiViewAdapter;
  }

  @Override public int getSpanSize(int adapterPosition) {
    return multiViewAdapter.getSpanSize(adapterPosition);
  }

  @Override public int getCachedSpanIndex(int position, int spanCount) {
    return super.getCachedSpanIndex(position, spanCount);
  }

  /**
   * Returns the index of the group this position belongs.
   * <p>
   * For example, if grid has 3 columns and each item occupies 1 span, span group index
   * for item 1 will be 0, item 5 will be 1.
   *
   * @param adapterPosition The position in adapter
   * @param spanCount       The total number of spans in the grid
   *
   * @return The index of the span group including the item at the given adapter position
   */
  @Override public int getSpanGroupIndex(int adapterPosition, int spanCount) {
    int group = groupIndexCache.get(adapterPosition, -1);
    if (group == -1) {
      int positionSpanSize = getSpanSize(adapterPosition);

      int previousPosition = adapterPosition - 1;
      int span = previousPosition < 0 ? 0 : getCachedSpanIndex(previousPosition, spanCount);
      group = previousPosition < 0 ? 0 : getSpanGroupIndex(previousPosition, spanCount);
      span += previousPosition < 0 ? 0 : getSpanSize(previousPosition);
      if (span + positionSpanSize > spanCount) {
        group++;
      }

      groupIndexCache.append(adapterPosition, group);
    }
    return group;
  }
}
