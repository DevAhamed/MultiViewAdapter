package com.ahamed.sample.common.model;

public class Quote {

  private String author;
  private String quote;

  public Quote(String author, String quote) {
    this.author = author;
    this.quote = quote;
  }

  public String getAuthor() {
    return author;
  }

  public String getQuote() {
    return quote;
  }
}