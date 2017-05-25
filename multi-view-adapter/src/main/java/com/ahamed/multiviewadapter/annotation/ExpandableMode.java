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
import com.ahamed.multiviewadapter.DataGroupManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE;
import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_NONE;
import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_SINGLE;

/**
 * Annotation to represent the expandable mode of the item or the {@link DataGroupManager}
 *
 * Possible values are :
 * EXPANDABLE_MODE_NONE - Item/Group can not be expanded
 * EXPANDABLE_MODE_SINGLE - Only one item/group can be expanded at a time
 * EXPANDABLE_MODE_MULTIPLE - Multiple items/groups can be expanded at a time
 */
@Retention(RetentionPolicy.SOURCE) @IntDef({
    EXPANDABLE_MODE_NONE, EXPANDABLE_MODE_SINGLE, EXPANDABLE_MODE_MULTIPLE
}) public @interface ExpandableMode {
}