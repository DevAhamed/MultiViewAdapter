package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.GridItem;

public class GridItemBinder extends ItemBinder<GridItem, GridItemBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_grid, parent, false));
  }

  @Override public void bind(ItemViewHolder holder, GridItem item) {
    holder.itemView.setBackgroundColor(item.getColor());
    holder.ivIcon.setImageResource(item.getDrawable());
    if (holder.isItemSelected()) {
      holder.ivSelectionIndicator.setVisibility(View.VISIBLE);
    } else {
      holder.ivSelectionIndicator.setVisibility(View.GONE);
    }
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof GridItem;
  }

  static class ItemViewHolder extends BaseViewHolder<GridItem> {

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

      setItemLongClickListener(new OnItemLongClickListener<GridItem>() {
        @Override public boolean onItemLongClick(View view, GridItem item) {
          toggleItemSelection();
          return true;
        }
      });
    }
  }
}