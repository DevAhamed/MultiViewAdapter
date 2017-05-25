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

package com.ahamed.sample.swipetodismiss;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import com.ahamed.sample.common.BaseActivity;
import com.ahamed.sample.common.adapter.MultiListAdapter;
import com.ahamed.sample.common.model.Flower;
import java.util.ArrayList;
import java.util.List;

public class SwipeToDismissActivity extends BaseActivity {

  public static void start(Context context) {
    Intent starter = new Intent(context, SwipeToDismissActivity.class);
    context.startActivity(starter);
  }

  @Override protected void setUpAdapter() {
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

    MultiListAdapter adapter = new MultiListAdapter(this);
    recyclerView.addItemDecoration(adapter.getItemDecorationManager());

    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);
    adapter.getItemTouchHelper().attachToRecyclerView(recyclerView);

    List<Flower> flowers = new ArrayList<>();
    for (int i = 0; i < 25; i++) {
      flowers.add(new Flower(i, "Flower " + i));
    }
    adapter.addFlowers(flowers);
  }
}
