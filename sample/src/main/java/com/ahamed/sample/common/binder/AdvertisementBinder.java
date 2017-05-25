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

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Advertisement;

public class AdvertisementBinder extends ItemBinder<Advertisement, AdvertisementBinder.ViewHolder> {

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_promotion, parent, false));
  }

  @Override public void bind(ViewHolder holder, Advertisement item) {
    holder.tvTitle.setText(item.getAdDescription());
    holder.tvDescription.setText(item.getAdSecondaryText());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Advertisement;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends BaseViewHolder<Advertisement> {

    private TextView tvTitle;
    private TextView tvDescription;
    private Button btnGithub;

    ViewHolder(View itemView) {
      super(itemView);
      tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
      btnGithub = (Button) itemView.findViewById(R.id.btn_github);
      btnGithub.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getItem().getUrl()));
          v.getContext().startActivity(browserIntent);
        }
      });
    }
  }
}