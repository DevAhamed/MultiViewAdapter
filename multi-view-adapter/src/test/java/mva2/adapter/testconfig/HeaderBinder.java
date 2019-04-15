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

package mva2.adapter.testconfig;

import android.view.View;
import android.view.ViewGroup;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class HeaderBinder extends ItemBinder<Header, HeaderBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(parent);
  }

  @Override public void bindViewHolder(ViewHolder holder, Header item) {
    // No-op
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Header;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  public static class ViewHolder extends ItemViewHolder<Header> {

    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
