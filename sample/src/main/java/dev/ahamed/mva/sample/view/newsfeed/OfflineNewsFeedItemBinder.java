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

package dev.ahamed.mva.sample.view.newsfeed;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.NewsItem;

public class OfflineNewsFeedItemBinder extends NewsFeedItemBinder {

  @Override public NewsFeedItemBinder.ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_news));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof NewsItem && ((NewsItem) item).isOffline();
  }

  static class ViewHolder extends NewsFeedItemBinder.ViewHolder {

    public ViewHolder(View itemView) {
      super(itemView);
    }

    @Override public int getSwipeDirections() {
      return ItemTouchHelper.START | ItemTouchHelper.END;
    }
  }
}
