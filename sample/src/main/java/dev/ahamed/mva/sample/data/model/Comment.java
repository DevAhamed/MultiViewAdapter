package dev.ahamed.mva.sample.data.model;

import java.util.List;

public class Comment {

  private final String author;
  private final String comment;
  private final String postedTime;
  private final List<Comment> childComments;

  public Comment(String author, String comment, String postedTime, List<Comment> childComments) {
    this.author = author;
    this.comment = comment;
    this.postedTime = postedTime;
    this.childComments = childComments;
  }

  public String getAuthor() {
    return author;
  }

  public List<Comment> getChildComments() {
    return childComments;
  }

  public String getComment() {
    return comment;
  }

  public String getPostedTime() {
    return postedTime;
  }
}
