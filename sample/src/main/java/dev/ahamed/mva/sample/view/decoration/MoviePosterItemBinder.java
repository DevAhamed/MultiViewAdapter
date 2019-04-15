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

package dev.ahamed.mva.sample.view.decoration;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.MoviePosterItem;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class MoviePosterItemBinder
    extends ItemBinder<MoviePosterItem, MoviePosterItemBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_movie_poster));
  }

  @Override public void bindViewHolder(ViewHolder holder, MoviePosterItem item) {
    holder.textView.setText(item.getName());
    holder.textView.setBackgroundColor(item.getPosterColor());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof MoviePosterItem;
  }

  static class ViewHolder extends ItemViewHolder<MoviePosterItem> {

    TextView textView;

    ViewHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.text_view);
    }
  }
}
