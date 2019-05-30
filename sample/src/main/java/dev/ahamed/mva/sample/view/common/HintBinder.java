package dev.ahamed.mva.sample.view.common;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Hint;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class HintBinder extends ItemBinder<Hint, HintBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_hint));
  }

  @Override public void bindViewHolder(ViewHolder holder, Hint item) {
    holder.tvDescription.setText(item.getDescription());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Hint;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  public static class ViewHolder extends ItemViewHolder<Hint> {

    private final TextView tvDescription;

    public ViewHolder(View itemView) {
      super(itemView);
      tvDescription = itemView.findViewById(R.id.tv_hint_description);
    }

    @Override public int getSwipeDirections() {
      return ItemTouchHelper.START | ItemTouchHelper.END;
    }
  }
}
