package dev.ahamed.mva.sample.data.model;

public class Person {

  private final String name;
  private final String initials;
  private final String overline;

  public Person(String name, String initials, String overline) {
    this.name = name;
    this.initials = initials;
    this.overline = overline;
  }

  public String getInitials() {
    return initials;
  }

  public String getName() {
    return name;
  }

  public String getOverline() {
    return overline;
  }
}
