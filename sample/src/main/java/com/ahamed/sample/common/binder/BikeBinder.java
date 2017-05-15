package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemDecorator;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Bike;

public class BikeBinder extends ItemBinder<Bike, BikeBinder.ViewHolder> {

  public BikeBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_bike, parent, false));
  }

  @Override public void bind(ViewHolder holder, Bike item) {
    holder.tvName.setText(item.getBikeName());
    holder.tvDescription.setText(item.getDescription());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Bike;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends BaseViewHolder<Bike> {

    private TextView tvName;
    private TextView tvDescription;

    ViewHolder(View itemView) {
      super(itemView);
      tvName = (TextView) itemView.findViewById(R.id.tv_name);
      tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
    }
  }
}