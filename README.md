[ ![Download](https://api.bintray.com/packages/vshmakin/maven/rx-vmt/images/download.svg) ](https://bintray.com/vshmakin/maven/rx-vmt/_latestVersion)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Licence](https://img.shields.io/badge/Licence-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

# RxViewModelTask
Simplified task long task execution by using Android architecture [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel) and [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) with [RxAndroid](https://github.com/ReactiveX/RxAndroid).
You can simply implement Observable/Flowable/Maybe/Single/Completable objects and pass them to RxViewModelTask. It will be executed with respect to Android Lifecycle)

Download
--------

Gradle:

```groovy
implementation 'com.github.VyacheslavShmakin:rx-vmt:1.2.1'
```

Maven:

```xml
<dependency>
    <groupId>com.github.VyacheslavShmakin</groupId>
    <artifactId>rx-vmt</artifactId>
    <version>1.2.1</version>
    <type>aar</type>
</dependency>
```
Usage
-----
``` kotlin
// "this" - Fragment or FragmentActivity will be used as ViewModelStoreOwner and LifeCycleOwner
RxViewModelTask.create(this, "Unique String key in Fragment/FragmentActivity")
  .init(observable, observer, true)
//.init(flowable, observer, true)
//.init(single, singleObserver)
//.init(maybe, maybeObserver)
//.init(completable, completableObserver)
// You're able to use "restart" method also
```
- **init** will execute Observable/Flowable/Maybe/Single/Completable object only once.
Even if you call this multiple times the last callback method will be called.
- **restart** will execute Observable/Flowable/Maybe/Single/Completable object every restart call.
- Observable and Flowable implementation are able to collect all items when ViewModel inactive and pass them when ViewModel became active.
To use this feature just enable the flag *collectAll* when calling **init** or **restart** method. This option is disabled by default.
Please be careful by using this option to avoid UI freezes when you passing a lot of data in a short time

Imported libraries with initial version
--------
```groovy
implementation 'com.android.support:appcompat-v7:28.0.0'
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
implementation 'io.reactivex.rxjava2:rxjava:2.2.9'
implementation 'android.arch.lifecycle:extensions:1.1.1'
```
