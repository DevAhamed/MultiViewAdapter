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

import java.util.Objects;

public class NewsItem {

  private final int id;
  private final String source;
  private final int sourceLogo;
  private final int sourceColor;

  private final int thumbNailId;
  private final int thumbNailColor;

  private final String title;
  private final String time;
  private final boolean isOffline;

  public NewsItem(int id, String source, int sourceLogo, int sourceColor, int thumbNailId,
      int thumbNailColor, String title, String time, boolean isOffline) {
    this.id = id;
    this.source = source;
    this.sourceLogo = sourceLogo;
    this.sourceColor = sourceColor;
    this.thumbNailId = thumbNailId;
    this.thumbNailColor = thumbNailColor;
    this.title = title;
    this.time = time;
    this.isOffline = isOffline;
  }

  public int getId() {
    return id;
  }

  public String getSource() {
    return source;
  }

  public int getSourceColor() {
    return sourceColor;
  }

  public int getSourceLogo() {
    return sourceLogo;
  }

  public int getThumbNailColor() {
    return thumbNailColor;
  }

  public int getThumbNailId() {
    return thumbNailId;
  }

  public String getTime() {
    return time;
  }

  public String getTitle() {
    return title;
  }

  public boolean isOffline() {
    return isOffline;
  }

  @Override public int hashCode() {
    return Objects.hash(getId(), getSource(), getSourceLogo(), getSourceColor(), getThumbNailId(),
        getThumbNailColor(), getTitle(), getTime(), isOffline());
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NewsItem)) return false;
    NewsItem newsItem = (NewsItem) o;
    return getId() == newsItem.getId()
        && getSourceLogo() == newsItem.getSourceLogo()
        && getSourceColor() == newsItem.getSourceColor()
        && getThumbNailId() == newsItem.getThumbNailId()
        && getThumbNailColor() == newsItem.getThumbNailColor()
        && isOffline() == newsItem.isOffline()
        && Objects.equals(getSource(), newsItem.getSource())
        && Objects.equals(getTitle(), newsItem.getTitle())
        && Objects.equals(getTime(), newsItem.getTime());
  }
}
