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

import android.graphics.Rect;
import java.util.Objects;

public class RectUtil {

  private final int left;
  private final int top;
  private final int right;
  private final int bottom;

  public RectUtil(Rect outRect) {
    this.left = outRect.left;
    this.top = outRect.top;
    this.right = outRect.right;
    this.bottom = outRect.bottom;
  }

  public RectUtil(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RectUtil)) return false;
    RectUtil rectUtil = (RectUtil) o;
    return left == rectUtil.left
        && top == rectUtil.top
        && right == rectUtil.right
        && bottom == rectUtil.bottom;
  }

  @Override public int hashCode() {
    return Objects.hash(left, top, right, bottom);
  }
}
