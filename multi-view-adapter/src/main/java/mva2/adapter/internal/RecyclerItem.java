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

package mva2.adapter.internal;

public class RecyclerItem<M> {

  private final M item;
  private ItemMetaData itemMetaData;

  public RecyclerItem(M item, ItemMetaData itemMetaData) {
    this.item = item;
    this.itemMetaData = itemMetaData;
  }

  public M getItem() {
    return item;
  }

  public ItemMetaData getItemMetaData() {
    return itemMetaData;
  }
}
