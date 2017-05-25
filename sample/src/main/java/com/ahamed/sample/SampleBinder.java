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

package com.ahamed.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;

class SampleBinder extends ItemBinder<String, SampleBinder.ViewHolder> {

  private final BaseViewHolder.OnItemClickListener<String> onItemClickListener;

  SampleBinder(BaseViewHolder.OnItemClickListener<String> onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public ViewHolder create(LayoutInflater inflater, ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.item_sample, parent, false),
        onItemClickListener);
  }

  @Override public void bind(ViewHolder holder, String item) {
    holder.textView.setText(item);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof String;
  }

  static class ViewHolder extends BaseViewHolder<String> {

    TextView textView;

    ViewHolder(View itemView, OnItemClickListener<String> onItemClickListener) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_sample_name);
      setItemClickListener(onItemClickListener);
    }
  }
}
