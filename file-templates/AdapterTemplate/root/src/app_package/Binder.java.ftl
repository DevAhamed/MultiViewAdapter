package ${packageName};

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import ${packageNameOfModel}.${modelName};
<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>

public class ${binderName} extends ItemBinder<${modelName}, ${binderName}.ViewHolder> {

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.${item_layout}, parent, false));
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof ${modelName};
  }

  @Override public void bind(ViewHolder holder, ${modelName} item) {
    // TODO bind data here
  }

  static class ViewHolder extends BaseViewHolder<${modelName}> {

    ViewHolder(View itemView) {
      super(itemView);
    }
  }
}