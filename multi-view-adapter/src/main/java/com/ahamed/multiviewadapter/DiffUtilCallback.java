package com.ahamed.multiviewadapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import java.util.List;

abstract class DiffUtilCallback<M> extends DiffUtil.Callback {

  private final List<M> oldList;
  private final List<M> newList;

  DiffUtilCallback(List<M> oldList, List<M> newList) {
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
    return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
  }

  @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
  }

  /**
   * When {@link #areItemsTheSame(int, int)} returns {@code true} for two items and
   * {@link #areContentsTheSame(int, int)} returns false for them, DiffUtil
   * calls this method to get a payload about the change.
   * <p>
   * For example, if you are using DiffUtil with {@link RecyclerView}, you can return the
   * particular field that changed in the item and your
   * {@link android.support.v7.widget.RecyclerView.ItemAnimator ItemAnimator} can use that
   * information to run the correct animation.
   * <p>
   * Default implementation returns {@code null}.
   *
   * @param oldItemPosition The position of the item in the old list
   * @param newItemPosition The position of the item in the new list
   * @return A payload object that represents the change between the two items.
   */
  @Nullable @Override public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    Object payload = getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
    return payload != null ? payload : super.getChangePayload(oldItemPosition, newItemPosition);
  }

  /**
   * Called by the DiffUtil when it wants to check whether two items have the same data.
   * DiffUtil uses this information to detect if the contents of an item has changed.
   * <p>
   * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
   * so that you can change its behavior depending on your UI.
   * For example, if you are using DiffUtil with a
   * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
   * return whether the items' visual representations are the same.
   * <p>
   * This method is called only if {@link #areItemsTheSame(int, int)} returns
   * {@code true} for these items.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list which replaces the oldItem
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
   * {@link android.support.v7.widget.RecyclerView.ItemAnimator ItemAnimator} can use that
   * information to run the correct animation.
   * <p>
   * Default implementation returns {@code null}.
   *
   * @param oldItem The item in the old list
   * @param newItem The item in the new list
   * @return A payload object that represents the change between the two items.
   */
  public abstract Object getChangePayload(M oldItem, M newItem);
}