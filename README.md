### Android Simple Time

A simple [ISO_8601](https://en.wikipedia.org/wiki/ISO_8601) time formatter, parser and conversion utility for Android.  Parsing and conversions and handled by the [SimpleTimeFormatter](http://developer.android.com/reference/java/text/SimpleDateFormat.html) utility and formatting is delegated to [PrettyTime](http://www.ocpsoft.org/prettytime/).

### Installation

[![Download](https://api.bintray.com/packages/mrkcsc/maven/com.miguelgaeta.simple-time/images/download.svg)](https://bintray.com/mrkcsc/maven/com.miguelgaeta.simple-time/_latestVersion)

```groovy

compile 'com.miguelgaeta.android-simple-time:simple-time:1.0.0'

```

When including in your gradle file you must add this exclusion block to your `android` namespace.

```groovy

packagingOptions {

    // Pretty time exclusion.
    exclude 'META-INF/INDEX.LIST'
}

```

### Configuration

TODO

```java

// TODO

```

### Usage

TODO

```java

// TODO

```

### License

*Copyright 2016 Miguel Gaeta*

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
