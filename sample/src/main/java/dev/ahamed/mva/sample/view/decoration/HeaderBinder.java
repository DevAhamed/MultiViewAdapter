package dev.ahamed.mva.sample.view.decoration;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Header;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class HeaderBinder extends ItemBinder<Header, HeaderBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_header_decorated));
  }

  @Override public void bindViewHolder(ViewHolder holder, Header item) {
    holder.header.setText(item.getHeader());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Header;
  }

  static class ViewHolder extends ItemViewHolder<Header> {

    private TextView header;

    ViewHolder(View itemView) {
      super(itemView);
      header = itemView.findViewById(R.id.tv_header);
    }
  }
}
