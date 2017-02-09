package com.ahamed.sample.common.model;

public class ItemOne implements BaseModel {

  private int id;
  private String data;

  public ItemOne(int id, String data) {
    this.id = id;
    this.data = data;
  }

  public int getId() {
    return id;
  }

  public String getData() {
    return data;
  }
}
