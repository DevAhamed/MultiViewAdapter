package ${packageName};

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ahamed.multiviewadapter.ItemDataBinder;
import ${packageNameOfModel}.${modelName};
import ${applicationPackage}.databinding.${bindingName};
<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>

public class ${binderName} extends ItemDataBinder<${modelName}, ${bindingName}> {

  @Override protected ${bindingName} createBinding(LayoutInflater inflater, ViewGroup parent) {
    return DataBindingUtil.inflate(inflater, R.layout.${item_layout}, parent, false);
  }

  @Override protected void bindModel(${modelName} item, ${bindingName} binding) {
    binding.setModel(item);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof ${modelName};
  }
}
