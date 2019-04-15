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

package dev.ahamed.mva.sample.view.advanced;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.TextItem;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.decorator.Decorator;

public class TextItemBinder extends ItemBinder<TextItem, TextItemBinder.ViewHolder> {

  private final SettingsProvider settingsProvider;

  TextItemBinder(Decorator decorator, SettingsProvider settingsProvider) {
    super(decorator);
    this.settingsProvider = settingsProvider;
  }

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(settingsProvider, inflate(parent, R.layout.item_text));
  }

  @Override public void bindViewHolder(ViewHolder holder, TextItem item) {
    holder.textView.setText(item.getText());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof TextItem;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static final class ViewHolder extends ItemViewHolder<TextItem> {

    TextView textView;
    ImageView imageView;
    private final SettingsProvider settingsProvider;

    ViewHolder(SettingsProvider settingsProvider, View itemView) {
      super(itemView);
      this.settingsProvider = settingsProvider;
      textView = itemView.findViewById(R.id.text_view);
      imageView = itemView.findViewById(R.id.image_view);

      imageView.setOnLongClickListener(v -> {
        startDrag();
        return true;
      });
    }

    @Override public int getDragDirections() {
      return settingsProvider.isDragAndDropEnabled() ? ItemTouchHelper.UP | ItemTouchHelper.DOWN
          : 0;
    }

    @Override public int getSwipeDirections() {
      return settingsProvider.isSwipeToDismissEnabled() ? ItemTouchHelper.LEFT
          | ItemTouchHelper.RIGHT : 0;
    }
  }

  interface SettingsProvider {

    boolean isDragAndDropEnabled();

    boolean isSwipeToDismissEnabled();
  }
}
