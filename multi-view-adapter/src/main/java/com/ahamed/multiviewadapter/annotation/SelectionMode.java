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
 * <li>SELECTION_MODE_NONE - Default value. No {@link RecyclerAdapter} is not selectable </li>
 * <li>SELECTION_MODE_SINGLE - Single selection. You cannot deselect the item without selecting
 * other item. Similar to a {@link RadioGroup} </li>
 * <li>SELECTION_MODE_SINGLE_OR_NONE - Single selection. You can deselect the item previously
 * selected item</li>
 * <li>SELECTION_MODE_MULTIPLE - Multiple selection</li>
 */
@Retention(RetentionPolicy.SOURCE) @IntDef({
    SELECTION_MODE_NONE, SELECTION_MODE_SINGLE, SELECTION_MODE_SINGLE_OR_NONE,
    SELECTION_MODE_MULTIPLE
}) public @interface SelectionMode {
}