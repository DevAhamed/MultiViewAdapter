package com.ahamed.sample.common.model;

public class Article {

  private int id;
  private String title;
  private String category;
  private String lastUpdated;
  private boolean isFeatured;
  private int coverImageId;
  private int categoryColor;

  public Article(int id, String title, String category, String lastUpdated, boolean isFeatured,
      int coverImageId, int categoryColor) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.lastUpdated = lastUpdated;
    this.isFeatured = isFeatured;
    this.coverImageId = coverImageId;
    this.categoryColor = categoryColor;
  }

  public String getTitle() {
    return title;
  }

  public String getCategory() {
    return category;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public boolean isFeatured() {
    return isFeatured;
  }

  public int getCoverImageId() {
    return coverImageId;
  }

  public int getCategoryColor() {
    return categoryColor;
  }
}
