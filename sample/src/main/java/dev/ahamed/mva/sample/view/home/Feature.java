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

package dev.ahamed.mva.sample.view.home;

public class Feature {

  private final String title;
  private final int icon;
  private final String description;

  Feature(String title, int icon, String description) {
    this.title = title;
    this.icon = icon;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getIcon() {
    return icon;
  }

  public String getTitle() {
    return title;
  }
}
