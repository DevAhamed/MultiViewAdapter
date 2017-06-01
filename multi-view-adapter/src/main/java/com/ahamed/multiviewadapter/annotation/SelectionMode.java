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

package com.ahamed.multiviewadapter.annotation;

import android.support.annotation.IntDef;
import android.widget.RadioGroup;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ahamed.multiviewadapter.SelectableAdapter.SELECTION_MODE_MULTIPLE;
import static com.ahamed.multiviewadapter.SelectableAdapter.SELECTION_MODE_NONE;
import static com.ahamed.multiviewadapter.SelectableAdapter.SELECTION_MODE_SINGLE;
import static com.ahamed.multiviewadapter.SelectableAdapter.SELECTION_MODE_SINGLE_OR_NONE;

/**
 * Represents the selection mode of the adapter.
 *
 * <p>Possible values : </p>
 * <p>SELECTION_MODE_NONE - Default value. {@link RecyclerAdapter} is not selectable </p>
 * <p>SELECTION_MODE_SINGLE - Single selection. You cannot deselect the item without selecting
 * other item. Similar to a {@link RadioGroup} </p>
 * <p>SELECTION_MODE_SINGLE_OR_NONE - Single selection. You can deselect the item previously
 * selected item</p>
 * <p>SELECTION_MODE_MULTIPLE - Multiple selection</p>
 */
@Retention(RetentionPolicy.SOURCE) @IntDef({
    SELECTION_MODE_NONE, SELECTION_MODE_SINGLE, SELECTION_MODE_SINGLE_OR_NONE,
    SELECTION_MODE_MULTIPLE
}) public @interface SelectionMode {
}