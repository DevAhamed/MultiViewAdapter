package com.ahamed.sample.common.binder;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.model.Advertisement;

public class AdvertisementBinder extends ItemBinder<Advertisement, AdvertisementBinder.ViewHolder> {

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_promotion, parent, false));
  }

  @Override public void bind(ViewHolder holder, Advertisement item) {
    holder.tvTitle.setText(item.getAdDescription());
    holder.tvDescription.setText(item.getAdSecondaryText());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Advertisement;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends BaseViewHolder<Advertisement> {

    private TextView tvTitle;
    private TextView tvDescription;
    private Button btnGithub;

    ViewHolder(View itemView) {
      super(itemView);
      tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
      btnGithub = (Button) itemView.findViewById(R.id.btn_github);
      btnGithub.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getItem().getUrl()));
          v.getContext().startActivity(browserIntent);
        }
      });
    }
  }
}