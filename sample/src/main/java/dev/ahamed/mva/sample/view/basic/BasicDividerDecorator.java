package dev.ahamed.mva.sample.view.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.view.SampleActivity;
import mva3.adapter.MultiViewAdapter;
import mva3.adapter.decorator.Decorator;

public class BasicDividerDecorator extends Decorator {

  private final Rect mBounds = new Rect();
  private Paint dividerPaint = new Paint();

  BasicDividerDecorator(MultiViewAdapter adapter, Context context) {
    super(adapter);
    dividerPaint.setColor(ContextCompat.getColor(context, R.color.grey_500));
    dividerPaint.setStrokeWidth(SampleActivity.TWO_DP);
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    int positionType = getPositionType(adapterPosition, parent);

    if (!isItemOnBottomEdge(positionType)) {
      addToRect(outRect, 0, 0, 0, SampleActivity.TWO_DP);
    }
    if (!isItemOnRightEdge(positionType)) {
      addToRect(outRect, 0, 0, SampleActivity.TWO_DP, 0);
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
      canvas.drawRect(left, bottom - SampleActivity.TWO_DP, right, bottom, dividerPaint);
    }
    if (!isItemOnRightEdge(positionType)) {
      canvas.drawRect(right - SampleActivity.TWO_DP, top, right, bottom, dividerPaint);
    }
    canvas.restore();
  }
}
