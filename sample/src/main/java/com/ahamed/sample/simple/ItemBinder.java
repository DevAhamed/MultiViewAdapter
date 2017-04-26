package com.ahamed.sample.simple;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.SelectableBinder;
import com.ahamed.multiviewadapter.SelectableViewHolder;
import com.ahamed.sample.R;

class ItemBinder extends SelectableBinder<ItemModel, ItemBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_simple, parent, false));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof ItemModel;
  }

  @Override public void bind(ItemViewHolder holder, ItemModel item, boolean isSelected) {
    if (isSelected) {
      holder.textView.setTextColor(Color.parseColor("#33B5E5"));
    }
    holder.textView.setText(item.getData());
  }

  static class ItemViewHolder extends SelectableViewHolder<ItemModel> {

    private TextView textView;

    ItemViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_data);
    }
  }
}