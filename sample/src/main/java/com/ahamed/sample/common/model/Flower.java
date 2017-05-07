package com.ahamed.sample.common.model;

public class Flower {

  private int flowerId;
  private String flowerName;

  public Flower(int flowerId, String flowerName) {
    this.flowerId = flowerId;
    this.flowerName = flowerName;
  }

  public int getFlowerId() {
    return flowerId;
  }

  public String getFlowerName() {
    return flowerName;
  }
}
