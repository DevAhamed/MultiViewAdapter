package com.ahamed.multiviewadapter;

import android.view.View;

public class SelectableViewHolder<M> extends BaseViewHolder<M> {

  private OnItemSelectedListener itemSelectedListener;

  public SelectableViewHolder(View itemView) {
    super(itemView);
    setItemLongClickListener(new OnItemLongClickListener<M>() {
      @Override public boolean onItemLongClick(View view, M item) {
        itemSelected();
        return null != itemSelectedListener;
      }
    });
  }

  void setItemSelectedListener(OnItemSelectedListener listener) {
    this.itemSelectedListener = listener;
  }

  protected void itemSelected() {
    if (null != itemSelectedListener) {
      itemSelectedListener.onItemSelected(getAdapterPosition());
    }
  }

  interface OnItemSelectedListener {

    void onItemSelected(int position);
  }
}