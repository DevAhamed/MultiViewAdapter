package com.ahamed.sample.simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;

class SimpleItemBinder extends ItemBinder<ItemModel, SimpleItemBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_simple, parent, false));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof ItemModel;
  }

  @Override public void bind(ItemViewHolder holder, ItemModel item) {
    holder.textView.setText(item.getData());
  }

  static class ItemViewHolder extends BaseViewHolder<ItemModel> {

    private TextView textView;

    ItemViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_data);
    }
  }
}