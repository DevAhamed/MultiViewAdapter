package com.ahamed.multiviewadapter.listener;

import com.ahamed.multiviewadapter.annotation.SelectionMode;

/**
 * Listener to listen for changes if an item is selected or un-selected. This can be used if the
 * adapter has the selection mode as "SELECTION_MODE_SINGLE"
 *
 * @param <M> Refers to the model class
 * @see SelectionMode annotation for possible values
 */
public interface ItemSelectionChangedListener<M> {
  void onItemSelectionChangedListener(M item, boolean isSelected);
}