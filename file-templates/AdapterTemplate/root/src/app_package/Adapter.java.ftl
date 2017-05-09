package ${packageName};

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import java.util.List;
import ${packageNameOfModel}.${modelName};

public class ${adapterName} extends RecyclerAdapter {

  private DataListManager<${modelName}> ${extractLetters(modelName?lower_case)}ListManager;

  public ${adapterName}() {
    this.${extractLetters(modelName?lower_case)}ListManager = new DataListManager<>(this);
    addDataManager(${extractLetters(modelName?lower_case)}ListManager);

    registerBinder(new ${binderName}());
  }

  public void set${modelName}List(List<${modelName}> dataList) {
    ${extractLetters(modelName?lower_case)}ListManager.set(dataList);
  }
}