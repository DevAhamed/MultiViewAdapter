package com.ahamed.sample.common.model;

public class Advertisement {

  private int id;
  private String adDescription;
  private String adSecondaryText;
  private String url;

  public Advertisement(int id, String adDescription, String adSecondaryText, String url) {
    this.id = id;
    this.adDescription = adDescription;
    this.adSecondaryText = adSecondaryText;
    this.url = url;
  }

  public String getAdDescription() {
    return adDescription;
  }

  public String getAdSecondaryText() {
    return adSecondaryText;
  }

  public String getUrl() {
    return url;
  }
}
