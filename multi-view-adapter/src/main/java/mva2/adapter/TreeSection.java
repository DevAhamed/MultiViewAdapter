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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import mva2.adapter.decorator.Decorator;
import mva2.adapter.decorator.SectionPositionType;
import mva2.adapter.internal.Notifier;
import mva2.adapter.util.Mode;

/**
 * TreeSection class is still in alpha stage. If you use this class be aware that its public api's
 * are subject to change without backward compatibility.
 *
 * TreeSection, as the name suggests, can be used to display a list of nested items. Ex : To
 * display a list of nested comments. The main difference between NestedSection and TreeSection is
 * while NestedSection can display nested elements, TreeSection can display a parent item and list
 * of its child elements.
 *
 * @param <M> Model class for section
 */
public class TreeSection<M> extends NestedSection implements Notifier {

  private final ItemSection<M> itemSection;
  private Decorator treeDecorator;
  private boolean isSectionExpanded = true;

  /**
   * Constructor which initializes TreeSection with an item.
   *
   * @param item       Item to be set for this TreeSection
   * @param isExpanded If true, TreeSection is expanded and added to adapter. If false, the
   *                   TreeSection is collapsed and added to the adapter.
   */
  public TreeSection(M item, boolean isExpanded) {
    super();

    itemSection = new ItemSection<>(item);
    itemSection.setNotifier(this);

    sections.add(itemSection);

    setSectionExpansionMode(Mode.MULTIPLE);
    setSectionExpanded(isExpanded);
  }

  /**
   * Returns the child section at the specified position in this list.
   *
   * @param index index of the child section to return
   *
   * @return the child section at the specified position in this list
   *
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  public TreeSection getChild(int index) {
    return (TreeSection) sections.get(index);
  }

  /**
   * Returns the current item. If the section, for example, displays a tree of comments then this
   * method will return the current comment item for this section.
   *
   * @return Current item object
   */
  public M getParent() {
    return itemSection.getItem();
  }

  /**
   * Sets a decorator for TreeSection and its child items.
   *
   * @param treeDecorator Decorator that needs to be set for current TreeSection and its child
   *                      sections
   */
  public void setTreeDecorator(Decorator treeDecorator) {
    this.treeDecorator = treeDecorator;
    for (Section section : sections) {
      if (section instanceof TreeSection) {
        ((TreeSection) section).setTreeDecorator(treeDecorator);
      }
    }
  }

  /**
   * Adds a TreeSection to the current TreeSection. If the section being added is not an instance of
   * TreeSection, IllegalArgumentException will be thrown.
   *
   * @param section TreeSection to be added
   */
  @Override public void addSection(Section section) {
    if (!(section instanceof TreeSection)) {
      throw new IllegalArgumentException();
    }
    addTreeSection((TreeSection) section);
    if (null != treeDecorator) {
      ((TreeSection) section).setTreeDecorator(treeDecorator);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////
  /// ------------------------------------------------------------------------------ ///
  /// ---------------------  CAUTION : INTERNAL METHODS AHEAD  --------------------- ///
  /// ---------  INTERNAL METHODS ARE NOT PART OF PUBLIC API SET OFFERED  ---------- ///
  /// -------------  SUBJECT TO CHANGE WITHOUT BACKWARD COMPATIBILITY -------------- ///
  /// ------------------------------------------------------------------------------ ///
  //////////////////////////////////////////////////////////////////////////////////////

  @Override void collapseSection() {
    if (isSectionExpanded()) {
      onSectionExpansionToggled(0, sectionExpansionMode);
    }
  }

  @Override int getCount() {
    if (isSectionExpanded()) {
      return super.getCount();
    } else {
      return itemSection.getCount();
    }
  }

  @Override SectionPositionType getSectionPositionType(int adapterPosition, int sectionPosition,
      int size) {
    return SectionPositionType.MIDDLE;
  }

  @Override int onSectionExpansionToggled(int itemPosition, @NonNull Mode sectionExpansionMode) {
    if (itemPosition < getCount() && itemPosition >= 0) {
      if (itemPosition == 0) {
        int count = getChildCount() - 1;
        setSectionExpanded(!isSectionExpanded());
        if (isSectionExpanded()) {
          onInserted(1, count);
        } else {
          onRemoved(1, count);
        }
        onChanged(0, 1, SECTION_EXPANSION_PAYLOAD);
      } else {
        onChildSectionExpansionToggled(itemPosition, this.sectionExpansionMode);
      }
    }
    return itemPosition - getCount();
  }

  @Override void drawDecoration(int itemPosition, @NonNull Canvas canvas,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, View child,
      int adapterPosition) {
    if (itemPosition != 0) {
      drawChildSectionDecoration(itemPosition, canvas, parent, state, child, adapterPosition);
    } else if (null != treeDecorator) {
      treeDecorator.onDraw(canvas, parent, state, child, adapterPosition);
    }
  }

  @Override void drawDecorationOver(int itemPosition, @NonNull Canvas canvas,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, View child,
      int adapterPosition) {
    if (itemPosition != 0) {
      drawChildSectionDecorationOver(itemPosition, canvas, parent, state, child, adapterPosition);
    } else if (null != treeDecorator) {
      treeDecorator.onDrawOver(canvas, parent, state, child, adapterPosition);
    }
  }

  @Override void getDecorationOffsets(int itemPosition, @NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state, int adapterPosition) {
    if (null != treeDecorator) {
      treeDecorator.getItemOffsets(outRect, view, parent, state, adapterPosition);
    }
    if (itemPosition != 0) {
      getChildSectionOffsets(itemPosition, outRect, view, parent, state, adapterPosition);
    }
  }

  @Override boolean isSectionExpanded(int itemPosition) {
    if (itemPosition == 0) {
      return isSectionExpanded();
    }
    return super.isSectionExpanded(itemPosition);
  }

  boolean isSectionVisible() {
    return !isSectionHidden();
  }

  private void addTreeSection(TreeSection section) {
    section.setNotifier(this);
    section.setTreeDecorator(treeDecorator);
    sections.add(section);
  }

  private int getChildCount() {
    int itemCount = 0;
    for (Section section : sections) {
      itemCount += section.getCount();
    }
    return itemCount;
  }

  private boolean isSectionExpanded() {
    return isSectionExpanded;
  }

  private void setSectionExpanded(boolean sectionExpanded) {
    isSectionExpanded = sectionExpanded;
  }
}
