package dev.ahamed.mva.sample.view.decoration;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Person;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;

public class PersonBinder extends ItemBinder<Person, PersonBinder.ViewHolder> {

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_person));
  }

  @Override public void bindViewHolder(ViewHolder holder, Person item) {
    holder.name.setText(item.getName());
    holder.overline.setText(item.getOverline());
    holder.avatar.setText(item.getInitials());
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Person;
  }

  static class ViewHolder extends ItemViewHolder<Person> {

    private TextView name;
    private TextView overline;
    private TextView avatar;

    ViewHolder(View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.tv_name);
      overline = itemView.findViewById(R.id.tv_overline);
      avatar = itemView.findViewById(R.id.tv_avatar);
    }
  }
}
