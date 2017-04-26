package com.ahamed.multiviewadapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

public class BaseViewHolder<M> extends ViewHolder
    implements View.OnClickListener, View.OnLongClickListener {

  private M item;
  private OnItemClickListener<M> itemClickListener;
  private OnItemLongClickListener<M> itemLongClickListener;

  public BaseViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(this);
    itemView.setOnLongClickListener(this);
  }

  public final M getItem() {
    return item;
  }

  final void setItem(M item) {
    this.item = item;
  }

  protected final void setItemClickListener(OnItemClickListener<M> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  protected final void setItemLongClickListener(OnItemLongClickListener<M> itemLongClickListener) {
    this.itemLongClickListener = itemLongClickListener;
  }

  @Override public void onClick(View view) {
    if (null == itemClickListener) return;
    itemClickListener.onItemClick(view, getItem());
  }

  @Override public boolean onLongClick(View view) {
    return null != itemLongClickListener && itemLongClickListener.onItemLongClick(view, getItem());
  }

  public interface OnItemClickListener<M> {
    void onItemClick(View view, M item);
  }

  public interface OnItemLongClickListener<M> {
    boolean onItemLongClick(View view, M item);
  }
}