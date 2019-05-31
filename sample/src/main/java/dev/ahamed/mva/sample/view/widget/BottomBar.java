package dev.ahamed.mva.sample.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;

public class BottomBar extends FrameLayout {

  public BottomBar(Context context) {
    super(context);
    setInsetsListener();
  }

  public BottomBar(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setInsetsListener();
  }

  public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setInsetsListener();
  }

  public void setInsetsListener() {
    setOnApplyWindowInsetsListener((v, insets) -> {
      v.setPadding(0, 0, 0, insets.getSystemWindowInsetBottom());
      return insets;
    });
  }
}
