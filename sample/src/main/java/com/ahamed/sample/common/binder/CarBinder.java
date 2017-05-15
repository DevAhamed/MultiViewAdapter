package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemDecorator;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Car;

public class CarBinder extends ItemBinder<Car, CarBinder.ViewHolder> {

  public CarBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_car, parent, false));
  }

  @Override public void bind(ViewHolder holder, Car item) {
    holder.tvName.setText(item.getModelName());
    holder.tvMake.setText(item.getMake());
    holder.tvYear.setText(item.getYear());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Car;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends BaseViewHolder<Car> {

    private TextView tvName;
    private TextView tvMake;
    private TextView tvYear;

    ViewHolder(View itemView) {
      super(itemView);
      tvName = (TextView) itemView.findViewById(R.id.tv_name);
      tvMake = (TextView) itemView.findViewById(R.id.tv_make);
      tvYear = (TextView) itemView.findViewById(R.id.tv_year);
    }
  }
}