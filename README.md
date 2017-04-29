# MultiViewAdapter

[ ![Core](https://api.bintray.com/packages/devahamed/MultiViewAdapter/multi-view-adapter/images/download.svg) ](https://bintray.com/devahamed/MultiViewAdapter/multi-view-adapter/_latestVersion)
[![GitHub license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/DevAhamed/MultiViewAdapter/blob/master/LICENSE)


Helper library for recyclerviews to create composable view holders without boilerplate code.


## Gradle Dependency

The Gradle dependency is available via [jCenter](https://bintray.com/devahamed/MultiViewADapter/multi-view-adapter/view).
jCenter is the default Maven repository used by Android Studio.

The minimum API level supported by this library is API 14.

```gradle
dependencies {
	// ... other dependencies here
    compile 'com.github.devahamed:multi-view-adapter:0.6.0'
}
```


## Why this library?

Most of the android apps out there uses recyclerview to display content. As with any other system-level api, recyclerview api is also designed in a generic way. So it needs lot of boilerplate code to be written for displaying a simple list.

If the app contains multiple view types in the same list, the boilerplate code doubles. MultiViewAdapter library helps you in removing this boilerplate code while allowing you to truly re-use the viewholder code across multiple adapters.

There are many other libraries, which provides the same feature. But they do enforce the either or all of the following constraints :

1. Your POJO classes should extend/implement some Base classes. This forces us to make changes in model level.
2. Forces you to implement some boilerplate code - like managing view types by yourself.
3. Doesn't utilise diffutil or payloads from recyclerview api

Now the advantages of the MultiViewAdapter

1. No restrictions on POJO class' parent/hierarchy. Now, your model classes can truly reside inside data layer.
2. No need to cast your models, no need for switch/if-else cases when you are having multiple view types.
3. Takes advantage of diffutil and allows payload while notifying adapter.

## Usage
To get some idea about the MultiViewAdapter features kindly look at sample implementations.

## List & Grids
If you are using the linear layout manager for recyclerview, extend the adapter from RecyclerListAdapter. If you are using the grid layout manager use RecyclerGridAdapter as parent for the adapter.

## Getting models inside ViewHolder


## Limitations
1. While the library allows to reuse the viewholders, you still need to override a method to identify models used (ie., instanceOf check).
2. You can not have a different viewholder for the same model inside one adapter. For example, if the adapter shows list of 'Car', then 'Car' can have only one viewholder


## Changelog
See the project's Releases page for a list of versions with their changelog. [View Releases](https://github.com/DevAhamed/MultiViewAdapter/releases)<br/>
If you watch this repository, GitHub will send you an email every time there is an update.


## Contribution
Contributing to this library is simple, 
1. Clone the repo
2. Make the changes
3. Make a pull request to develop branch
Kindly make sure your code is formatted with this codestyle - [Square Java code style](https://github.com/square/java-code-styles)
<br/>


## Alternatives
This library may not suit your needs or imposes too many restrictions. In that case create an issue/feature request. In the mean time check these awesome alternatives as well.
1. [MultipleViewTypesAdapter](https://github.com/yqritc/RecyclerView-MultipleViewTypesAdapter) - Original inspiration for this library. By inspiration, we mean that parts of code were "re-used"<br/>
2. [AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates)
3. [Groupie](https://github.com/lisawray/groupie)
4. [Epoxy](https://github.com/airbnb/epoxy)


## License
```
Copyright 2017 Riyaz Ahamed

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```