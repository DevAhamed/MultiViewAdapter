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

package dev.ahamed.mva.sample.view.newsfeed;

import android.view.ViewGroup;
import dev.ahamed.mva.sample.R;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class EmptyStateItemBinder extends ItemBinder<String, ItemViewHolder<String>> {

  @Override public ItemViewHolder<String> createViewHolder(ViewGroup parent) {
    return new ItemViewHolder<>(inflate(parent, R.layout.item_news_empty));
  }

  @Override public void bindViewHolder(ItemViewHolder<String> holder, String item) {
    // Nothing to bindViewHolder
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof String;
  }
}
