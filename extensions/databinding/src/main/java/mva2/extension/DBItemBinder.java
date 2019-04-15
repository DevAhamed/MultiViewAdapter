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
import android.view.ViewGroup;
import mva2.adapter.ItemBinder;

/**
 * ItemBinder which supports the DataBinding
 *
 * @param <M>   Refers to the model class
 * @param <VDB> Refers to the view binding of the model class
 */
public abstract class DBItemBinder<M, VDB extends ViewDataBinding>
    extends ItemBinder<M, DBItemBinder.ViewHolder<M, VDB>> {

  @Override public final ViewHolder<M, VDB> createViewHolder(ViewGroup parent) {
    return createViewHolder(createBinding(parent));
  }

  @Override public final void bindViewHolder(ViewHolder<M, VDB> holder, M item) {
    bindModel(item, holder.getBinding());
    holder.getBinding().executePendingBindings();
  }

  protected ViewHolder<M, VDB> createViewHolder(VDB binding) {
    return new ViewHolder<>(binding);
  }

  protected abstract void bindModel(M item, VDB binding);

  protected abstract VDB createBinding(ViewGroup parent);

  protected static class ViewHolder<M, VDB extends ViewDataBinding>
      extends BindingViewHolder<M, VDB> {

    public ViewHolder(VDB binding) {
      super(binding);
    }
  }
}
