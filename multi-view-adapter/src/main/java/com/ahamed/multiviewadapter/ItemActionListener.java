package com.ahamed.multiviewadapter;

interface ItemActionListener {

  void onItemSelectionToggled(int position);

  boolean isItemSelected(int position);
}
