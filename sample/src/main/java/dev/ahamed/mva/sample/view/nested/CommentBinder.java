package dev.ahamed.mva.sample.view.nested;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Comment;
import java.util.List;
import mva2.adapter.ItemBinder;
import mva2.adapter.ItemViewHolder;
import mva2.adapter.decorator.Decorator;

public class CommentBinder extends ItemBinder<Comment, CommentBinder.ViewHolder> {

  public CommentBinder(Decorator decorator) {
    super(decorator);
  }

  @Override public ViewHolder createViewHolder(ViewGroup parent) {
    return new ViewHolder(inflate(parent, R.layout.item_comment));
  }

  @Override public void bindViewHolder(ViewHolder holder, Comment item, List payload) {
    if (item.getChildComments().size() > 0) {
      holder.tvChildCount.setText(
          "(" + (holder.isSectionExpanded() ? "-" : "+") + item.getChildComments().size() + ")");
    } else {
      holder.tvChildCount.setText("");
    }
  }

  @Override public void bindViewHolder(ViewHolder holder, Comment item) {
    holder.tvComment.setText(item.getComment());
    holder.tvOverline.setText(item.getAuthor() + " - " + item.getPostedTime());
    if (item.getChildComments().size() > 0) {
      holder.tvChildCount.setText(
          "(" + (holder.isSectionExpanded() ? "-" : "+") + item.getChildComments().size() + ")");
    } else {
      holder.tvChildCount.setText("");
    }
  }

  @Override public boolean canBindData(Object item) {
    return true; // Comment is the only item displayed in this adapter
  }

  static class ViewHolder extends ItemViewHolder<Comment> {

    TextView tvComment;
    TextView tvOverline;
    TextView tvChildCount;

    public ViewHolder(View itemView) {
      super(itemView);
      tvComment = itemView.findViewById(R.id.tv_comment);
      tvOverline = itemView.findViewById(R.id.tv_overline);
      tvChildCount = itemView.findViewById(R.id.tv_count);
      itemView.setOnClickListener(view -> toggleSectionExpansion());
    }
  }
}
