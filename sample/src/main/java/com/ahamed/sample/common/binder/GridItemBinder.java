package com.ahamed.sample.common.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ahamed.multiviewadapter.BaseBinder;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.GridItem;

public class GridItemBinder extends BaseBinder<GridItem, GridItemBinder.ItemViewHolder> {

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_grid, parent, false));
  }

  @Override public void bind(ItemViewHolder holder, GridItem item) {
    holder.textView.setText(item.getData());
  }

  @Override public Class<GridItem> getType() {
    return GridItem.class;
  }

  static class ItemViewHolder extends BaseViewHolder<GridItem> {

    private TextView textView;

    ItemViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_grid_text);

      setListener(new OnItemClickedListener<GridItem>() {
        @Override public void onItemClicked(View view, GridItem item, int position) {
          Toast.makeText(view.getContext(), item.getData(), Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}