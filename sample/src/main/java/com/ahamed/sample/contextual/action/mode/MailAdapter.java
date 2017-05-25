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

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.sample.common.model.Mail;
import java.util.List;

public class MailAdapter extends RecyclerAdapter {

  private DataListManager<Mail> mailListManager;

  public MailAdapter() {
    this.mailListManager = new DataListManager<>(this);
    addDataManager(mailListManager);

    registerBinder(new MailBinder());
  }

  public void setMailList(List<Mail> dataList) {
    mailListManager.set(dataList);
  }
}