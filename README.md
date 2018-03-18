[![Build Status](https://travis-ci.org/codetojoy/WarO_Java_Spock.svg?branch=master)](https://travis-ci.org/codetojoy/WarO_Java_Spock)

WarO_Java_Spock
=========

a Java submission for War-O as a code exercise

- this project now uses Java 8 streams
- uses [Spock](http://spockframework.org/) for tests
- goals include: a functional style, immutable objects, minimal use of for-loops
- Spring's Java configuration is used to configure players

To Build:
---------

download Gradle at http://gradle.org

useful commands:

- gradle clean test
- gradle run
- gradle build

See test output in ~build/reports/tests/index.html

See executable zip in ~/build/distributions

To Run:
---------

- configure src/main/java/org/peidevs/waro/config/Config.java
- gradle run

Rules:
---------

Rules are [here](Rules.md).
