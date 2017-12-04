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

import com.ahamed.multiviewadapter.util.ItemDecorator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) public class ItemBinderTest {

  @Mock ItemDecorator itemDecorator;
  @Mock ItemDecorator itemDecoratorTwo;
  @Mock DummyTwoBinder dummyTwoBinder;
  DummyOneBinder dummyOneBinder = new DummyOneBinder();

  @Before public void setUp() {
    dummyTwoBinder.addDecorator(itemDecorator);
  }

  @Test public void itemDecoratorTest() {
    dummyOneBinder = new DummyOneBinder(itemDecorator);

    // No item decoration for dummyTwoBinder
    assertTrue(dummyTwoBinder.isItemDecorationEnabled());

    assertTrue(dummyOneBinder.isItemDecorationEnabled());
    dummyOneBinder.addDecorator(itemDecoratorTwo);
    assertTrue(dummyOneBinder.isItemDecorationEnabled());

    dummyOneBinder.getItemOffsets(null, -1, ItemDecorator.POSITION_TOP);
    verify(itemDecorator).getItemOffsets(null, -1, ItemDecorator.POSITION_TOP);
    verify(itemDecoratorTwo).getItemOffsets(null, -1, ItemDecorator.POSITION_TOP);

    dummyOneBinder.onDraw(null, null, null, -1, ItemDecorator.POSITION_TOP);
    verify(itemDecorator).onDraw(null, null, null, -1, ItemDecorator.POSITION_TOP);
    verify(itemDecoratorTwo).onDraw(null, null, null, -1, ItemDecorator.POSITION_TOP);
  }
}
