# Android Base Module
Android Base classes to start quickly to develop an app.

[![Build Status](https://travis-ci.org/juanchosaravia/android_base_module.svg?branch=master)](https://travis-ci.org/juanchosaravia/android_base_module)


## Gradle

### Add Repository:
This library is already available from jcenter, so just make sure to have it included.
As this lib depends on Icepick and it's published from another repo, we have to add it here:

```Groovy
allprojects {
    repositories {
        jcenter()
        maven {url "https://clojars.org/repo/"}
        ...
    }
}
```

### Include Dependency in your module:
```
compile 'com.droidcba.core:base:1.0.+'
```

#### Otherwise, download it:

[ ![Download](https://api.bintray.com/packages/juanchosaravia/maven/android_base_module/images/download.svg) ](https://bintray.com/juanchosaravia/maven/android_base_module/_latestVersion)


## Dependencies
 - support-v4
 - appcompat-v7
 - design
 - icepick
 - butterknife
