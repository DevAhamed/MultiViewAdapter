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

package mva3.adapter;

import mva3.adapter.util.Mode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class CoreExpansionTest extends BaseTest {

  /**
   * Default expansion mode is multiple
   */
  @Test public void expansionModeTest_NotSet() {
    adapter.onItemExpansionToggled(0);
    assertTrue(adapter.isItemExpanded(0));

    adapter.onItemExpansionToggled(2);
    assertTrue(adapter.isItemExpanded(2));
    assertTrue(adapter.isItemExpanded(0));

    adapter.onItemExpansionToggled(2);
    assertTrue(!adapter.isItemExpanded(2));
    assertTrue(adapter.isItemExpanded(0));

    adapter.onItemExpansionToggled(12);
    assertTrue(adapter.isItemExpanded(12));
    adapter.onItemExpansionToggled(12);
    assertTrue(!adapter.isItemExpanded(12));

    adapter.onItemExpansionToggled(21);
    assertTrue(adapter.isItemExpanded(21));
    adapter.onItemExpansionToggled(21);
    assertTrue(!adapter.isItemExpanded(21));

    adapter.onItemExpansionToggled(31);
    assertTrue(adapter.isItemExpanded(31));
    adapter.onItemExpansionToggled(31);
    assertTrue(!adapter.isItemExpanded(31));

    adapter.onItemExpansionToggled(41);
    assertTrue(adapter.isItemExpanded(41));
    adapter.onItemExpansionToggled(41);
    assertTrue(!adapter.isItemExpanded(41));

    adapter.onItemExpansionToggled(45);
    assertTrue(adapter.isItemExpanded(45));
    adapter.onItemExpansionToggled(45);
    assertTrue(!adapter.isItemExpanded(45));

    assertTrue(adapter.isItemExpanded(0));
  }

  @Test public void expansionModeTest_SectionMultiple_1() {
    adapter.setExpansionMode(Mode.MULTIPLE);
    listSection1.setExpansionMode(Mode.MULTIPLE);
    adapter.onItemExpansionToggled(1);
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(12);
    assertTrue(adapter.isItemExpanded(12));
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(13);
    assertTrue(adapter.isItemExpanded(13));
    assertTrue(adapter.isItemExpanded(12));
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(13);
    assertTrue(!adapter.isItemExpanded(13));
    assertTrue(adapter.isItemExpanded(12));
    assertTrue(adapter.isItemExpanded(1));

    adapter.collapseAllItems();

    assertFalse(adapter.isItemExpanded(13));
    assertFalse(adapter.isItemExpanded(12));
    assertFalse(adapter.isItemExpanded(1));
  }

  @Test public void expansionModeTest_SectionMultiple_2() {
    adapter.setExpansionMode(Mode.MULTIPLE);
    listSection1.setExpansionMode(Mode.MULTIPLE);
    headerSection1.setExpansionMode(Mode.SINGLE);
    adapter.onItemExpansionToggled(1);
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(12);
    assertTrue(adapter.isItemExpanded(12));
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(13);
    assertTrue(adapter.isItemExpanded(13));
    assertTrue(adapter.isItemExpanded(12));
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(22);
    assertTrue(adapter.isItemExpanded(22));
    adapter.onItemExpansionToggled(23);
    assertTrue(adapter.isItemExpanded(23));
    assertTrue(!adapter.isItemExpanded(22));

    assertTrue(adapter.isItemExpanded(13));
    assertTrue(adapter.isItemExpanded(12));
    assertTrue(adapter.isItemExpanded(1));
  }

  @Test public void expansionModeTest_SectionSingle() {
    adapter.setExpansionMode(Mode.MULTIPLE);
    listSection1.setExpansionMode(Mode.SINGLE);
    listSection2.setExpansionMode(Mode.SINGLE);
    adapter.onItemExpansionToggled(1);
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(22);
    assertTrue(adapter.isItemExpanded(22));
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(23);
    assertTrue(adapter.isItemExpanded(23));
    Assert.assertFalse(adapter.isItemExpanded(22));
    assertTrue(adapter.isItemExpanded(1));
  }

  @Test public void expansionModeTest_SectionSingle_1() {
    adapter.setExpansionMode(Mode.MULTIPLE);
    listSection1.setExpansionMode(Mode.SINGLE);
    listSection2.setExpansionMode(Mode.SINGLE);

    adapter.onItemExpansionToggled(22);
    assertTrue(adapter.isItemExpanded(22));

    adapter.onItemExpansionToggled(1);
    assertTrue(adapter.isItemExpanded(22));
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(23);
    assertTrue(adapter.isItemExpanded(23));
    assertFalse(adapter.isItemExpanded(22));
    assertTrue(adapter.isItemExpanded(1));
  }

  @Test public void expansionModeTest_Single() {
    adapter.setExpansionMode(Mode.SINGLE);
    adapter.onItemExpansionToggled(1);
    assertTrue(adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(41);
    assertTrue(adapter.isItemExpanded(41));
    assertTrue(!adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(2);
    assertTrue(adapter.isItemExpanded(2));
    assertTrue(!adapter.isItemExpanded(41));

    adapter.onItemExpansionToggled(25);
    assertTrue(adapter.isItemExpanded(25));
    assertTrue(!adapter.isItemExpanded(1));

    adapter.onItemExpansionToggled(25);
    assertTrue(!adapter.isItemExpanded(25));
  }

  @Test public void expansionModeTest_Notify() {
    adapter.setExpansionMode(Mode.MULTIPLE);
    listSection1.setExpansionMode(Mode.MULTIPLE);
    listSection2.setExpansionMode(Mode.MULTIPLE);

    adapter.onItemExpansionToggled(1);
    adapter.onItemExpansionToggled(41);
    adapter.onItemExpansionToggled(2);
    adapter.onItemExpansionToggled(25);
    adapter.onItemExpansionToggled(28);

    clearInvocations(adapterDataObserver);
    adapter.collapseAllItems();

    verify(adapterDataObserver).notifyItemRangeChanged(eq(1), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(41), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(2), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(25), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(28), eq(1), any());
  }

  @Test public void expansionModeTest_NestedSection() {
    adapter.setExpansionMode(Mode.MULTIPLE);
    listSection1.setExpansionMode(Mode.SINGLE);
    listSection2.setExpansionMode(Mode.SINGLE);
    listSection3.setExpansionMode(Mode.SINGLE);
    listSection4.setExpansionMode(Mode.SINGLE);
    adapter.onSectionExpansionToggled(19);

    adapter.onItemExpansionToggled(21);
    assertTrue(adapter.isItemExpanded(21));

    adapter.onSectionExpansionToggled(19);

    assertFalse(adapter.isItemExpanded(21));
    assertTrue(adapter.isItemExpanded(30));
  }
}
