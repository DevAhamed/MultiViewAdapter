package mva3.extension.decorator;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import mva3.adapter.MultiViewAdapter;
import mva3.adapter.decorator.Decorator;

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
