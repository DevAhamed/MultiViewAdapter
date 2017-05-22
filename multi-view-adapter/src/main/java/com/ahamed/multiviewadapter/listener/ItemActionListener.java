package com.ahamed.multiviewadapter.listener;

public interface ItemActionListener {

  void onItemSelectionToggled(int position);

  void onItemExpansionToggled(int position);

  void onGroupExpansionToggled(int position);

  boolean isItemSelected(int position);

  boolean isItemExpanded(int adapterPosition);
}
