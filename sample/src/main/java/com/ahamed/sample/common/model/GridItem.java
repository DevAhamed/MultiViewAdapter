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

package com.ahamed.sample.common.model;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import com.ahamed.sample.R;
import java.util.Random;

public class GridItem implements BaseModel {

  private static Random random = new Random();
  private static int[] drawableList =
      { R.drawable.ic_circle, R.drawable.ic_heart, R.drawable.ic_star };
  private static int[] colorList = {
      Color.parseColor("#ef9a9a"), Color.parseColor("#F48FB1"), Color.parseColor("#CE93D8"),
      Color.parseColor("#B39DDB"), Color.parseColor("#9FA8DA"), Color.parseColor("#90CAF9"),
      Color.parseColor("#81D4FA"), Color.parseColor("#C5E1A5"), Color.parseColor("#FFCC80"),
      Color.parseColor("#FFAB91")
  };

  @ColorInt private int color;
  @DrawableRes private int drawable;
  private String data;

  public GridItem(@ColorInt int color, @DrawableRes int drawable, String data) {
    this.color = color;
    this.drawable = drawable;
    this.data = data;
  }

  public static GridItem generateGridItem(int position) {
    return new GridItem(colorList[random.nextInt(10)], drawableList[random.nextInt(3)],
        "Grid Item " + position);
  }

  public int getColor() {
    return color;
  }

  public int getDrawable() {
    return drawable;
  }

  public String getData() {
    return data;
  }
}
