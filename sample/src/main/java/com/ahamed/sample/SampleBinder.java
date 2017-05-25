package com.ahamed.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;

class SampleBinder extends ItemBinder<String, SampleBinder.ViewHolder> {

  private final BaseViewHolder.OnItemClickListener<String> onItemClickListener;

  SampleBinder(BaseViewHolder.OnItemClickListener<String> onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.item_sample, parent, false),
        onItemClickListener);
  }

  @Override public void bind(ViewHolder holder, String item) {
    holder.textView.setText(item);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof String;
  }

  static class ViewHolder extends BaseViewHolder<String> {

    TextView textView;

    ViewHolder(View itemView, OnItemClickListener<String> onItemClickListener) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_sample_name);
      setItemClickListener(onItemClickListener);
    }
  }
}
