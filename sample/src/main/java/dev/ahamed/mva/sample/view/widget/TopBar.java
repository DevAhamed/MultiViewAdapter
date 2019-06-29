package dev.ahamed.mva.sample.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class TopBar extends FrameLayout {

  public TopBar(Context context) {
    super(context);
    setInsetsListener();
  }

  public TopBar(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setInsetsListener();
  }

  public TopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setInsetsListener();
  }

  public void setInsetsListener() {
    setOnApplyWindowInsetsListener((v, insets) -> {
      v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
          insets.getSystemWindowInsetRight(), 0);
      return insets;
    });
  }
}
