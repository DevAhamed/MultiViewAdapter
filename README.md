# MultiViewAdapter

[ ![Core](https://api.bintray.com/packages/devahamed/MultiViewAdapter/multi-view-adapter/images/download.svg) ](https://bintray.com/devahamed/MultiViewAdapter/multi-view-adapter/_latestVersion)
[![GitHub license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/DevAhamed/MultiViewAdapter/blob/master/LICENSE)

------

# Sample Project

You can download the latest sample APK from this repo here: https://github.com/DevAhamed/MultiViewAdapter/blob/master/sample/sample.apk

---


# Gradle Dependency

The Gradle dependency is available via [jCenter](https://bintray.com/devahamed/MultiViewADapter/multi-view-adapter/view).
jCenter is the default Maven repository used by Android Studio.

The minimum API level supported by this library is API 14.

```gradle
dependencies {
	// ... other dependencies here
    compile 'com.github.devahamed:multi-view-adapter:0.6.0'
}
```

---


# Changelog

See the project's Releases page for a list of versions with their changelog.

### [View Releases](https://github.com/DevAhamed/MultiViewAdapter/releases)

If you Watch this repository, GitHub will send you an email every time there is an update.

---


# Why this library?

Most of the android apps out there uses recyclerview to display content. As with any other system-level api, recyclerview api is also designed in a generic way. So it needs lot of boilerplate code to be written.

If the app contains multiple view types, the boilerplate code doubles. MultiViewAdapter library helps you in removing this bolierplate code while allowing you to truly re-use the viewholder code across multiple adapters.

There are many other libraries, which provides the same feature. But they do enforce the either or all of the following constraints :

1. Your POJO classes should extend/implement some Base classes. This forces us to make changes in model level, sometimes this is not acceptable.
2. Forces you to implement some boilerplate code - like managing view types by yourself. (This library too have one redundant method. More about this later)
3. Doesn't utilise diffutil or payloads from recyclerview api

Now the advantages of the MultiViewAdapter

1. No restrictions on POJO class' parent/hierarchy. Now, your model classes can truly reside inside data layer.
2. No need to cast your models, no need for switch/if-else cases when you are having multiple view types.
3. Takes advantage of diffutil and allows payload while notifying adapter.

The MultiViewAdapter itself has own cons.  As mentioned earlier, you need to override one boilerplate method to differentiate different models used inside same adapter. We did the best to reduce the boilerplate methods except this one.

## Limitations

You can not have a different viewholder for the same model inside one adapter. For example, if the adapter shows list of 'Car', then 'Car' can have only one viewholder 

---


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