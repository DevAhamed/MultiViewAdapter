package com.ahamed.multiviewadapter.annotation;

import android.support.annotation.IntDef;
import com.ahamed.multiviewadapter.DataListManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_BOTTOM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_FIRST_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_LAST_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_LEFT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_MIDDLE;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_MIDDLE_ITEM;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_RIGHT;
import static com.ahamed.multiviewadapter.util.ItemDecorator.POSITION_TOP;

/**
 * PositionType can be used to resolve whether the item is first/last element in the {@link
 * DataListManager} in the order of display.
 *
 * <p>ie., if the LinearLayoutManager has reverseLayout as true, then the first element in the
 * {@link DataListManager} will have the {@link PositionType} as
 * PositionType.POSITION_FIRST_ITEM</p>
 *
 * <p>The item can be a first element, last element or middle element(for all other
 * positions).</p>
 */
@Retention(RetentionPolicy.SOURCE) @IntDef({
    POSITION_TOP, POSITION_LEFT, POSITION_MIDDLE, POSITION_RIGHT, POSITION_BOTTOM,
    POSITION_FIRST_ITEM, POSITION_MIDDLE_ITEM, POSITION_LAST_ITEM
}) public @interface PositionType {
}