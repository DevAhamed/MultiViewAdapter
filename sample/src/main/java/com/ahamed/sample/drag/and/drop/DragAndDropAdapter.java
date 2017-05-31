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

package com.ahamed.sample.drag.and.drop;

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.SelectableAdapter;
import com.ahamed.sample.common.binder.GridItemBinder;
import com.ahamed.sample.common.binder.HeaderBinder;
import com.ahamed.sample.common.model.BaseModel;
import java.util.List;

class DragAndDropAdapter extends SelectableAdapter {

  private DataListManager<BaseModel> dataManager;

  DragAndDropAdapter(int insetInPixels) {
    dataManager = new DataListManager<>(this);
    addDataManager(dataManager);

    registerBinder(new HeaderBinder());
    registerBinder(new GridItemBinder(insetInPixels));
  }

  void addData(List<BaseModel> data) {
    dataManager.addAll(data);
  }
}