package com.ahamed.multiviewadapter.listener;

import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY) public interface ItemActionListener {

  void onItemSelectionToggled(int position);

  void onItemExpansionToggled(int position);

  void onGroupExpansionToggled(int position);

  boolean isItemSelected(int position);

  boolean isItemExpanded(int adapterPosition);

  boolean isAdapterInContextMode();
}
