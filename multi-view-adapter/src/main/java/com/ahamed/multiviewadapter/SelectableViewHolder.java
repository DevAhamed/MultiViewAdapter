package com.ahamed.multiviewadapter;

import android.view.View;

public class SelectableViewHolder<M> extends BaseViewHolder<M> {

  private OnItemSelectedListener itemSelectedListener;

  public SelectableViewHolder(View itemView) {
    super(itemView);
    setItemLongClickListener(new OnItemLongClickListener<M>() {
      @Override public boolean onItemLongClick(View view, M item) {
        itemSelectionToggled();
        return null != itemSelectedListener;
      }
    });
  }

  void setItemSelectedListener(OnItemSelectedListener listener) {
    this.itemSelectedListener = listener;
  }

  ////////////////////////////////////////
  ///////// Public Methods ///////////////
  ////////////////////////////////////////

  /**
   * Can be called by the child view holders to toggle the selection.
   *
   * <p>By default long press of the view holder will toggle the selection. If the selection has to
   * be toggled for an item in the view holder (ex: Button) this method can be called from the
   * item's click listener </p>
   */
  protected void itemSelectionToggled() {
    if (null != itemSelectedListener) {
      itemSelectedListener.onItemSelected(getAdapterPosition());
    }
  }

  interface OnItemSelectedListener {

    void onItemSelected(int position);
  }
}