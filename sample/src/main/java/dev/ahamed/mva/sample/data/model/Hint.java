package dev.ahamed.mva.sample.data.model;

import androidx.annotation.StringRes;

public class Hint {

  private final int description;

  public Hint(@StringRes int description) {
    this.description = description;
  }

  public @StringRes int getDescription() {
    return description;
  }
}
