package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    holder.itemView.setBackgroundColor(item.getColor());
    holder.ivIcon.setImageResource(item.getDrawable());
    if (isSelected) {
      holder.ivSelectionIndicator.setVisibility(View.VISIBLE);
    } else {
      holder.ivSelectionIndicator.setVisibility(View.GONE);
    }
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof GridItem;
  }

  static class ItemViewHolder extends SelectableViewHolder<GridItem> {

    private ImageView ivIcon;
    private ImageView ivSelectionIndicator;
    private View itemView;

    ItemViewHolder(View itemView) {
      super(itemView);
      ivSelectionIndicator = (ImageView) itemView.findViewById(R.id.iv_selection_indicator);
      ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
      this.itemView = itemView;

      setItemClickListener(new OnItemClickListener<GridItem>() {
        @Override public void onItemClick(View view, GridItem item) {
          Toast.makeText(view.getContext(), item.getData(), Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}