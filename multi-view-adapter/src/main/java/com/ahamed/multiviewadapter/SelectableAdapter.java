package com.ahamed.multiviewadapter;

import android.support.annotation.IntDef;
import android.util.SparseBooleanArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SelectableAdapter extends RecyclerListAdapter
    implements SelectableViewHolder.OnItemSelectedListener {

  public static final int SELECTION_MODE_NONE = -1;
  public static final int SELECTION_MODE_SINGLE = 1;
  public static final int SELECTION_MODE_SINGLE_OR_NONE = 2;
  public static final int SELECTION_MODE_MULTIPLE = 3;

  private int lastSelectedPosition = -1;
  @SelectionMode private int selectionMode = SELECTION_MODE_NONE;
  private SparseBooleanArray selectedItemIndexes = new SparseBooleanArray();

  @Override public void onItemSelected(int position) {
    switch (selectionMode) {
      case SELECTION_MODE_SINGLE:
        if (lastSelectedPosition == position) {
          return;
        }
        if (lastSelectedPosition != -1) {
          selectedItemIndexes.put(lastSelectedPosition, false);
          getDataManager(lastSelectedPosition).onItemSelectionToggled(
              getItemPositionInManager(lastSelectedPosition), false);
        }
        selectedItemIndexes.put(position, true);
        getDataManager(position).onItemSelectionToggled(
            getItemPositionInManager(lastSelectedPosition), true);
        lastSelectedPosition = position;
        break;
      case SELECTION_MODE_SINGLE_OR_NONE:
        if (lastSelectedPosition != -1) {
          selectedItemIndexes.put(lastSelectedPosition, false);
          getDataManager(lastSelectedPosition).onItemSelectionToggled(
              getItemPositionInManager(lastSelectedPosition), false);
        }
        if (lastSelectedPosition != position) {
          selectedItemIndexes.put(position, true);
          getDataManager(position).onItemSelectionToggled(
              getItemPositionInManager(lastSelectedPosition), true);
          lastSelectedPosition = position;
        }
        break;
      case SELECTION_MODE_MULTIPLE:
        selectedItemIndexes.put(position, true);
        getDataManager(position).onItemSelectionToggled(getItemPositionInManager(position),
            !selectedItemIndexes.get(position));
        break;
      case SELECTION_MODE_NONE:
      default:
        break;
    }
  }

  @Override boolean isItemSelected(int adapterPosition) {
    return selectedItemIndexes.get(adapterPosition);
  }

  @Override void addBinder(ItemBinder binder) {
    if (binder instanceof SelectableBinder) {
      ((SelectableBinder) binder).setListener(this);
    }
    super.addBinder(binder);
  }

  public void setSelectionMode(int selectionMode) {
    this.selectionMode = selectionMode;
  }

  @Retention(RetentionPolicy.SOURCE) @IntDef({
      SELECTION_MODE_NONE, SELECTION_MODE_SINGLE, SELECTION_MODE_SINGLE_OR_NONE,
      SELECTION_MODE_MULTIPLE
  }) @interface SelectionMode {
  }
}
