/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
