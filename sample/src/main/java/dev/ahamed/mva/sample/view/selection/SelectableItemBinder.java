/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.ahamed.mva.sample.view.selection;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.SelectableItem;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class SelectableItemBinder
    extends ItemBinder<SelectableItem, SelectableItemBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_selectable_card));
  }

  @Override public void bindViewHolder(ViewHolder holder, SelectableItem item) {
    holder.imageView.setImageResource(item.getIconResource());
    holder.textView.setText(item.getText());
    int bgColor = ContextCompat.getColor(holder.textView.getContext(),
        holder.isItemSelected() ? item.getColor() : R.color.cardview_light_background);
    holder.linearLayout.setBackgroundColor(bgColor);
    holder.cardView.setCardElevation(holder.isItemSelected() ? 16 : 0);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof SelectableItem;
  }

  public static class ViewHolder extends ItemViewHolder<SelectableItem> {

    private ImageView imageView;
    private TextView textView;
    private CardView cardView;
    private LinearLayout linearLayout;

    public ViewHolder(View itemView) {
      super(itemView);

      imageView = itemView.findViewById(R.id.image_view);
      cardView = (CardView) itemView;
      textView = itemView.findViewById(R.id.text_view);
      linearLayout = itemView.findViewById(R.id.linear_layout);
      itemView.setOnClickListener(view -> toggleItemSelection());
    }
  }
}
