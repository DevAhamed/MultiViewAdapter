package com.ahamed.sample.common.model;

public class Car extends Vehicle {

  private long carId;
  private String modelName;
  private String make;
  private String year;

  public Car(long carId, String modelName, String make, String year) {
    this.carId = carId;
    this.modelName = modelName;
    this.make = make;
    this.year = year;
  }

  public String getModelName() {
    return modelName;
  }

  public String getMake() {
    return make;
  }

  public String getYear() {
    return year;
  }
}
