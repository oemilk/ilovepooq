
ilovepooq
====================

This is a project for 1st code review interview.

![alt tag](https://github.com/oemilk/images/blob/master/ilovepooq_screenshot_01.png)
![alt tag](https://github.com/oemilk/images/blob/master/ilovepooq_screenshot_02.png)

## Requirements

- JDK 1.8
- [Android SDK](http://developer.android.com/sdk/index.html)
- Target SDK version : Android M [(API 25) ](http://developer.android.com/tools/revisions/platforms.html)
- Minimun SDK version : Android L [(API 21)](http://developer.android.com/tools/revisions/platforms.html)
- Latest Android SDK Tools and build tools.

## Libraries and tools included:

- Support libraries
- RecyclerViews and CardViews
- [RxJava 2](https://github.com/ReactiveX/RxJava) and [RxAndroid 2](https://github.com/ReactiveX/RxAndroid)
- [Jsoup](https://jsoup.org/)
- [Retrofit 2](http://square.github.io/retrofit/)
- [Dagger 2](http://google.github.io/dagger/)
- [Butterknife](https://github.com/JakeWharton/butterknife)
- [Glide](https://github.com/bumptech/glide)
- [google gson](https://github.com/google/gson)
- Unit tests with [Mockito](http://mockito.org/)
- [Checkstyle](http://checkstyle.sourceforge.net/), [PMD](https://pmd.github.io/) and [Findbugs](http://findbugs.sourceforge.net/) for code analysis

## Architecture

![](https://github.com/oemilk/images/blob/master/ilovepooq_mvp.png)

It demonstrates the architecture, tools and guidelines that we use when developing for the Android platform (https://github.com/ribot/android-guidelines)

This project follows ilovepooq's Android architecture guidelines that are based on [MVP (Model View Presenter)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter). Read more about them [here](https://github.com/googlesamples/android-architecture).

![](https://github.com/oemilk/images/blob/master/ilovepooq_mvp_logic.png)

## Code Quality

This project integrates a combination of unit tests and code analysis tools.

![](https://github.com/oemilk/images/blob/master/ilovepooq_code%20quality.png)

### Tests

Run unit tests

```
./gradlew test
```

### Code Analysis tools

[Run code analysis tools](https://github.com/oemilk/ilovepooq/wiki/3.-Check-Code-qualities#33-run-code-analysis-tools)

```
./gradlew check
```

* [Checkstyle](https://github.com/oemilk/ilovepooq/wiki/3.-Check-Code-qualities#321-checkstyle): It ensures that the code style follows [our Android code guidelines](https://github.com/oemilk/ilovepooq/wiki/2.-Code-guidelines). See our [checkstyle config file](https://github.com/oemilk/ilovepooq/blob/master/Ilovepooq/config/quality/checkstyle/checkstyle-config.xml).

* [Findbugs](https://github.com/oemilk/ilovepooq/wiki/3.-Check-Code-qualities#322-findbugs): This tool uses static analysis to find bugs in Java code. Unlike PMD, it uses compiled Java bytecode instead of source code. See [this project's Findbugs ruleset](https://github.com/oemilk/ilovepooq/blob/master/Ilovepooq/config/quality/findbugs/android-exclude-filter.xml).

* [PMD](https://github.com/oemilk/ilovepooq/wiki/3.-Check-Code-qualities#323-pmd): It finds common programming flaws like unused variables, empty catch blocks, unnecessary object creation, and so forth. See [this project's PMD ruleset](https://github.com/oemilk/ilovepooq/blob/master/Ilovepooq/config/quality/pmd/pmd-ruleset.xml).

## Guidelines

You can see a guildlines about project and code at the [wiki page](https://github.com/oemilk/ilovepooq/wiki).

If you have any questions about this project.
Please send an email to "oemilk@naver.com".

[SBS PD Note]: http://w3.sbs.co.kr/pdNote/pdNoteProgram.do?pdnote_div=p_program
