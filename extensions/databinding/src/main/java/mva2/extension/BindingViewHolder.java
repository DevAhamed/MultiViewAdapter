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

package mva2.extension;

import android.databinding.ViewDataBinding;
import mva2.adapter.ItemViewHolder;

/**
 * ViewHolder which supports the DataBinding
 *
 * @param <M>   Refers to the model class
 * @param <VDB> Refers to the DataBinding class of the model
 */
abstract class BindingViewHolder<M, VDB extends ViewDataBinding> extends ItemViewHolder<M> {

  private final VDB binding;

  BindingViewHolder(VDB binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  public VDB getBinding() {
    return binding;
  }
}
