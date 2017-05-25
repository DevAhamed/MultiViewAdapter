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

import android.databinding.ViewDataBinding;

/**
 * {@link ItemBinder} which supports the DataBinding
 *
 * @param <M> Refers to the model class
 * @param <VDB> Refers to the view binding of the model class
 * @param <VH> Refers to the view holder which supports the data binding
 */
public abstract class ItemDataBinder<M, VDB extends ViewDataBinding, VH extends BindingViewHolder<M, VDB>>
    extends ItemBinder<M, VH> {

  @Override public final void bind(VH holder, M item) {
    holder.bindModel(item);
  }
}
