package dev.ahamed.mva.sample.view.basic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import dev.ahamed.mva.sample.view.SampleActivity;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.decorator.Decorator;

public class BasicDividerDecorator extends Decorator {

  private final Rect mBounds = new Rect();
  private Paint dividerPaint = new Paint();

  public BasicDividerDecorator(MultiViewAdapter adapter) {
    super(adapter);
    dividerPaint.setColor(Color.parseColor("#66606060"));
    dividerPaint.setStrokeWidth(SampleActivity.DP);
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    int positionType = getPositionType(adapterPosition, parent);

    if (!isItemOnBottomEdge(positionType)) {
      addToRect(outRect, 0, 0, 0, (int) SampleActivity.DP);
    }
    //if (!isItemOnLeftEdge(positionType)) {
    //  addToRect(outRect, SampleActivity.DP, 0, 0, 0);
    //}
    if (!isItemOnRightEdge(positionType)) {
      addToRect(outRect, 0, 0, (int) SampleActivity.DP, 0);
    }
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    int positionType = getPositionType(adapterPosition, parent);

    canvas.save();

    parent.getDecoratedBoundsWithMargins(child, mBounds);

    final int left = mBounds.left;
    final int right = mBounds.right;
    final int top = mBounds.top;
    final int bottom = mBounds.bottom;

    if (!isItemOnBottomEdge(positionType)) {
      canvas.drawRect(left, bottom - SampleActivity.DP, right, bottom, dividerPaint);
    }
    if (!isItemOnRightEdge(positionType)) {
      canvas.drawRect(right - SampleActivity.DP, top, right, bottom, dividerPaint);
    }
    canvas.restore();
  }
}
