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

package dev.ahamed.mva.sample.view.basic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.NumberItem;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.decorator.Decorator;

public class NumberItemBinder extends ItemBinder<NumberItem, NumberItemBinder.ViewHolder> {

  public NumberItemBinder(Decorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basic, null, false));
  }

  @Override public void bindViewHolder(ViewHolder holder, NumberItem item) {
    holder.textView.setText(String.valueOf(item.getNumber()));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof NumberItem;
  }

  static final class ViewHolder extends ItemViewHolder<NumberItem> {

    TextView textView;

    ViewHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.text_view);
    }
  }
}
