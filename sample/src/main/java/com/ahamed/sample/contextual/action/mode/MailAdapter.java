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