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

package com.ahamed.multiviewadapter.listener;

import com.ahamed.multiviewadapter.annotation.SelectionMode;
import java.util.List;

/**
 * Listener to listen for changes if an item is selected or un-selected. This can be used if the
 * adapter has the selection mode as "SELECTION_MODE_MULTIPLE"
 *
 * @param <M> Refers to the model class
 * @see SelectionMode annotation for possible values
 */
public interface MultiSelectionChangedListener<M> {
  void onMultiSelectionChangedListener(List<M> selectedItems);
}
