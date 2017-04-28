package com.ahamed.multiviewadapter;

import android.support.annotation.IntDef;
import android.widget.RadioGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SelectableAdapter extends RecyclerAdapter
    implements SelectableViewHolder.OnItemSelectedListener {

  public static final int SELECTION_MODE_NONE = -1;
  public static final int SELECTION_MODE_SINGLE = 1;
  public static final int SELECTION_MODE_SINGLE_OR_NONE = 2;
  public static final int SELECTION_MODE_MULTIPLE = 3;

  private int lastSelectedIndex = -1;
  @SelectionMode private int selectionMode = SELECTION_MODE_NONE;

  @Override public final void onItemSelected(int adapterPosition) {
    switch (selectionMode) {
      case SELECTION_MODE_SINGLE:
        if (lastSelectedIndex == adapterPosition) {
          return;
        }
        if (lastSelectedIndex != -1) {
          getDataManager(lastSelectedIndex).onItemSelectionToggled(
              getItemPositionInManager(lastSelectedIndex), false);
        }
        getDataManager(adapterPosition).onItemSelectionToggled(
            getItemPositionInManager(adapterPosition), true);
        lastSelectedIndex = adapterPosition;
        break;
      case SELECTION_MODE_SINGLE_OR_NONE:
        if (lastSelectedIndex != -1) {
          getDataManager(lastSelectedIndex).onItemSelectionToggled(
              getItemPositionInManager(lastSelectedIndex), false);
        }
        if (lastSelectedIndex != adapterPosition) {
          getDataManager(adapterPosition).onItemSelectionToggled(
              getItemPositionInManager(adapterPosition), true);
          lastSelectedIndex = adapterPosition;
        }
        break;
      case SELECTION_MODE_MULTIPLE:
        getDataManager(adapterPosition).onItemSelectionToggled(
            getItemPositionInManager(adapterPosition), !isItemSelected(adapterPosition));
        break;
      case SELECTION_MODE_NONE:
      default:
        break;
    }
  }

  @Override boolean isItemSelected(int adapterPosition) {
    return getDataManager(adapterPosition).isItemSelected(
        getItemPositionInManager(adapterPosition));
  }

  @Override void addBinder(ItemBinder binder) {
    if (binder instanceof SelectableBinder) {
      ((SelectableBinder) binder).setListener(this);
    }
    super.addBinder(binder);
  }

  void setLastSelectedIndex(int index) {
    lastSelectedIndex = index;
  }

  ////////////////////////////////////////
  ///////// Public Methods ///////////////
  ////////////////////////////////////////

  /**
   * To set the selection mode for the {@link SelectableAdapter}
   *
   * @param selectionMode The selection mode to be set
   * @see SelectionMode SelectionMode for possible values
   */
  public final void setSelectionMode(int selectionMode) {
    this.selectionMode = selectionMode;
  }

  /**
   * Represents the selection mode of the adapter.
   *
   * <p>Possible values : </p>
   * <li>SELECTION_MODE_NONE - Default value. No {@link DataListManager} is not selectable </li>
   * <li>SELECTION_MODE_SINGLE - Single selection. You cannot deselect the item without selecting
   * other. Similar to a {@link RadioGroup} </li>
   * <li>SELECTION_MODE_SINGLE_OR_NONE - Single selection. You can deselect the item </li>
   * <li>SELECTION_MODE_MULTIPLE - Multiple selection</li>
   */
  @Retention(RetentionPolicy.SOURCE) @IntDef({
      SELECTION_MODE_NONE, SELECTION_MODE_SINGLE, SELECTION_MODE_SINGLE_OR_NONE,
      SELECTION_MODE_MULTIPLE
  }) @interface SelectionMode {
  }
}
