package dev.ahamed.mva.sample.view.nested;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioGroup;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.Comment;
import dev.ahamed.mva.sample.view.SampleActivity;
import dev.ahamed.mva.sample.view.common.BaseFragment;
import dev.ahamed.mva.sample.view.common.CustomItemAnimator;
import java.util.ArrayList;
import java.util.List;
import mva2.adapter.TreeSection;
import mva2.adapter.decorator.Decorator;

public class NestedSectionFragment extends BaseFragment {

  private List<TreeSection<Comment>> treeSections;
  private RadioGroup radioGroup;

  @Override public void initViews(View view) {
    radioGroup = view.findViewById(R.id.rg_decoration);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(new CustomItemAnimator(SampleActivity.DP));
    adapter.registerItemBinders(new CommentBinder(new DividerDecorator(adapter, requireContext())));
    List<Comment> commentList = dataManager.getComments();
    treeSections = new ArrayList<>();
    for (Comment comment : commentList) {
      TreeSection<Comment> treeSection = getTreeSection(comment, true);
      adapter.addSection(treeSection);
    }
    recyclerView.addItemDecoration(adapter.getItemDecoration());
    recyclerView.setAdapter(adapter);
  }

  @Override public int layoutId() {
    return R.layout.fragment_nested_section;
  }

  @Override public void resetConfiguration() {
    radioGroup.check(R.id.rb_decoration_one);
    updateConfiguration();
  }

  @Override public void updateConfiguration() {
    int checkedId = radioGroup.getCheckedRadioButtonId();
    Decorator decorator = checkedId == R.id.rb_decoration_one ? new CommentDecorator(adapter)
        : checkedId == R.id.rb_decoration_two ? new ThreadDecorator(adapter, requireContext())
            : null;
    for (TreeSection treeSection : treeSections) {
      treeSection.setTreeDecorator(decorator);
    }
    recyclerView.invalidateItemDecorations();
  }

  private TreeSection<Comment> getTreeSection(Comment comment, boolean addDecorator) {
    TreeSection<Comment> treeSection = new TreeSection<>(comment, false);
    for (Comment childComment : comment.getChildComments()) {
      TreeSection<Comment> childTreeSection = getTreeSection(childComment, false);
      treeSection.addSection(childTreeSection);
      if (addDecorator) {
        treeSections.add(childTreeSection);
        //childTreeSection.setTreeDecorator();
      }
    }
    return treeSection;
  }
}
