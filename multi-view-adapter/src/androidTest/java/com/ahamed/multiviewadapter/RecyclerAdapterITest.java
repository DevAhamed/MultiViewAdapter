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

package com.ahamed.multiviewadapter;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) public class RecyclerAdapterITest {

  private RecyclerAdapter recyclerAdapter = new RecyclerAdapter();

  @Test public void setExpandableModeTest() {
    recyclerAdapter.setExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
    assertEquals(recyclerAdapter.expandableMode, RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
  }

  @Test public void setGroupExpandableModeTest() {
    recyclerAdapter.setGroupExpandableMode(RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
    assertEquals(recyclerAdapter.groupExpandableMode, RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE);
  }
}
