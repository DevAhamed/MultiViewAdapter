package com.ahamed.multiviewadapter;

import android.view.View;

/**
 * @see BaseViewHolder#toggleItemSelection()
 * @deprecated Use {@link BaseViewHolder} instead.
 */
@Deprecated public class SelectableViewHolder<M> extends BaseViewHolder<M> {

  public SelectableViewHolder(View itemView) {
    super(itemView);
  }

  /**
   * Can be called by the child view holders to toggle the selection.
   *
   * <p>By default long press of the view holder will toggle the selection. If the selection has to
   * be toggled for an item in the view holder (ex: Button) this method can be called from the
   * item's click listener </p>
   */
  protected void itemSelectionToggled() {
    toggleItemSelection();
  }
}