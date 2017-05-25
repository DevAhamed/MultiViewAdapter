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

package com.ahamed.sample.common.model;

public class Article {

  private int id;
  private String title;
  private String category;
  private String lastUpdated;
  private boolean isFeatured;
  private int coverImageId;
  private int categoryColor;

  public Article(int id, String title, String category, String lastUpdated, boolean isFeatured,
      int coverImageId, int categoryColor) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.lastUpdated = lastUpdated;
    this.isFeatured = isFeatured;
    this.coverImageId = coverImageId;
    this.categoryColor = categoryColor;
  }

  public String getTitle() {
    return title;
  }

  public String getCategory() {
    return category;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public boolean isFeatured() {
    return isFeatured;
  }

  public int getCoverImageId() {
    return coverImageId;
  }

  public int getCategoryColor() {
    return categoryColor;
  }
}
