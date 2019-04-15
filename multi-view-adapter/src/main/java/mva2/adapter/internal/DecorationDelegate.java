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

package mva2.adapter.internal;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import mva2.adapter.ItemBinder;
import mva2.adapter.MultiViewAdapter;

/**
 * This is an internal class. Should not be extended by client code. Used to manage the different
 * {@link RecyclerView.ItemDecoration} for different {@link ItemBinder}. It will delegate the {@link
 * RecyclerView.ItemDecoration}
 */
public class DecorationDelegate extends RecyclerView.ItemDecoration {

  private final MultiViewAdapter adapter;

  public DecorationDelegate(MultiViewAdapter adapter) {
    this.adapter = adapter;
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);
      int adapterPosition = parent.getChildAdapterPosition(child);
      if (adapterPosition < 0) {
        return;
      }
      adapter.drawDecoration(canvas, parent, state, child, adapterPosition);
    }
  }

  @Override public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    // TODO Implement onDrawOver
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    int adapterPosition = parent.getChildAdapterPosition(view);
    if (adapterPosition < 0) {
      return;
    }
    adapter.getDecorationOffset(outRect, view, parent, state, adapterPosition);
  }
}
