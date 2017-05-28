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

package com.ahamed.sample.expandable.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Header;

public class ExpandableHeaderBinder extends ItemBinder<Header, ExpandableHeaderBinder.ViewHolder> {

  @Override public ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.item_expandable_group_header, parent, false));
  }

  @Override public void bind(ViewHolder holder, Header item) {
    holder.tvHeader.setText(item.getHeaderInfo());
    holder.ivIndicator.setImageResource(
        holder.isItemExpanded() ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Header;
  }

  static class ViewHolder extends BaseViewHolder<Header> {

    private TextView tvHeader;
    private ImageView ivIndicator;

    public ViewHolder(View itemView) {
      super(itemView);
      tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
      ivIndicator = (ImageView) itemView.findViewById(R.id.iv_expandable_indicator);

      setItemClickListener(new OnItemClickListener<Header>() {
        @Override public void onItemClick(View view, Header item) {
          toggleGroupExpansion();
          ivIndicator.setImageResource(
              isItemExpanded() ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
        }
      });
    }
  }
}

