package dev.ahamed.mva.sample.view.nested;

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

public class CommentDecorator extends Decorator {

  private Paint paint = new Paint();
  private Rect bounds = new Rect();

  CommentDecorator(MultiViewAdapter adapter) {
    super(adapter);
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    addToRect(outRect, SampleActivity.FOUR_DP, 0, 0, 0);
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    canvas.save();
    int decoratedWidth = parent.getLayoutManager().getLeftDecorationWidth(child);
    paint.setColor(getColor(decoratedWidth));

    parent.getDecoratedBoundsWithMargins(child, bounds);
    final int left = decoratedWidth - SampleActivity.FOUR_DP;
    canvas.drawRect(left, bounds.top, decoratedWidth, bounds.bottom, paint);
    canvas.restore();
  }

  private int getColor(int decoratedWidth) {
    switch (decoratedWidth / SampleActivity.FOUR_DP) {
      case 1:
        return Color.parseColor("#33B5E5");
      case 2:
        return Color.parseColor("#B39DDB");
      case 3:
        return Color.parseColor("#FFCC80");
      case 4:
        return Color.parseColor("#EF9A9A");
      case 5:
        return Color.parseColor("#8596a8");
      case 6:
        return Color.parseColor("#a0a0a0");
      case 7:
        return Color.parseColor("#707070");
      default:
        return Color.parseColor("#303030");
    }
  }
}
