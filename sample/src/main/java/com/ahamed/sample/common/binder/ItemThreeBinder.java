package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.ItemThree;

public class ItemThreeBinder extends ItemBinder<ItemThree, ItemThreeBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_three, parent, false));
  }

  @Override public void bind(ItemViewHolder holder, ItemThree item) {
    holder.textView.setText(item.getData());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof ItemThree;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ItemViewHolder extends BaseViewHolder<ItemThree> {

    private TextView textView;

    ItemViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_content);
    }
  }
}
