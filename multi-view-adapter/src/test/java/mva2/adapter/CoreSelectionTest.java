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

import mva2.adapter.util.Mode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class CoreSelectionTest extends BaseTest {

  /**
   * Default selection mode is multiple
   */
  @Test public void selectionModeTest_NotSet() {
    adapter.onItemSelectionToggled(0);
    assertTrue(adapter.isItemSelected(0));

    adapter.onItemSelectionToggled(2);
    assertTrue(adapter.isItemSelected(2));
    assertTrue(adapter.isItemSelected(0));

    adapter.onItemSelectionToggled(2);
    assertTrue(!adapter.isItemSelected(2));
    assertTrue(adapter.isItemSelected(0));

    adapter.onItemSelectionToggled(12);
    assertTrue(adapter.isItemSelected(12));
    adapter.onItemSelectionToggled(12);
    assertTrue(!adapter.isItemSelected(12));

    adapter.onItemSelectionToggled(21);
    assertTrue(adapter.isItemSelected(21));
    adapter.onItemSelectionToggled(21);
    assertTrue(!adapter.isItemSelected(21));

    adapter.onItemSelectionToggled(31);
    assertTrue(adapter.isItemSelected(31));
    adapter.onItemSelectionToggled(31);
    assertTrue(!adapter.isItemSelected(31));

    adapter.onItemSelectionToggled(41);
    assertTrue(adapter.isItemSelected(41));
    adapter.onItemSelectionToggled(41);
    assertTrue(!adapter.isItemSelected(41));

    adapter.onItemSelectionToggled(45);
    assertTrue(adapter.isItemSelected(45));
    adapter.onItemSelectionToggled(45);
    assertTrue(!adapter.isItemSelected(45));

    assertTrue(adapter.isItemSelected(0));
  }

  @Test public void selectionModeTest_SectionMultiple_1() {
    adapter.setSelectionMode(Mode.MULTIPLE);
    listSection1.setSelectionMode(Mode.MULTIPLE);
    adapter.onItemSelectionToggled(1);
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(12);
    assertTrue(adapter.isItemSelected(12));
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(13);
    assertTrue(adapter.isItemSelected(13));
    assertTrue(adapter.isItemSelected(12));
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(13);
    assertTrue(!adapter.isItemSelected(13));
    assertTrue(adapter.isItemSelected(12));
    assertTrue(adapter.isItemSelected(1));
  }

  @Test public void selectionModeTest_SectionMultiple_2() {
    adapter.setSelectionMode(Mode.MULTIPLE);
    listSection1.setSelectionMode(Mode.MULTIPLE);
    headerSection1.setSelectionMode(Mode.SINGLE);
    adapter.onItemSelectionToggled(1);
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(12);
    assertTrue(adapter.isItemSelected(12));
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(13);
    assertTrue(adapter.isItemSelected(13));
    assertTrue(adapter.isItemSelected(12));
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(20);
    assertTrue(adapter.isItemSelected(20));
    adapter.onItemSelectionToggled(22);
    assertTrue(adapter.isItemSelected(22));
    assertTrue(!adapter.isItemSelected(20));

    assertTrue(adapter.isItemSelected(13));
    assertTrue(adapter.isItemSelected(12));
    assertTrue(adapter.isItemSelected(1));
  }

  @Test public void selectionModeTest_SectionSingle() {
    adapter.setSelectionMode(Mode.MULTIPLE);
    listSection1.setSelectionMode(Mode.SINGLE);
    listSection2.setSelectionMode(Mode.SINGLE);
    adapter.onItemSelectionToggled(1);
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(22);
    assertTrue(adapter.isItemSelected(22));
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(23);
    assertTrue(adapter.isItemSelected(23));
    assertFalse(adapter.isItemSelected(22));
    assertTrue(adapter.isItemSelected(1));
  }

  @Test public void selectionModeTest_SectionSingle_1() {
    adapter.setSelectionMode(Mode.MULTIPLE);
    listSection1.setSelectionMode(Mode.SINGLE);
    listSection2.setSelectionMode(Mode.SINGLE);

    adapter.onItemSelectionToggled(22);
    assertTrue(adapter.isItemSelected(22));

    adapter.onItemSelectionToggled(1);
    assertTrue(adapter.isItemSelected(22));
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(23);
    assertTrue(adapter.isItemSelected(23));
    assertFalse(adapter.isItemSelected(22));
    assertTrue(adapter.isItemSelected(1));
  }

  @Test public void selectionModeTest_Single() {
    adapter.setSelectionMode(Mode.SINGLE);
    adapter.onItemSelectionToggled(1);
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(41);
    assertTrue(adapter.isItemSelected(41));
    assertTrue(!adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(2);
    assertTrue(adapter.isItemSelected(2));
    assertTrue(!adapter.isItemSelected(41));

    adapter.onItemSelectionToggled(25);
    assertTrue(adapter.isItemSelected(25));
    assertTrue(!adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(25);
    assertTrue(!adapter.isItemSelected(25));
  }

  @Test public void selectionModeTest_Notify() {
    adapter.setSelectionMode(Mode.MULTIPLE);
    listSection1.setSelectionMode(Mode.MULTIPLE);
    listSection2.setSelectionMode(Mode.MULTIPLE);

    adapter.onItemSelectionToggled(1);
    adapter.onItemSelectionToggled(41);
    adapter.onItemSelectionToggled(2);
    adapter.onItemSelectionToggled(25);
    adapter.onItemSelectionToggled(28);

    clearInvocations(adapterDataObserver);
    adapter.clearAllSelections();

    verify(adapterDataObserver).notifyItemRangeChanged(eq(1), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(41), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(2), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(25), eq(1), any());
    verify(adapterDataObserver).notifyItemRangeChanged(eq(28), eq(1), any());
  }

  @Test public void selectionModeTest_NestedSection() {
    adapter.setSelectionMode(Mode.MULTIPLE);
    listSection1.setSelectionMode(Mode.SINGLE);
    listSection2.setSelectionMode(Mode.SINGLE);
    listSection3.setSelectionMode(Mode.SINGLE);
    listSection4.setSelectionMode(Mode.SINGLE);
    adapter.onSectionExpansionToggled(19);

    adapter.onItemSelectionToggled(21);

    assertTrue(adapter.isItemSelected(21));

    adapter.onSectionExpansionToggled(19);

    assertFalse(adapter.isItemSelected(21));
    assertTrue(adapter.isItemSelected(30));
  }
}
