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

package mva2.adapter.util;

import java.util.List;

/**
 * Listener to listen for changes if an item is selected or un-selected. This can be used if the
 * adapter has the selection mode as {@link Mode#SINGLE}
 *
 * @param <M> Refers to the model class
 *
 * @see Mode for possible values
 */
public interface OnSelectionChangedListener<M> {

  /**
   * Called when the selection is changed. Even if you are using {@link Mode#SINGLE}, selected items
   * will be provided as a list. If no item is selected, it will provide an empty list.
   *
   * @param item          Item which is currently selected / unselected
   * @param isSelected    Denotes whether the item was selected or unselected
   * @param selectedItems List of selected items. It will be empty if no items are selected
   */
  void onSelectionChanged(M item, boolean isSelected, List<M> selectedItems);
}
