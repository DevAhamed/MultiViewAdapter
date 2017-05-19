package com.ahamed.multiviewadapter;

interface ItemActionListener {

  void onItemSelectionToggled(int position);

  void onItemExpansionToggled(int position);

  void onGroupExpansionToggled(int position);

  boolean isItemSelected(int position);

  boolean isItemExpanded(int adapterPosition);
}
