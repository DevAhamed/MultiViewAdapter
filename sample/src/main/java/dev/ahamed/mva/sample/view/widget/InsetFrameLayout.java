package dev.ahamed.mva.sample.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import dev.ahamed.mva.sample.R;

public class InsetFrameLayout extends FrameLayout {

  public InsetFrameLayout(Context context) {
    super(context);
    setInsetsListener(context);
  }

  public InsetFrameLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setInsetsListener(context);
  }

  public InsetFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setInsetsListener(context);
  }

  public void setInsetsListener(Context context) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
    int actionBarSize = (int) getResources().getDimension(typedValue.resourceId);
    setOnApplyWindowInsetsListener((v, insets) -> {
      v.setPadding(insets.getSystemWindowInsetLeft(), 0,
          insets.getSystemWindowInsetRight(), 0);
      return insets;
    });
  }
}
