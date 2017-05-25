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

package com.ahamed.sample;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import java.util.List;

class SampleAdapter extends RecyclerAdapter {

  private DataListManager<String> dataListManager;

  SampleAdapter(BaseViewHolder.OnItemClickListener<String> onItemClickListener) {
    dataListManager = new DataListManager<>(this);
    addDataManager(dataListManager);

    registerBinder(new SampleBinder(onItemClickListener));
  }

  void setDataList(List<String> dataList) {
    dataListManager.set(dataList);
  }
}
