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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import mva2.adapter.internal.Notifier;
import mva2.adapter.util.Mode;

import static mva2.adapter.decorator.PositionType.BOTTOM;
import static mva2.adapter.decorator.PositionType.TOP;

/**
 * Displays list of items along with a header and has collapsing/expanding feature.
 *
 * <p>
 *
 * Section to display list of items with a header. Internally it comprises of an ItemSection and
 * ListSection. HeaderSection can be used with GridLayoutManager or LinearLayoutManager
 *
 * <p>
 *
 * It provides the collapsing and expanding feature. If the section is collapsed the getCount() will
 * return 1. To collapse the section call {@link ItemViewHolder#toggleSectionExpansion()}.
 *
 * @param <H> The header item object
 * @param <M> The list item object
 *
 * @see MultiViewAdapter#setSectionExpansionMode(Mode)
 */
public class HeaderSection<H, M> extends NestedSection implements Notifier {

  private final ItemSection<H> itemSection;
  private final ListSection<M> listSection;

  /**
   * Initializes the HeaderSection. This header object is set as null which results in header not
   * being displayed. Call {@link HeaderSection#setHeader} to set the header.
   */
  @SuppressWarnings("WeakerAccess") public HeaderSection() {
    super();

    itemSection = new ItemSection<>();
    itemSection.setNotifier(this);

    listSection = new ListSection<>();
    listSection.setNotifier(this);

    this.addSection(itemSection);
    this.addSection(listSection);
  }

  /**
   * Initializes the HeaderSection with header object.
   *
   * @param header object to be set as header
   */
  public HeaderSection(@NonNull H header) {
    this();
    itemSection.setItem(header);
  }

  /**
   * Returns the header object. If the header is not set, null will be returned.
   *
   * @return Header object
   */
  public H getHeader() {
    return itemSection.getItem();
  }

  /**
   * Use this method to get the ListSection which is inside the HeaderSection. Use this ListSection
   * to set any data to the ListSection.
   *
   * @return ListSection which is added to the this HeaderSection
   */
  public ListSection<M> getListSection() {
    return listSection;
  }

  /**
   * Sets the header object. If null is being set, the item will not be displayed in the
   * recyclerview
   *
   * @param header object to be set as header
   */
  public void setHeader(@NonNull H header) {
    itemSection.setItem(header);
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @Override void collapseSection() {
    if (listSection.isSectionExpanded()) {
      listSection.setSectionExpanded(false);
      onRemoved(1, listSection.size());
      onChanged(0, 1, null);
    }
  }

  @Override boolean isSectionExpanded(int itemPosition) {
    return listSection.isSectionExpanded();
  }

  @Override int onSectionExpansionToggled(int itemPosition, @NonNull Mode sectionExpansionMode) {
    Mode mode = getModeToHonor(sectionExpansionMode, this.sectionExpansionMode);
    int prevCount = getCount();
    switch (mode) {
      case SINGLE:
        if (itemPosition < getCount() && itemPosition >= 0) {
          listSection.setSectionExpanded(!listSection.isSectionExpanded());
          if (listSection.isSectionExpanded()) {
            onInserted(1, listSection.size());
          } else {
            onRemoved(1, listSection.size());
          }
          onChanged(0, 1, null);
        } else {
          if (listSection.isSectionExpanded()) {
            listSection.setSectionExpanded(!listSection.isSectionExpanded());
            onRemoved(1, listSection.size());
            onChanged(0, 1, null);
          }
        }
        return itemPosition - prevCount;
      case MULTIPLE:
        if (itemPosition < getCount() && itemPosition >= 0) {
          listSection.setSectionExpanded(!listSection.isSectionExpanded());
          if (listSection.isSectionExpanded()) {
            onInserted(1, listSection.size());
          } else {
            onRemoved(1, listSection.size());
          }
          onChanged(0, 1, null);
        }
        return itemPosition - prevCount;
      case NONE:
      default:
        return itemPosition - prevCount;
    }
  }

  @Override int getPositionType(int itemPosition, int adapterPosition,
      RecyclerView.LayoutManager layoutManager) {
    int result = super.getPositionType(itemPosition, adapterPosition, layoutManager);
    if (itemPosition == 0 && getCount() > 1) {
      result = result ^ BOTTOM;
    } else if (itemPosition != 0 && (result & TOP) == TOP) {
      result = result ^ TOP;
    }
    return result;
  }
}
