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
