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

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import java.util.List;

@RestrictTo(RestrictTo.Scope.LIBRARY) public abstract class DiffUtilCallback<M>
    extends DiffUtil.Callback {

  private final List<M> oldList;
  private final List<M> newList;

  protected DiffUtilCallback(List<M> oldList, List<M> newList) {
    this.oldList = oldList;
    this.newList = newList;
  }

  @Override public int getOldListSize() {
    return oldList.size();
  }

  @Override public int getNewListSize() {
    return newList.size();
  }

  @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
  }

  @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
  }

  @Nullable @Override public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    Object payload = getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
    return payload != null ? payload : super.getChangePayload(oldItemPosition, newItemPosition);
  }

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
  public abstract boolean areItemsTheSame(M oldItem, M newItem);

  /**
   * Called by the DiffUtil when it wants to check whether two items have the same data.
   * DiffUtil uses this information to detect if the contents of an item has changed.
   * <p>
   * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
   * so that you can change its behavior depending on your UI.
   * For example, if you are using DiffUtil with a
   * {@link RecyclerView.Adapter RecyclerView.Adapter}, you should
   * return whether the items' visual representations are the same.
   * <p>
   * This method is called only if {@link #areItemsTheSame(int, int)} returns
   * {@code true} for these items.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list which replaces the oldItem
   *
   * @return True if the contents of the items are the same or false if they are different.
   */
  public abstract boolean areContentsTheSame(M oldItem, M newItem);

  /**
   * When {@link #areItemsTheSame(int, int)} returns {@code true} for two items and
   * {@link #areContentsTheSame(int, int)} returns false for them, DiffUtil
   * calls this method to get a payload about the change.
   * <p>
   * For example, if you are using DiffUtil with {@link RecyclerView}, you can return the
   * particular field that changed in the item and your
   * {@link RecyclerView.ItemAnimator ItemAnimator} can use that
   * information to run the correct animation.
   * <p>
   * Default implementation returns {@code null}.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list
   *
   * @return A payload object that represents the change between the two items.
   */
  public abstract Object getChangePayload(M oldItem, M newItem);
}