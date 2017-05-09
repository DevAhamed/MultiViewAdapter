package com.ahamed.multiviewadapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import java.util.Collection;
import java.util.List;

public final class DataListManager<M> extends BaseDataManager<M> {

  private final PayloadProvider<M> payloadProvider;

  public DataListManager(RecyclerAdapter adapter) {
    this(adapter, new PayloadProvider<M>() {
      @Override public boolean areContentsTheSame(M oldItem, M newItem) {
        return oldItem.equals(newItem);
      }

      @Override public Object getChangePayload(M oldItem, M newItem) {
        return null;
      }
    });
  }

  public DataListManager(RecyclerAdapter adapter, PayloadProvider<M> payloadProvider) {
    super(adapter);
    this.payloadProvider = payloadProvider;
  }

  /**
   * Appends the specified element to the end of {@link DataListManager}. Also calls {@link
   * RecyclerView.ItemAnimator}'s add animation.
   *
   * @param item element to be appended to this list
   * @return <tt>true</tt> (as specified by {@link Collection#add})
   * @throws UnsupportedOperationException if the <tt>add</tt> operation
   * is not supported by this {@link DataListManager}
   */
  public final boolean add(M item) {
    boolean result = getDataList().add(item);
    if (result) {
      onInserted(getDataList().size() - 1, 1);
    }
    return result;
  }

  /**
   * Appends all of the elements in the specified collection to the end of
   * this {@link DataListManager}, in the order that they are returned by the specified
   * collection's iterator (optional operation).  The behavior of this
   * operation is undefined if the specified collection is modified while
   * the operation is in progress.  (Note that this will occur if the
   * specified collection is this list, and it's nonempty.) Also calls {@link
   * RecyclerView.ItemAnimator}'s add animation.
   *
   * @param items collection containing elements to be added to this list
   * @return <tt>true</tt> if this list changed as a result of the call
   * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
   * is not supported by this {@link DataListManager}
   * @see #add(Object)
   */
  public final boolean addAll(@NonNull Collection<? extends M> items) {
    return addAll(getDataList().size(), items);
  }

  /**
   * Inserts all of the elements in the specified collection into this
   * data manager at the specified position (optional operation).  Shifts the
   * element currently at that position (if any) and any subsequent
   * elements to the right (increases their indices).  The new elements
   * will appear in this list in the order that they are returned by the
   * specified collection's iterator.  The behavior of this operation is
   * undefined if the specified collection is modified while the
   * operation is in progress. Also calls {@link
   * RecyclerView.ItemAnimator}'s add animation.
   *
   * @param index index at which to insert the first element from the
   * specified collection
   * @param items collection containing elements to be added to this list
   * @return <tt>true</tt> if this data manager changed as a result of the call
   * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
   * is not supported by this data manager
   */
  public final boolean addAll(int index, @NonNull Collection<? extends M> items) {
    boolean result = getDataList().addAll(index, items);
    if (result) {
      onInserted(index, items.size());
    }
    return result;
  }

  /**
   * Inserts the specified element at the specified position in this data manger
   * (optional operation).  Shifts the element currently at that position
   * (if any) and any subsequent elements to the right (adds one to their
   * indices). Also calls {@link
   * RecyclerView.ItemAnimator}'s add animation.
   *
   * @param index index at which the specified element is to be inserted
   * @param item element to be inserted
   * @throws UnsupportedOperationException if the <tt>add</tt> operation
   * is not supported by this data manger
   * @throws IndexOutOfBoundsException if the index is out of range
   * (<tt>index &lt; 0 || index &gt; size()</tt>)
   */
  public final void add(int index, M item) {
    getDataList().add(index, item);
    onInserted(index, 1);
  }

  /**
   * Replaces the element at the specified position in this data manager with the
   * specified element (optional operation). Also calls necessary {@link
   * RecyclerView.ItemAnimator}'s animation with payload.
   *
   * @param index index of the element to replace
   * @param item element to be stored at the specified position
   * @throws UnsupportedOperationException if the <tt>set</tt> operation
   * is not supported by this data manager
   * @throws IndexOutOfBoundsException if the index is out of range
   * (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  public final void set(int index, M item) {
    M oldItem = getDataList().get(index);
    getDataList().set(index, item);
    onChanged(index, 1, payloadProvider.getChangePayload(oldItem, item));
  }

  /**
   * Replaces the data list in this data manager with the new list. Also calls necessary {@link
   * RecyclerView.ItemAnimator}'s animation with payload.
   *
   * @param dataList list to be stored in the data manager
   */
  public final void set(List<M> dataList) {
    DiffUtil.DiffResult result =
        DiffUtil.calculateDiff(new DiffUtilCallback<M>(this.getDataList(), dataList) {
          @Override public boolean areContentsTheSame(M oldItem, M newItem) {
            return payloadProvider.areContentsTheSame(oldItem, newItem);
          }

          @Override public Object getChangePayload(M oldItem, M newItem) {
            return payloadProvider.getChangePayload(oldItem, newItem);
          }
        });
    setDataList(dataList);
    result.dispatchUpdatesTo(this);
  }

  /**
   * Removes the first occurrence of the specified element from this data manager,
   * if it is present (optional operation).  If this data manager does not contain
   * the element, it is unchanged.  More formally, removes the element with
   * the lowest index <tt>i</tt> such that
   * <tt>(item==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;item.equals(get(i)))</tt>
   * (if such an element exists).  If the data manager contained the element the element is removed
   * and {@link RecyclerView.ItemAnimator}'s remove animation will be called.
   *
   * @param item element to be removed from this data manager, if present
   */
  public final void remove(M item) {
    int index = getDataList().indexOf(item);
    boolean result = getDataList().remove(item);
    if (result) {
      onRemoved(index, 1);
    }
  }

  /**
   * Removes the element at the specified position in this list (optional operation).  Shifts any
   * subsequent elements to the left (subtracts one from their indices). If the element is removed
   * {@link RecyclerView.ItemAnimator}'s remove animation will be called.
   *
   * @param index the index of the element to be removed
   * @throws IndexOutOfBoundsException if the index is out of range
   * (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  public final void remove(int index) {
    if (index >= size()) {
      throw new IndexOutOfBoundsException();
    }
    getDataList().remove(index);
    onRemoved(index, 1);
  }

  /**
   * Removes all of the elements from this data manager (optional operation).
   * The data manager will be empty after this call returns.  After the elements are removed
   * {@link RecyclerView.ItemAnimator}'s remove animation will be called.
   */
  public final void clear() {
    if (size() <= 0) {
      return;
    }
    int oldSize = getDataList().size();
    getDataList().clear();
    onRemoved(0, oldSize);
  }

  public interface PayloadProvider<M> {

    /**
     * Called by the {@link BaseDataManager} when it wants to check whether two items have the same
     * data.
     * BaseDataManager uses this information to detect if the contents of an item has changed.
     * <p>
     * BaseDataManager uses this method to check equality instead of {@link Object#equals(Object)}
     * so that you can change its behavior depending on your UI.
     *
     * @param oldItem The item in the old list
     * @param newItem The item in the new list which replaces the oldItem
     * @return True if the contents of the items are the same or false if they are different, ie.,
     * you
     * should return whether the items' visual representations are the same.
     */
    boolean areContentsTheSame(M oldItem, M newItem);

    /**
     * Called by the {@link BaseDataManager} when it wants to get the payload of changed elements.
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
    @SuppressWarnings("UnusedParameters") Object getChangePayload(M oldItem, M newItem);
  }
}