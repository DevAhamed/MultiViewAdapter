package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseBinder;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Header;

public class HeaderBinder extends BaseBinder<Header, HeaderBinder.HeaderViewHolder> {

  @Override public HeaderViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new HeaderViewHolder(layoutInflater.inflate(R.layout.item_header, parent, false));
  }

  @Override public void bind(HeaderViewHolder holder, Header item) {
    holder.header.setText(item.getHeaderInfo());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Header;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class HeaderViewHolder extends BaseViewHolder<Header> {

    TextView header;

    HeaderViewHolder(View itemView) {
      super(itemView);
      header = (TextView) itemView.findViewById(R.id.tv_header);
    }
  }
}