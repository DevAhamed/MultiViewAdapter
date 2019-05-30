package dev.ahamed.mva.sample.view.nested;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import dev.ahamed.mva.sample.R;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.decorator.Decorator;

public class DividerDecorator extends Decorator {

  private Paint dividerPaint = new Paint();
  private Rect bounds = new Rect();

  DividerDecorator(MultiViewAdapter adapter, Context context) {
    super(adapter);
    dividerPaint.setColor(ContextCompat.getColor(context, R.color.grey_300));
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    // No-op
  }

  @Override public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    //if(isLast(getPositionType(adapterPosition, parent))) {
    //  return;
    //}
    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    if (null == layoutManager) {
      return;
    }
    canvas.save();
    int decoratedWidth = layoutManager.getLeftDecorationWidth(child);

    parent.getDecoratedBoundsWithMargins(child, bounds);
    canvas.drawRect(decoratedWidth, bounds.bottom - 2, bounds.right, bounds.bottom, dividerPaint);
    canvas.restore();
  }
}
