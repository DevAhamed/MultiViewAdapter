![RxDiffUtil](images/ext-rx-cover.jpg)

'RxDiffUtil' is a lightweight reactive wrapper around DiffUtil from RecyclerView library.


### Download

![RxDiffUtil Version](https://api.bintray.com/packages/devahamed/MVA2/ext-diffutil-rx/images/download.svg)

```groovy
implementation 'dev.ahamed.mva2:ext-diffutil-rx:2.0.0-alpha01'
```

### Usage

RxDiffUtil can be used to run the diff calculation in the background thread and notify the adapter on ui thread. Use RxDiffUtill with following steps,

1. Create RxDiffUtil object
2. Set it to your ```Section```

```java
    RxDiffUtil<M> diffUtil = new RxDiffUtil<>(payloadProvider);
    section.setDiffUtil(diffUtil);
```

Kindly read more about ``PayloadProvider`` [here](feature_diffutil.md).