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
import dev.ahamed.mva.sample.view.SampleActivity;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.decorator.Decorator;

public class ThreadDecorator extends Decorator {

  private Paint paint = new Paint();
  private Rect bounds = new Rect();

  ThreadDecorator(MultiViewAdapter adapter, Context context) {
    super(adapter);
    paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    addToRect(outRect, SampleActivity.EIGHT_DP, 0, 0, 0);
  }

  @Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state, View child, int adapterPosition) {
    canvas.save();
    int decoratedWidth = parent.getLayoutManager().getLeftDecorationWidth(child);
    int level = decoratedWidth / SampleActivity.EIGHT_DP;

    parent.getDecoratedBoundsWithMargins(child, bounds);
    for (int i = 0; i < level; i++) {
      final int left = (i + 1) * SampleActivity.EIGHT_DP;
      canvas.drawRect(left - SampleActivity.TWO_DP, bounds.top, left, bounds.bottom, paint);
    }
    canvas.restore();
  }
}
