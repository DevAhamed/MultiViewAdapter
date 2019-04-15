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

package mva2.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import mva2.adapter.decorator.PositionType;
import mva2.adapter.decorator.SectionPositionType;
import mva2.adapter.internal.Notifier;
import mva2.adapter.internal.RecyclerItem;
import mva2.adapter.util.Mode;

import static mva2.adapter.util.Mode.MULTIPLE;

/**
 * NestedSection is a section which can host multiple other section.
 *
 * <p/>
 *
 * When you need a group of section to have different behaviour than parent, then you can put those
 * section inside the NestedSection and set the desired behaviour to the NestedSection. For example,
 * if the parent has selectionMode as MULTIPLE and for a group of sections you want them to have
 * SINGLE as the selection mode, you can put those group of section inside the NestedSection and set
 * the selection mode for the NestedSection as SINGLE.
 */
public class NestedSection extends Section implements Notifier {

  final List<Section> sections = new ArrayList<>();
  private int count = -1;

  /**
   * Add a section to this NestedSection. You can add any section here, even a NestedSection.
   *
   * @param section Section to be added in this NestedSection.
   */
  public void addSection(Section section) {
    section.setNotifier(this);
    sections.add(section);
    section.onInserted(0, section.getCount());
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void onItemClicked(int itemPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        section.onItemClicked(itemPosition);
        return;
      }
    }
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void notifySectionItemMoved(Section section, int fromPosition, int toPosition) {
    onMoved(getAdapterPosition(section, fromPosition), getAdapterPosition(section, toPosition));
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void notifySectionRangeChanged(Section section, int positionStart, int itemCount,
      Object payload) {
    onChanged(getAdapterPosition(section, positionStart), itemCount, payload);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void notifySectionRangeInserted(Section section, int positionStart, int itemCount) {
    onInserted(getAdapterPosition(section, positionStart), itemCount);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override
  public final void notifySectionRangeRemoved(Section section, int positionStart, int itemCount) {
    onRemoved(getAdapterPosition(section, positionStart), itemCount);
  }

  @Override int getMaxSpanCount(int itemPosition, int spanCount) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        return section.getMaxSpanCount(itemPosition,
            super.getMaxSpanCount(itemPosition, spanCount));
      }
    }
    return spanCount;
  }

  @Override void collapseSection() {
    for (Section section : sections) {
      section.collapseSection();
    }
  }

  @Override boolean isSectionExpanded(int itemPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        return section.isSectionExpanded(itemPosition);
      }
    }
    return false;
  }

  @Override SectionPositionType getSectionPositionType(int adapterPosition, int sectionPosition,
      int size) {
    SectionPositionType parentSectionPositionType =
        super.getSectionPositionType(adapterPosition, sectionPosition, size);
    if (parentSectionPositionType == SectionPositionType.MIDDLE) {
      return parentSectionPositionType;
    }
    SectionPositionType currentSectionPositionType = getSectionPositionType(adapterPosition);
    if (parentSectionPositionType == currentSectionPositionType) {
      return currentSectionPositionType;
    } else {
      return SectionPositionType.MIDDLE;
    }
  }

  @Override void drawDecoration(int itemPosition, @NonNull Canvas canvas,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, View child,
      int adapterPosition) {
    super.drawDecoration(itemPosition, canvas, parent, state, child, adapterPosition);
    drawChildSectionDecoration(itemPosition, canvas, parent, state, child, adapterPosition);
  }

  @Override void getDecorationOffsets(int itemPosition, @NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    super.getDecorationOffsets(itemPosition, outRect, view, parent, state, adapterPosition);
    getChildSectionOffsets(itemPosition, outRect, view, parent, state, adapterPosition);
  }

  @Override void onDataSetChanged() {
    super.onDataSetChanged();
    count = -1;
  }

  @Override Object getItem(int itemPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        return section.getItem(itemPosition);
      }
    }
    throw new IllegalStateException();
  }

  @Override int getCount() {
    if (count == -1) {
      if (isSectionVisible()) {
        int itemCount = 0;
        for (Section section : sections) {
          itemCount += section.getCount();
        }
        count = itemCount;
      } else {
        count = 0;
      }
    }
    return count;
  }

  @Override boolean isItemSelected(int itemPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        return section.isItemSelected(itemPosition);
      }
    }
    return false;
  }

  @Override void onItemSelectionToggled(int itemPosition, @NonNull Mode selectionMode) {
    Mode selectionModeToHonor = getModeToHonor(selectionMode, this.selectionMode);
    for (Section section : sections) {
      section.onItemSelectionToggled(itemPosition, selectionModeToHonor);
      itemPosition -= section.getCount();
      if (itemPosition < 0 && selectionModeToHonor == MULTIPLE) {
        break;
      }
    }
  }

  @Override void clearAllSelections() {
    for (Section section : sections) {
      section.clearAllSelections();
    }
  }

  @Override boolean isItemExpanded(int itemPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        return section.isItemExpanded(itemPosition);
      }
    }
    return false;
  }

  @Override void onItemExpansionToggled(int itemPosition, @NonNull Mode expansionMode) {
    Mode expansionModeToHonor = getModeToHonor(expansionMode, this.expansionMode);
    for (Section section : sections) {
      section.onItemExpansionToggled(itemPosition, expansionModeToHonor);
      itemPosition -= section.getCount();
      if (itemPosition < 0 && expansionModeToHonor == MULTIPLE) {
        break;
      }
    }
  }

  @Override void collapseAllItems() {
    for (Section section : sections) {
      section.collapseAllItems();
    }
  }

  @Override int onSectionExpansionToggled(int itemPosition, @NonNull Mode sectionExpansionMode) {
    Mode mode = getModeToHonor(sectionExpansionMode, this.sectionExpansionMode);
    if (itemPosition < getCount() && itemPosition >= 0) {
      onChildSectionExpansionToggled(itemPosition, mode);
    }
    return itemPosition - getCount();
  }

  @Override int getPositionType(int itemPosition, int adapterPosition,
      LayoutManager layoutManager) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        return section.getPositionType(itemPosition, adapterPosition, layoutManager);
      }
    }
    return PositionType.MIDDLE;
  }

  @Override void onItemDismiss(int itemPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        section.onItemDismiss(itemPosition);
        return;
      }
    }
  }

  @Override boolean move(int initialPosition, int targetOffset) {
    int currentPosition = initialPosition;
    int targetPosition = initialPosition + targetOffset;

    for (Section section : sections) {
      if (currentPosition >= section.getCount()) {
        currentPosition -= section.getCount();
      } else {
        if (currentPosition + targetOffset <= section.getCount()
            && currentPosition + targetOffset > 0) {
          return section.move(currentPosition, targetOffset);
        }
        break;
      }
    }

    // Item is moved down between different sections

    RecyclerItem itemToMove = null;
    currentPosition = initialPosition;

    for (Section section : sections) {
      if (currentPosition >= section.getCount()) {
        currentPosition -= section.getCount();
      } else {
        itemToMove = section.startMovingItem(currentPosition);
        break;
      }
    }

    if (null == itemToMove) {
      return false;
    }

    targetPosition -= initialPosition < targetPosition ? 1 : 0;

    for (Section section : sections) {
      if (targetPosition >= section.getCount()) {
        targetPosition -= section.getCount();
      } else {
        section.finishMovingItem(targetPosition, itemToMove);
        return true;
      }
    }
    return false;
  }

  @Override RecyclerItem startMovingItem(int itemPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        onDataSetChanged();
        return section.startMovingItem(itemPosition);
      }
    }
    throw new IllegalStateException();
  }

  @Override void finishMovingItem(int currentPosition, RecyclerItem itemToMove) {
    for (Section section : sections) {
      if (currentPosition >= section.getCount()) {
        currentPosition -= section.getCount();
      } else {
        onDataSetChanged();
        section.finishMovingItem(currentPosition, itemToMove);
      }
    }
  }

  SectionPositionType getSectionPositionType(int itemPosition) {
    int sectionPosition = 0;
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        return section.getSectionPositionType(itemPosition, sectionPosition, sections.size());
      }
      sectionPosition++;
    }
    return SectionPositionType.MIDDLE;
  }

  void removeAllSections() {
    for (Section section : sections) {
      section.setNotifier(null);
    }
    sections.clear();
    count = -1;
  }

  void onChildSectionExpansionToggled(int itemPosition, @NonNull Mode expansionMode) {
    for (Section section : sections) {
      itemPosition = section.onSectionExpansionToggled(itemPosition, expansionMode);
      if (itemPosition < 0 && expansionMode == MULTIPLE) {
        return;
      }
    }
  }

  void collapseAllSections() {
    for (Section section : sections) {
      section.collapseSection();
    }
  }

  void drawChildSectionDecoration(int itemPosition, @NonNull Canvas canvas,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, View child,
      int adapterPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        section.drawDecoration(itemPosition, canvas, parent, state, child, adapterPosition);
        break;
      }
    }
  }

  void getChildSectionOffsets(int itemPosition, @NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    for (Section section : sections) {
      if (itemPosition >= section.getCount()) {
        itemPosition -= section.getCount();
      } else {
        section.getDecorationOffsets(itemPosition, outRect, view, parent, state, adapterPosition);
        break;
      }
    }
  }

  private int getAdapterPosition(Section section, int position) {
    int sectionIndex = sections.indexOf(section);
    if (sectionIndex < 0) {
      throw new IllegalStateException("Section does not exist in parent!");
    }

    for (int i = 0; i < sectionIndex; i++) {
      position += sections.get(i).getCount();
    }
    return position;
  }
}
