package dev.ahamed.mva.sample.view.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import dev.ahamed.mva.sample.R;
import mva3.adapter.MultiViewAdapter;
import mva3.adapter.decorator.Decorator;

public class DividerDecorator extends Decorator {

  private final Rect mBounds = new Rect();
  private Paint dividerPaint = new Paint();
  private final int paddingLeft;

  DividerDecorator(MultiViewAdapter adapter, Context context) {
    this(adapter, context, true, 0);
  }

  DividerDecorator(MultiViewAdapter adapter, Context context, boolean isSubtle, int paddingLeft) {
    super(adapter);
    int color = ContextCompat.getColor(context, isSubtle ? R.color.grey_300 : R.color.grey_500);
    dividerPaint.setColor(color);
    this.paddingLeft = paddingLeft;
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    // No-op
  }

  @Override public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    int itemPositionType = getPositionType(adapterPosition, parent);
    if (isLast(itemPositionType)) {
      return;
    }
    canvas.save();
    int left;
    int right;
    if (parent.getClipToPadding()) {
      left = parent.getPaddingLeft();
      right = parent.getWidth() - parent.getPaddingRight();
      canvas.clipRect(left, parent.getPaddingTop(), right,
          parent.getHeight() - parent.getPaddingBottom());
    } else {
      left = 0;
      right = parent.getWidth();
    }

    left += paddingLeft;

    parent.getDecoratedBoundsWithMargins(child, mBounds);
    final int bottom = mBounds.bottom + Math.round(child.getTranslationY());

    canvas.drawRect(left, bottom - 2, right, bottom, dividerPaint);
    canvas.restore();
  }
}
