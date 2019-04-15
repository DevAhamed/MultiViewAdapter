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

package dev.ahamed.mva.sample.data.model;

public class SelectableItem {

  private final int iconResource;
  private final int color;
  private final String text;

  public SelectableItem(int iconResource, int color, String text) {
    this.iconResource = iconResource;
    this.color = color;
    this.text = text;
  }

  public int getColor() {
    return color;
  }

  public int getIconResource() {
    return iconResource;
  }

  public String getText() {
    return text;
  }
}
