# Migration

## Upgrading to 2.x

2.0 has been re-written from scratch. API surface has been revamped completely and it might require lot of changes on your code to update. Good news is, you can keep both 1.x and 2.x versions inside the same app and migrate one adapter at a time.

### Contents

1. [Dependency & Package](#_1-dependency-and-package)
2. [Naming changes](#_2-naming-changes)
3. [Removed Features](#_3-removed-features)

### 1. Dependency and package

To allow having 1.x and 2.x work side by side, MultiViewAdapter 2.0 has been uploaded with new artifact name. Also the package names are changed to facilitate the same.

#### Gradle dependency

|Name|1.x|2.x|
|---|---|---|
|Adapter|com.github.devahamed:multi-view-adapter:1.3.0|dev.ahamed.mva2&#58;adapter:2.0.0|
|Databinding extension|com.github.devahamed:multi-view-adapter-databinding:1.3.0|dev.ahamed.mva2&#58;ext-databinding:2.0.0|
|Decorator extension|NA|dev.ahamed.mva2&#58;ext-decorator:2.0.0|
|RxDiffUtil extension|NA|dev.ahamed.mva2&#58;ext-diffutil-rx:2.0.0|

#### Package name changes

||1.x|2.x|
|---|---|----|
|Adapter|com.ahamed.multiviewadapter.* |mva2.adapter.* |
|Extension|com.ahamed.multiviewadapter.* |mva2.extension.* |

### 2. Naming Changes

We regret about introducing lot of ambiguity and confusion while naming our components. During 2.x rewrite, major time was spent on naming the classes and public api methods. One of the major change is DataManager variants are renamed to Section. Previously, DataManager variants are named like Data*Manager, like DataItemManager, DataListManager etc., Now they are named as *Section. (ItemSection, ListSection)

Component naming changes

|1.x|2.x|Comments|
|---|---|---|
|**Adapter**|
|RecyclerAdapter|MultiViewAdapter|
|SelectableAdapter|*Removed*|Use MultiViewAdapter instead|
|SimpleRecyclerAdapter|*Removed*|Coming as an extension|
|**DataManager**|
|BaseDataManager|Section|
|DataItemManager|ItemSection|
|DataListManager|ListSection|
|DataGroupManager|HeaderSection|
|**Decorator**|
|ItemDecorationManager|DecorationManager|
|ItemDecorator|Decorator|Previously it was an interface, now an abstract class|
|SimpleDividerDecoration|*Removed*|Use 'Decorator' extension|
|PositionTypeResolver|*Removed*|Merged into Decorator class|
|**Listeners**|
|ItemSelectionChangedListener|*Removed*|Use OnSelectionChangedListener instead|
|MultiSelectionChangedListener|OnSelectionChangedListener|
|**Others**|
|ItemBinderTouchCallback|ItemTouchCallback|
|**Enums**|
|SelectionMode|Mode|
|Expandable|Mode|

### 3. Removed Features

1. OnClickListener and OnLongClickListener has been removed from the library.
2. Set selected items feature has been removed.
3. SimpleRecyclerAdapter has been removed.