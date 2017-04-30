package com.ahamed.multiviewadapter.listener;

import java.util.List;

public interface MultiSelectionChangedListener<M> {
  void onMultiSelectionChangedListener(List<M> selectedItems);
}
