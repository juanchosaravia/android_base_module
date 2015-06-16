# Android Base Module
Android Base classes to start quicky to develop an app.

## Gradle

### Add Repository:
Add the repository in your build.gradle:

```Groovy
buildscript {
    repositories {
        maven {url "http://dl.bintray.com/juanchosaravia/maven"}
        ...
    }
    dependencies {
        ...
    }
}

allprojects {
    repositories {
        maven {url "http://dl.bintray.com/juanchosaravia/maven"}
        ...
    }
}
```

### Include Dependency in your module:
```
compile 'com.droidcba.core:base:1.0.0'
```

### Otherwise, download it:

[ ![Download](https://api.bintray.com/packages/juanchosaravia/maven/android_base_module/images/download.svg) ](https://bintray.com/juanchosaravia/maven/android_base_module/_latestVersion)

