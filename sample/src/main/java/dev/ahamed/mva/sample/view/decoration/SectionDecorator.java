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
import mva3.adapter.decorator.SectionPositionType;

import static mva3.adapter.decorator.SectionPositionType.FIRST;
import static mva3.adapter.decorator.SectionPositionType.LAST;

public class SectionDecorator extends Decorator {

  private final Rect mBounds = new Rect();
  private final int offsetInPixels;
  private Paint dividerPaint = new Paint();

  SectionDecorator(MultiViewAdapter adapter, Context context, int dp) {
    super(adapter);
    int color = ContextCompat.getColor(context, R.color.color_divider);
    dividerPaint.setColor(color);
    offsetInPixels = dp;
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    SectionPositionType sectionPositionType = getSectionPositionType(adapterPosition);
    int positionType = getPositionType(adapterPosition, parent);
    if (!(sectionPositionType == FIRST && isFirst(positionType)) && !isLast(positionType)) {
      return;
    }

    canvas.save();
    final int left;
    final int right;
    if (parent.getClipToPadding()) {
      left = parent.getPaddingLeft();
      right = parent.getWidth() - parent.getPaddingRight();
      canvas.clipRect(left, parent.getPaddingTop(), right,
          parent.getHeight() - parent.getPaddingBottom());
    } else {
      left = 0;
      right = parent.getWidth();
    }
    parent.getDecoratedBoundsWithMargins(child, mBounds);

    if (isLast(positionType)) {
      final int bottom = mBounds.bottom + Math.round(child.getTranslationY()) - 1;
      final int top = bottom - offsetInPixels + 1;

      canvas.drawLine(left, top, right, top, dividerPaint);
      if (getSectionPositionType(adapterPosition) != LAST) {
        canvas.drawLine(left, bottom, right, bottom, dividerPaint);
      }
    } else {
      final int top = mBounds.top;
      final int bottom = top + offsetInPixels - 1;

      canvas.drawLine(left, bottom, right, bottom, dividerPaint);
    }
    canvas.restore();
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    SectionPositionType sectionPositionType = getSectionPositionType(adapterPosition);
    int positionType = getPositionType(adapterPosition, parent);
    if (!(sectionPositionType == FIRST && isFirst(positionType)) && !isLast(positionType)) {
      return;
    }
    if (isLast(positionType)) {
      addToRect(outRect, 0, 0, 0, offsetInPixels);
    } else {
      addToRect(outRect, 0, offsetInPixels, 0, 0);
    }
  }
}
