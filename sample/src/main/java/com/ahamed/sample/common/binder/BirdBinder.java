package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemDecorator;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Bird;

public class BirdBinder extends ItemBinder<Bird, BirdBinder.ViewHolder> {

  public BirdBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public BirdBinder.ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_bird, parent, false));
  }

  @Override public void bind(ViewHolder holder, Bird item) {
    holder.textView.setText(item.getBirdName());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Bird;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends BaseViewHolder<Bird> {

    private TextView textView;

    ViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_bird_name);
    }
  }
}