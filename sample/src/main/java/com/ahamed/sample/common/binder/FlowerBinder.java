package com.ahamed.sample.common.binder;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Flower;

public class FlowerBinder extends ItemBinder<Flower, FlowerBinder.ViewHolder> {

  public FlowerBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_flower, parent, false));
  }

  @Override public void bind(ViewHolder holder, Flower item) {
    holder.textView.setText(item.getFlowerName());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Flower;
  }

  static class ViewHolder extends BaseViewHolder<Flower> {

    private TextView textView;

    ViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_flower_name);
    }

    @Override public int getSwipeDirections() {
      return ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    }
  }
}