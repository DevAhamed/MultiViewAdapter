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
 * Displays list of items or section of items along with a header and has collapsing/expanding
 * feature.
 *
 * <p>
 *
 * Section to display list of items with a header. Internally it comprises of an ItemSection and
 * NestedSection. HeaderSection can be used with GridLayoutManager or LinearLayoutManager
 *
 * <p>
 *
 * It provides the collapsing and expanding feature. If the section is collapsed the getCount()
 * will return 1. To collapse or expand the section call
 * {@link ItemViewHolder#toggleSectionExpansion()}.
 *
 * @param <H> The header item object
 *
 * @see MultiViewAdapter#setSectionExpansionMode(Mode)
 */
public class HeaderSection<H> extends NestedSection implements Notifier {

  private final ItemSection<H> itemSection;
  private final NestedSection nestedSection;

  /**
   * Initializes the HeaderSection. This header object is set as null which results in header
   * not being displayed. Call {@link HeaderSection#setHeader} to set the header.
   */
  public HeaderSection() {
    super();

    itemSection = new ItemSection<>();
    nestedSection = new NestedSection();

    super.addSection(itemSection);
    super.addSection(nestedSection);
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
   * Sets the header object. If null is being set, the item will not be displayed in the
   * recyclerview
   *
   * @param header object to be set as header
   */
  public void setHeader(@NonNull H header) {
    itemSection.setItem(header);
  }

  @Override public void addSection(Section section) {
    nestedSection.addSection(section);
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @Override void collapseSection() {
    if (nestedSection.isSectionVisible()) {
      nestedSection.hideSection();
      onChanged(0, 1, SECTION_EXPANSION_PAYLOAD);
    }
  }

  @Override boolean isSectionExpanded(int itemPosition) {
    return nestedSection.isSectionVisible();
  }

  @Override int onSectionExpansionToggled(int itemPosition, @NonNull Mode sectionExpansionMode) {
    Mode mode = getModeToHonor(sectionExpansionMode, this.sectionExpansionMode);
    int prevCount = getCount();
    switch (mode) {
      case SINGLE:
        if (itemPosition < getCount() && itemPosition >= 0) {
          if (!nestedSection.isSectionVisible()) {
            nestedSection.showSection();
          } else {
            nestedSection.hideSection();
          }
          onChanged(0, 1, SECTION_EXPANSION_PAYLOAD);
        } else {
          if (nestedSection.isSectionVisible()) {
            nestedSection.hideSection();
            onChanged(0, 1, SECTION_EXPANSION_PAYLOAD);
          }
        }
        return itemPosition - prevCount;
      case MULTIPLE:
        if (itemPosition < getCount() && itemPosition >= 0) {
          if (!nestedSection.isSectionVisible()) {
            nestedSection.showSection();
          } else {
            nestedSection.hideSection();
          }
          onChanged(0, 1, SECTION_EXPANSION_PAYLOAD);
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
