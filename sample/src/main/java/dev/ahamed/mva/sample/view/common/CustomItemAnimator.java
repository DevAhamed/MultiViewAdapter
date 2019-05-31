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

package dev.ahamed.mva.sample.view.common;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.mikepenz.itemanimators.DefaultAnimator;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;

public class CustomItemAnimator extends DefaultAnimator<SlideDownAlphaAnimator> {

  private final int elevation;

  public CustomItemAnimator(int elevation) {
    this.elevation = elevation;
  }

  @Override public void addAnimationPrepare(RecyclerView.ViewHolder holder) {
    ViewCompat.setTranslationY(holder.itemView, -holder.itemView.getHeight());
    ViewCompat.setAlpha(holder.itemView, 0);
    ViewCompat.setElevation(holder.itemView, 0);
  }

  @Override public ViewPropertyAnimatorCompat addAnimation(RecyclerView.ViewHolder holder) {
    return ViewCompat.animate(holder.itemView)
        .translationY(0)
        .alpha(1)
        .setDuration(getMoveDuration())
        .setInterpolator(getInterpolator());
  }

  @Override public void addAnimationCleanup(RecyclerView.ViewHolder holder) {
    ViewCompat.setTranslationY(holder.itemView, 0);
    ViewCompat.setAlpha(holder.itemView, 1);
    ViewCompat.setElevation(holder.itemView, elevation);
  }

  @Override public long getAddDelay(long remove, long move, long change) {
    return 0;
  }

  @Override public long getRemoveDelay(long remove, long move, long change) {
    return remove / 2;
  }

  @Override public ViewPropertyAnimatorCompat removeAnimation(RecyclerView.ViewHolder holder) {
    ViewCompat.setElevation(holder.itemView, 0);
    final ViewPropertyAnimatorCompat animation = ViewCompat.animate(holder.itemView);
    return animation.setDuration(getRemoveDuration())
        .alpha(0)
        .translationY(-holder.itemView.getHeight())
        .setInterpolator(getInterpolator());
  }

  @Override public void removeAnimationCleanup(RecyclerView.ViewHolder holder) {
    ViewCompat.setTranslationY(holder.itemView, 0);
    ViewCompat.setAlpha(holder.itemView, 1);
    ViewCompat.setElevation(holder.itemView, elevation);
  }
}
