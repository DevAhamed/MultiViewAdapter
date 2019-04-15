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

package mva2.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import mva2.adapter.decorator.Decorator;

/**
 * Each item displayed in recylerview needs to be backed by a viewholder. ViewHolders will be
 * created and binded by the ItemBinders.
 *
 * @param <M>  Model which is binded by this class
 * @param <VH> ViewHolder for this ItemBinder class
 */
@SuppressWarnings("ConstantConditions")
public abstract class ItemBinder<M, VH extends ItemViewHolder<M>> {

  private List<Decorator> decorators = new ArrayList<>();

  /**
   * No-arg constructor for ItemBinder.
   */
  public ItemBinder() {
  }

  /**
   * Initializes the ItemBinder class and adds the decorator to this ItemBinder.
   *
   * @param decorator Decorator to be used for this ItemBinder
   */
  public ItemBinder(Decorator decorator) {
    addDecorator(decorator);
  }

  /**
   * Used to add {@link Decorator} for the item binder. If null value is set the method exits
   * silently.
   *
   * @param decorator {@link Decorator} that needs to be added
   */
  public void addDecorator(@NonNull Decorator decorator) {
    addDecorator(decorator, -1);
  }

  /**
   * Used to add {@link Decorator} for the item binder with priority. If null value is set the
   * method exits
   * * silently.
   *
   * @param decorator {@link Decorator} to be added
   * @param priority  Position in the decoration chain to insert this decoration at. If this value
   *                  is negative the decoration will be added at the end.
   */
  public void addDecorator(@NonNull Decorator decorator, int priority) {
    if (null == decorator) {
      return;
    }
    if (null == decorators) {
      decorators = new ArrayList<>();
    }
    if (priority >= 0 && decorators.size() > priority) {
      decorators.add(priority, decorator);
    } else {
      decorators.add(decorator);
    }
  }

  /**
   * @param holder   holder The ItemViewHolder which should be updated to represent the contents of
   *                 the
   *                 item at the given position in the data set.
   * @param item     The object which holds the data
   * @param payloads A non-null list of merged payloads. Can be empty list if requires full
   *                 update.
   *
   * @see #bindViewHolder(ItemViewHolder, Object)
   */
  public void bindViewHolder(VH holder, M item, List payloads) {
    bindViewHolder(holder, item);
  }

  /**
   * Used to determine the span size for the {@link ItemBinder}.
   * <p>
   * By default the {@link ItemBinder} has the span size as 1. It can be overridden by the child
   * ItemBinder to provide the custom span size.
   * </p>
   *
   * @param maxSpanCount The maximum span count of the {@link GridLayoutManager} used inside the
   *                     RecyclerView
   *
   * @return Returns the span size
   */
  public int getSpanSize(int maxSpanCount) {
    return 1;
  }

  /**
   * Utility method to inflate the View
   *
   * @param parent      The ViewGroup into which the new View will be added after it is bound to
   *                    an adapter position.
   * @param layoutResId Layout to be inflated
   *
   * @return View that was inflated
   */
  public View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
    return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
  }

  /**
   * This method is called immediately after {@link ItemBinder#createViewHolder} method is called.
   * Use this method for setting click listeners and others similar stuff. For example, all the
   * initialization code inside the {@link ItemViewHolder#ItemViewHolder(View)} is qualified to be
   * here.
   *
   * @param holder The ItemViewHolder which should be updated to represent the contents of
   *               the item at the given position in the data set.
   */
  public void initViewHolder(VH holder) {
    // Write your own implementation here.
  }

  /**
   * Removes all decorators added to this ItemBinder
   */
  public void removeAllDecorators() {
    decorators.clear();
  }

  /**
   * Removes the first occurrence of the specified decorator from this ItemBinder.
   *
   * @param decorator Decorator to be removed from this ItemBinder, if present
   *
   * @return <tt>true</tt> if this list contained the specified element
   */
  public boolean removeDecorator(Decorator decorator) {
    return decorators.remove(decorator);
  }

  /**
   * Removes the decorator at the specified position from this ItemBinder.
   *
   * @param index the index of the Decorator to be removed
   */
  public void removeDecorator(int index) {
    decorators.remove(index);
  }

  /**
   * @param parent The ViewGroup into which the new View will be added after it is bound to
   *               an adapter position.
   *
   * @return A new ItemViewHolder that holds a View for the given {@link ItemBinder}.
   */
  public abstract VH createViewHolder(ViewGroup parent);

  /**
   * @param holder holder The ItemViewHolder which should be updated to represent the contents of
   *               the
   *               item at the given position in the data set.
   * @param item   The object which holds the data
   *
   * @see #bindViewHolder(ItemViewHolder, Object, List)
   */
  public abstract void bindViewHolder(VH holder, M item);

  /**
   * @param item The object from the data set
   *
   * @return boolean value which determines whether the {@link ItemBinder} can bindViewHolder the
   *     item to the ItemViewHolder
   */
  public abstract boolean canBindData(Object item);

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  VH createViewHolder(ViewGroup parent, MultiViewAdapter adapter) {
    VH viewHolder = createViewHolder(parent);
    viewHolder.setAdapter(adapter);
    initViewHolder(viewHolder);
    return viewHolder;
  }

  void drawItemDecoration(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    for (Decorator decorator : decorators) {
      decorator.onDraw(canvas, parent, state, child, adapterPosition);
    }
  }

  void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, int adapterPosition) {
    for (Decorator decorator : decorators) {
      decorator.getItemOffsets(outRect, view, parent, state, adapterPosition);
    }
  }
}
