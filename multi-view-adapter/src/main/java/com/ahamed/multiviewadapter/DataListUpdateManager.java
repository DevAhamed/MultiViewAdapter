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

package com.ahamed.multiviewadapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import com.ahamed.multiviewadapter.util.DiffUtilCallback;
import com.ahamed.multiviewadapter.util.PayloadProvider;
import java.util.Collection;
import java.util.List;

class DataListUpdateManager<M> extends BaseDataManager<M> {

  final PayloadProvider<M> payloadProvider;

  DataListUpdateManager(@NonNull RecyclerAdapter adapter) {
    this(adapter, new PayloadProvider<M>() {
      @Override public boolean areContentsTheSame(M oldItem, M newItem) {
        return oldItem.equals(newItem);
      }

      @Override public Object getChangePayload(M oldItem, M newItem) {
        return null;
      }
    });
  }

  DataListUpdateManager(@NonNull RecyclerAdapter adapter,
      @NonNull PayloadProvider<M> payloadProvider) {
    super(adapter);
    this.payloadProvider = payloadProvider;
  }

  final boolean add(M item, boolean notifyDataSetChanged) {
    boolean result = getDataList().add(item);
    if (result && notifyDataSetChanged) {
      onInserted(getDataList().size() - 1, 1);
    }
    return result;
  }

  final boolean addAll(@NonNull Collection<? extends M> items, boolean notifyDataSetChanged) {
    return addAll(getDataList().size(), items, notifyDataSetChanged);
  }

  final boolean addAll(int index, @NonNull Collection<? extends M> items,
      boolean notifyDataSetChanged) {
    boolean result = getDataList().addAll(index, items);
    if (result && notifyDataSetChanged) {
      onInserted(index, items.size());
    }
    return result;
  }

  final void add(int index, M item, boolean notifyDataSetChanged) {
    getDataList().add(index, item);
    if (notifyDataSetChanged) {
      onInserted(index, 1);
    }
  }

  final void set(int index, M item, boolean notifyDataSetChanged) {
    M oldItem = getDataList().get(index);
    getDataList().set(index, item);
    if (notifyDataSetChanged) {
      onChanged(index, 1, payloadProvider.getChangePayload(oldItem, item));
    }
  }

  final void set(List<M> dataList, boolean notifyDataSetChanged) {
    DiffUtil.DiffResult result = null;
    if (notifyDataSetChanged) {
      result = DiffUtil.calculateDiff(new DiffUtilCallback<M>(this.getDataList(), dataList) {
        @Override public boolean areContentsTheSame(M oldItem, M newItem) {
          return payloadProvider.areContentsTheSame(oldItem, newItem);
        }

        @Override public Object getChangePayload(M oldItem, M newItem) {
          return payloadProvider.getChangePayload(oldItem, newItem);
        }
      });
    }
    setDataList(dataList);
    if (notifyDataSetChanged) {
      result.dispatchUpdatesTo(this);
    }
  }

  final void remove(M item, boolean notifyDataSetChanged) {
    int index = getDataList().indexOf(item);
    boolean result = getDataList().remove(item);
    if (result && notifyDataSetChanged) {
      onRemoved(index, 1);
    }
  }

  final void remove(int index, boolean notifyDataSetChanged) {
    if (index >= size()) {
      throw new IndexOutOfBoundsException();
    }
    getDataList().remove(index);
    if (notifyDataSetChanged) {
      onRemoved(index, 1);
    }
  }

  final void clear(boolean notifyDataSetChanged) {
    if (size() <= 0) {
      return;
    }
    int oldSize = getDataList().size();
    getDataList().clear();
    if (notifyDataSetChanged) {
      onRemoved(0, oldSize);
    }
  }
}