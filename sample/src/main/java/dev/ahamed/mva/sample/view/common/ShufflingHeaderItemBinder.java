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

package dev.ahamed.mva.sample.view.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.ShufflingHeader;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class ShufflingHeaderItemBinder
    extends ItemBinder<ShufflingHeader, ShufflingHeaderItemBinder.ViewHolder> {

  private final ShuffleListener listener;

  public ShufflingHeaderItemBinder(ShuffleListener listener) {
    this.listener = listener;
  }

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_shuffling_header));
  }

  @Override public void bindViewHolder(ViewHolder holder, ShufflingHeader item) {
    holder.header.setText(item.getHeader());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof ShufflingHeader;
  }

  @Override public void initViewHolder(final ViewHolder holder) {
    holder.imageView.setOnClickListener(v -> listener.onShuffle(holder.getAdapterPosition()));
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends ItemViewHolder<ShufflingHeader> {

    private TextView header;
    private ImageView imageView;

    ViewHolder(View itemView) {
      super(itemView);
      header = itemView.findViewById(R.id.tv_header);
      imageView = itemView.findViewById(R.id.iv_shuffle);
    }
  }

  public interface ShuffleListener {
    void onShuffle(int adapterPosition);
  }
}
