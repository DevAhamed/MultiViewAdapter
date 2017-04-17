package com.ahamed.multiviewadapter;

import android.view.View;

public class SelectableViewHolder<M> extends BaseViewHolder<M> {

  private OnItemSelectedListener listener;

  public SelectableViewHolder(View itemView) {
    super(itemView);
    itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        itemSelected();
        return null != listener;
      }
    });
  }

  public void setListener(OnItemSelectedListener listener) {
    this.listener = listener;
  }

  public void itemSelected() {
    if (null != listener) {
      listener.onItemSelected(getAdapterPosition());
    }
  }

  public interface OnItemSelectedListener {

    void onItemSelected(int position);
  }
}