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

import android.view.View;

/**
 * @see BaseViewHolder#toggleItemSelection()
 * @deprecated Use {@link BaseViewHolder} instead.
 */
@Deprecated public class SelectableViewHolder<M> extends BaseViewHolder<M> {

  public SelectableViewHolder(View itemView) {
    super(itemView);
  }

  /**
   * Can be called by the child view holders to toggle the selection.
   *
   * <p>By default long press of the view holder will toggle the selection. If the selection has to
   * be toggled for an item in the view holder (ex: Button) this method can be called from the
   * item's click listener </p>
   */
  protected void itemSelectionToggled() {
    toggleItemSelection();
  }
}