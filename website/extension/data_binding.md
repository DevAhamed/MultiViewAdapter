# DataBinding

Data binding support is provided as an extension to the adapter. Currently library supports the DataBindingV2.


### Download

Latest Version [![DataBinding Version](https://api.bintray.com/packages/devahamed/MultiViewAdapter/multi-view-adapter-databinding/images/download.svg)](https://bintray.com/devahamed/MultiViewAdapter/multi-view-adapter/_latestVersion)

```groovy
implementation 'me.riyazahamed:mva-data-binding:x.y.z'
```

### Usage

Extend your binder classes from the ``DBItemBinder`` class. No viewholder is needed.

```java
public class SampleDataBinder extends DBItemBinder<M, MyBinding> {

  @Override protected void bindModel(M item, MyBinding binding) {
    binding.setModel(item);
  }

  @Override
  protected MyBinding createBinding(ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return DataBindingUtil.inflate(inflater, R.layout.item_binding, parent, false);
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof M;
  }
}
```

Incase you need to provide your own view holder just override the method ``createViewHolder`` inside binder class.

```java
  @Override protected YourViewHolder<M, YourBinding> createViewHolder(YourBinding binding) {
    return new YourViewHolder(binding);
  }
```

!> You can use ```ItemBinders``` with/without data binding at the same time inside an adapter.