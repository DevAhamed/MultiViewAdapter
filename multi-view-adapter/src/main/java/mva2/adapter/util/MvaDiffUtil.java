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

import android.support.v7.util.ListUpdateCallback;
import java.util.List;
import mva2.adapter.ListSection;

/**
 * MvaDiffUtil lets the client to calculate the diff between old & new list and apply the changes.
 * By default the diff happens on the main thread. With the help of MvaDiffUtil the client can
 * totally take over how the diff is calculated.
 *
 * @param <M> Model class for which the diff is calculated
 *
 * @see ListSection#setDiffUtil(MvaDiffUtil)
 * @see ListSection#setDiffUtil(MvaDiffUtil)
 */
public interface MvaDiffUtil<M> {

  /**
   * Lets you calculate the diff between oldList and newList, after that apply the changes to
   * listUpdateCallback
   *
   * @param listUpdateCallback Callback where the updates has to be posted
   * @param oldList            List of old items
   * @param newList            List of new items
   */
  void calculateDiff(ListUpdateCallback listUpdateCallback, List<M> oldList, List<M> newList);
}
