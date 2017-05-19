package com.ahamed.multiviewadapter.annotation;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_MULTIPLE;
import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_NONE;
import static com.ahamed.multiviewadapter.RecyclerAdapter.EXPANDABLE_MODE_SINGLE;

@Retention(RetentionPolicy.SOURCE) @IntDef({
    EXPANDABLE_MODE_NONE, EXPANDABLE_MODE_SINGLE, EXPANDABLE_MODE_MULTIPLE
}) public @interface ExpandableMode {

}