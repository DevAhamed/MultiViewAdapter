package dev.ahamed.mva.sample.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;

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
      v.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);
      return insets;
    });
  }
}
