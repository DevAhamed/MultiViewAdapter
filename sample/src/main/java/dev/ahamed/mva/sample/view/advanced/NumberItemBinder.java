package dev.ahamed.mva.sample.view.advanced;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.NumberItem;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class NumberItemBinder extends ItemBinder<NumberItem, NumberItemBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_number));
  }

  @Override public void bindViewHolder(ViewHolder holder, NumberItem item) {
    holder.textView.setText(String.valueOf(item.getNumber()));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof NumberItem;
  }

  static final class ViewHolder extends ItemViewHolder<NumberItem> {

    TextView textView;

    ViewHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.text_view);
    }
  }
}
