package com.ahamed.sample.common.model;

public class Flower {

  private int flowerId;
  private String flowerName;

  public Flower(int flowerId, String flowerName) {
    this.flowerId = flowerId;
    this.flowerName = flowerName;
  }

  public String getFlowerName() {
    return flowerName;
  }
}
