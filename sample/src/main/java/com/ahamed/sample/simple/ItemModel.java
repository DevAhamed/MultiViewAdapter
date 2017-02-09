package com.ahamed.sample.simple;

class ItemModel {

  private int id;
  private String data;

  ItemModel(int id, String data) {
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
