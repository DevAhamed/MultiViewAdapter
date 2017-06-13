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

package com.ahamed.sample.grid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.model.BaseModel;
import com.ahamed.sample.common.model.GridItem;
import com.ahamed.sample.common.model.Header;
import java.util.ArrayList;
import java.util.List;

public class GridAdapterActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, GridAdapterActivity.class);
    context.startActivity(starter);
  }

  public static int convertDpToPixel(float dp, Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    return (int) (dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
  }

  @Override protected void setUpAdapter() {
    GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 3);

    GridAdapter adapter = new GridAdapter(convertDpToPixel(4, this));
    adapter.setSpanCount(3);

    recyclerView.addItemDecoration(adapter.getItemDecorationManager());

    glm.setSpanSizeLookup(adapter.getSpanSizeLookup());
    recyclerView.setLayoutManager(glm);
    recyclerView.setAdapter(adapter);

    List<BaseModel> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      if (i % 10 == 0) {
        data.add(new Header("Sample header " + i));
      } else {
        data.add(GridItem.generateGridItem(i));
      }
    }
    adapter.addData(data);
  }
}