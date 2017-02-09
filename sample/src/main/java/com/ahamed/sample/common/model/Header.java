package com.ahamed.sample.common.model;

public class Header implements BaseModel {

  private String headerInfo;

  public Header(String headerInfo) {
    this.headerInfo = headerInfo;
  }

  public String getHeaderInfo() {
    return headerInfo;
  }
}
