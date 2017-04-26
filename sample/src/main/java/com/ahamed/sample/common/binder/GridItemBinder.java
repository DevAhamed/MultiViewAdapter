package com.ahamed.sample.common.binder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ahamed.multiviewadapter.SelectableBinder;
import com.ahamed.multiviewadapter.SelectableViewHolder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.GridItem;

public class GridItemBinder extends SelectableBinder<GridItem, GridItemBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_grid, parent, false));
  }

  @Override public void bind(ItemViewHolder holder, GridItem item, boolean isSelected) {
    holder.textView.setText(item.getData());
    if (isSelected) {
      holder.textView.setBackgroundColor(Color.parseColor("#999999"));
    } else {
      holder.textView.setBackgroundColor(Color.parseColor("#00999999"));
    }
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof GridItem;
  }

  static class ItemViewHolder extends SelectableViewHolder<GridItem> {

    private TextView textView;

    ItemViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_grid_text);

      setItemClickListener(new OnItemClickListener<GridItem>() {
        @Override public void onItemClick(View view, GridItem item) {
          Toast.makeText(view.getContext(), item.getData(), Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}