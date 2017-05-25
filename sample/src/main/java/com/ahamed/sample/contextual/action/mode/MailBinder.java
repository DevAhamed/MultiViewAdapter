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