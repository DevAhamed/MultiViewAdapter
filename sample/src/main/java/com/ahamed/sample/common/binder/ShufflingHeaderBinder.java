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

package com.ahamed.sample.common.binder;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Header;

public class ShufflingHeaderBinder
    extends ItemBinder<Header, ShufflingHeaderBinder.HeaderViewHolder> {

  private ShuffleListener shuffleListener;

  public ShufflingHeaderBinder(ShuffleListener shuffleListener) {
    this.shuffleListener = shuffleListener;
  }

  @Override public HeaderViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new HeaderViewHolder(layoutInflater.inflate(R.layout.item_header, parent, false),
        shuffleListener);
  }

  @Override public void bind(HeaderViewHolder holder, Header item) {
    holder.header.setText(item.getHeaderInfo());
    holder.header.setCompoundDrawablesWithIntrinsicBounds(null, null,
        ContextCompat.getDrawable(holder.header.getContext(), R.drawable.ic_shuffle), null);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Header && ((Header) item).isShuffleEnabled();
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  public interface ShuffleListener {
    void onShuffleClicked();
  }

  static class HeaderViewHolder extends BaseViewHolder<Header> {

    TextView header;

    HeaderViewHolder(View itemView, final ShuffleListener listener) {
      super(itemView);
      header = (TextView) itemView.findViewById(R.id.tv_header);
      setItemClickListener(new OnItemClickListener<Header>() {
        @Override public void onItemClick(View view, Header item) {
          listener.onShuffleClicked();
        }
      });
    }
  }
}