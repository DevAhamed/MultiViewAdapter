package com.ahamed.multiviewadapter.annotation;

import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
 * LinearLayoutManager} in the order of display.
 *
 * If adapter uses {@link GridLayoutManager} PositionType refers to relative position of item in the
 * grid.
 */
@Retention(RetentionPolicy.SOURCE) @IntDef({
    POSITION_TOP, POSITION_LEFT, POSITION_MIDDLE, POSITION_RIGHT, POSITION_BOTTOM,
    POSITION_FIRST_ITEM, POSITION_MIDDLE_ITEM, POSITION_LAST_ITEM
}) public @interface PositionType {
}