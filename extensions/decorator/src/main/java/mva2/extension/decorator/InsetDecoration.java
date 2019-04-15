package mva2.extension.decorator;

import android.graphics.Rect;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.MultiViewAdapter;
import mva2.adapter.decorator.Decorator;

public class InsetDecoration extends Decorator {

  private final int insetSpace;

  public InsetDecoration(MultiViewAdapter adapter, int insetSpace) {
    super(adapter);
    this.insetSpace = insetSpace;
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    int itemPositionType = getPositionType(adapterPosition, parent);
    addToRect(outRect, insetSpace, insetSpace, insetSpace, insetSpace);

    if (isItemOnLeftEdge(itemPositionType)) {
      outRect.left += insetSpace;
    }
    if (isItemOnRightEdge(itemPositionType)) {
      outRect.right += insetSpace;
    }
    if (isItemOnTopEdge(itemPositionType)) {
      outRect.top += insetSpace;
    }
    if (isItemOnBottomEdge(itemPositionType)) {
      outRect.bottom += insetSpace;
    }
  }
}
