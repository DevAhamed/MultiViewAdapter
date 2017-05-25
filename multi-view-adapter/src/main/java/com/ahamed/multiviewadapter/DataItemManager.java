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

import android.support.v7.widget.RecyclerView;

public final class DataItemManager<M> extends BaseDataManager<M> {

  public DataItemManager(RecyclerAdapter baseAdapter) {
    super(baseAdapter);
  }

  public DataItemManager(RecyclerAdapter baseAdapter, M item) {
    super(baseAdapter);
    getDataList().add(item);
  }

  /**
   * Adds item to the {@link DataItemManager}. This will call the necessary {@link
   * RecyclerView.ItemAnimator}'s animation.
   *
   * @param item item to be added to the
   */
  public final void setItem(M item) {
    if (getDataList().size() == 0) {
      getDataList().add(item);
      onInserted(0, 1);
    } else {
      getDataList().set(0, item);
      onChanged(0, 1, null);
    }
  }

  /**
   * Removes the item from {@link DataItemManager}. This will call the {@link
   * RecyclerView.ItemAnimator}'s remove animation.
   */
  public final void removeItem() {
    if (getDataList().size() > 0) {
      getDataList().clear();
      onRemoved(0, 1);
    }
  }
}