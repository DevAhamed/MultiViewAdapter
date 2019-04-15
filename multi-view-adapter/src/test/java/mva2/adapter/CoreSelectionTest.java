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
    adapter.onItemSelectionToggled(1);
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(22);
    assertTrue(adapter.isItemSelected(22));
    assertTrue(adapter.isItemSelected(1));

    adapter.onItemSelectionToggled(23);
    assertTrue(adapter.isItemSelected(23));
    assertTrue(adapter.isItemSelected(22));
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
}
