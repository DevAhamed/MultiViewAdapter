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

public class Header implements BaseModel {

  private String headerInfo;
  private boolean isShuffleEnabled;

  public Header(String headerInfo) {
    this.headerInfo = headerInfo;
  }

  public Header(String headerInfo, boolean isShuffleEnabled) {
    this.headerInfo = headerInfo;
    this.isShuffleEnabled = isShuffleEnabled;
  }

  public String getHeaderInfo() {
    return headerInfo;
  }

  public boolean isShuffleEnabled() {
    return isShuffleEnabled;
  }
}
