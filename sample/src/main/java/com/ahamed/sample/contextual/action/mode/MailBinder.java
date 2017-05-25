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

package com.ahamed.sample.contextual.action.mode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Mail;

public class MailBinder extends ItemBinder<Mail, MailBinder.ViewHolder> {

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_mail, parent, false));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Mail;
  }

  @Override public void bind(ViewHolder holder, Mail item) {
    // TODO bind data here
  }

  static class ViewHolder extends BaseViewHolder<Mail> {

    ViewHolder(View itemView) {
      super(itemView);
    }
  }
}