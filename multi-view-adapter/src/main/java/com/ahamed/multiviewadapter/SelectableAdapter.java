package com.ahamed.multiviewadapter;

import com.ahamed.multiviewadapter.annotation.SelectionMode;

public class SelectableAdapter extends RecyclerAdapter {

  public static final int SELECTION_MODE_NONE = -1;
  public static final int SELECTION_MODE_SINGLE = 1;
  public static final int SELECTION_MODE_SINGLE_OR_NONE = 2;
  public static final int SELECTION_MODE_MULTIPLE = 3;

  private int lastSelectedIndex = -1;
  private int selectionMode = SELECTION_MODE_NONE;

  @Override void onItemSelectionToggled(int adapterPosition) {
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
        getDataManager(adapterPosition).onItemSelectionToggled(
            getItemPositionInManager(adapterPosition), lastSelectedIndex != adapterPosition);
        lastSelectedIndex = lastSelectedIndex != adapterPosition ? adapterPosition : -1;
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

  void setLastSelectedIndex(int index) {
    lastSelectedIndex = index;
  }

  @SelectionMode int getSelectionMode() {
    return selectionMode;
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
  public final void setSelectionMode(@SelectionMode int selectionMode) {
    this.selectionMode = selectionMode;
  }
}
