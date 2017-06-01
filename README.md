# MultiViewAdapter

[ ![Core](https://api.bintray.com/packages/devahamed/MultiViewAdapter/multi-view-adapter/images/download.svg) ](https://bintray.com/devahamed/MultiViewAdapter/multi-view-adapter/_latestVersion)
[![GitHub license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/DevAhamed/MultiViewAdapter/blob/master/LICENSE)

![Alt text](/images/MultiViewAdapter-Article-1.jpg?raw=true)

Helper library for recyclerviews to create composable view holders without boilerplate code.

![Alt text](/images/MultiViewAdapter-gif.gif?raw=true)

## Gradle Dependency

The Gradle dependency is available via [JCenter](https://bintray.com/devahamed/MultiViewAdapter/multi-view-adapter/view).
JCenter is the default maven repository used by Android Studio.

The minimum API level supported by this library is API 9.

```gradle
dependencies {
	// ... other dependencies here
    compile 'com.github.devahamed:multi-view-adapter:1.1.0'
}
```


## Why this library?

Most of the android apps out there uses recyclerview to display content. 
As with any other system-level api, recyclerview api is also designed in a generic way. 
So it needs lot of code to be written for displaying a simple list. And it doubles, if you need to display multiple view types.
MultiViewAdapter library helps you in removing this boilerplate code while allowing you to truly re-use the viewholder code across various adapters.

There are many other libraries, which provides the same feature. But they do enforce the either or all of the following constraints :

1. Your POJO classes should extend/implement some Base classes. This forces us to make changes in model level.
2. Forces you to implement some boilerplate code - like managing view types by yourself.
3. Doesn't utilise diffutil or payloads from recyclerview api

Now the advantages of the MultiViewAdapter

1. No restrictions on POJO class' parent/hierarchy. Now, your model classes can truly reside inside data layer.
2. No need to cast your models, no need for switch/if-else cases when you are having multiple view types.
3. Takes advantage of diffutil and allows payload while notifying adapter.

## Key concepts

1. RecyclerAdapter - This is the adapter class. It can have multiple ItemBinder and DataManagers. It extends from official RecyclerView.Adapter
2. ItemBinder - ItemBinder's responsibility is to create and bind viewholders. ItemBinder has type parameter which accepts the  model class need to be displayed. ItemBinder needs to be registered inside RecyclerAdapter. ItemBinder can be registered with multiple adapters.
3. DataManger - Consider it as a List<E>. But internally it calls necessary animations when the list is modified. There are two DataManagers. <b>DataListManager</b> for list of items. <b>DataItemManager</b> for a single item (Header, Footer etc.,). 

You can read more about this library here in this [Medium article](https://medium.com/@DevAhamed/introducing-multiviewadapter-7f77e5758d3f).
<br/>

## Features

1. Supports different span count for different ItemBinders.
2. Adds different ItemDecoration for different ItemBinders.
3. Items can be selected - Single and Multiple selection options are available.
4. Out of the box support for DiffUtil.

## Usage
To get some idea about the MultiViewAdapter features kindly look at sample app code.
Also we have comprehensive wiki pages as well. Take a look at [JCenter](https://github.com/DevAhamed/MultiViewAdapter/wiki).


## Changelog
See the project's Releases page for a list of versions with their changelog. [View Releases](https://github.com/DevAhamed/MultiViewAdapter/releases)<br/>
If you watch this repository, GitHub will send you an email every time there is an update.


## Contribution
Contributing to this library is simple, 
1. Clone the repo
2. Make the changes
3. Make a pull request to develop branch
<br/><br/>The only requirement is whatever changes you make should be backward compatible. Also make sure its not a too specific feature which maynot be useful for everyone.
Kindly make sure your code is formatted with this codestyle - [Square Java code style](https://github.com/square/java-code-styles)


## Alternatives
This library may not suit your needs or imposes too many restrictions. In that case create an issue/feature request. Mean time check these awesome alternatives as well.
1. [MultipleViewTypesAdapter](https://github.com/yqritc/RecyclerView-MultipleViewTypesAdapter) - Original inspiration for this library.<br/>
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