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

package dev.ahamed.mva.sample.view.expansion;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.FaqItem;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class ExpandableItemBinder extends ItemBinder<FaqItem, ExpandableItemBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_faq));
  }

  @Override public void bindViewHolder(ViewHolder holder, FaqItem item) {
    holder.toggle.setImageResource(
        holder.isItemExpanded() ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
    holder.question.setText(item.getQuestion());
    holder.answer.setVisibility(holder.isItemExpanded() ? View.VISIBLE : View.GONE);
    if (holder.isItemExpanded()) {
      holder.answer.setText(item.getAnswer());
    }
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof FaqItem;
  }

  static class ViewHolder extends ItemViewHolder<FaqItem> {

    TextView question;
    TextView answer;
    ImageView toggle;

    public ViewHolder(View itemView) {
      super(itemView);
      question = itemView.findViewById(R.id.tv_question);
      answer = itemView.findViewById(R.id.tv_answer);
      toggle = itemView.findViewById(R.id.iv_toggle);
      toggle.setOnClickListener(v -> toggleItemExpansion());
    }
  }
}
