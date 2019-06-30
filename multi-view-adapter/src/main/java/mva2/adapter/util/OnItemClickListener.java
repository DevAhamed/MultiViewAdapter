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

import mva2.adapter.ItemSection;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.ListSection;

/**
 * This interface lets you listen to the click event of the items inside the ListSection or
 * ItemSection. You can attach this listener to the section and listen for click events. To pass
 * down the click events to this interface call {@link ItemViewHolder#onItemClick()} method.
 *
 * @param <M> Model used in the {@link ListSection} or {@link ItemSection} where this listener is
 *            attached.
 *
 * @see ItemViewHolder#onItemClick()
 */
public interface OnItemClickListener<M> {

  void onItemClicked(int position, M item);
}
