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

import android.support.v7.widget.RecyclerView;

/**
 * A Interface class used by DiffUtil while calculating the diff between two lists.
 *
 * @param <M> Refers to the model class
 */
public interface PayloadProvider<M> {

  /**
   * Called by the DataManager when it wants to check whether two items have the same data.
   * DataManager uses this information to detect if the contents of an item has changed.
   * <p>
   * DataManager uses this method to check equality instead of {@link Object#equals(Object)}
   * so that you can change its behavior depending on your UI.
   * </p>
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list which replaces the oldItem
   *
   * @return True if the contents of the items are the same or false if they are different, ie.,
   *     you should return whether the items' visual representations are the same.
   */
  boolean areContentsTheSame(M oldItem, M newItem);

  /**
   * Called by the DiffUtil to decide whether two object represent the same Item.
   * <p>
   * For example, if your items have unique ids, this method should check their id equality.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list which replaces the oldItem
   *
   * @return True if the two items represent the same object or false if they are different.
   */
  boolean areItemsTheSame(M oldItem, M newItem);

  /**
   * Called by the DataManager when it wants to get the payload of changed elements.
   * <p>
   * For example, if you are using DiffUtil with {@link RecyclerView}, you can return the
   * particular field that changed in the item and your
   * {@link RecyclerView.ItemAnimator ItemAnimator} can use that
   * information to run the correct animation.
   * </p>
   * Default implementation returns {@code null}.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list
   *
   * @return A payload object that represents the change between the two items.
   */
  @SuppressWarnings("UnusedParameters") Object getChangePayload(M oldItem, M newItem);
}