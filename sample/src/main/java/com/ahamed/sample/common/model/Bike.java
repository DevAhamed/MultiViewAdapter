package com.ahamed.sample.common.model;

public class Bike extends Vehicle {

  private int bikeId;
  private String bikeName;
  private String description;

  public Bike(int bikeId, String bikeName, String description) {
    this.bikeId = bikeId;
    this.bikeName = bikeName;
    this.description = description;
  }

  public String getBikeName() {
    return bikeName;
  }

  public String getDescription() {
    return description;
  }
}