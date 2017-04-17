package com.ahamed.multiviewadapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

public class BaseViewHolder<M> extends ViewHolder implements View.OnClickListener {

  private M item;
  private OnItemClickedListener<M> listener;

  public BaseViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(this);
  }

  public final M getItem() {
    return item;
  }

  final void setItem(M item) {
    this.item = item;
  }

  protected final void setListener(OnItemClickedListener<M> listener) {
    this.listener = listener;
  }

  @Override public void onClick(View view) {
    if (null == listener) return;
    listener.onItemClicked(view, getItem(), getAdapterPosition());
  }

  public interface OnItemClickedListener<M> {
    void onItemClicked(View view, M item, int position);
  }
}