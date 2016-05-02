
ilovepooq
====================

This is a project for 1st code review interview.

![alt tag](https://github.com/oemilk/images/blob/master/ilovepooq_screenshot_01.png)
![alt tag](https://github.com/oemilk/images/blob/master/ilovepooq_screenshot_02.png)

This can parse HTML by using the [jsoup] java library and gets content data from [SBS] website.<br />
It use an [android universal image loader] java library for loading images.

## Parse HTML

Parsing a below HTML code structure.<br />
If the structure is changed, codes should be modified for getting correct data.

```test_html_parsing
<div class="sbsmd_mcmpt_w">

    <a id="publist4" style="display: block;" href="hyperlink_01" target="_self" class="sit_cont"
       title="title_01">
        <img src="image_url_01" width="198" height="121" class="sit_img" alt="alt_01">
        <strong class="sit_tit_w">
            <span class="sit_tit">title_01</span>
        </strong>
    </a>

</div>

<div class="sbsmd_mcmpt_w">

    <a href="hyperlink_02" class="sit_cont" title="title_02">
        <img src="image_url_02" width="198" height="121" class="sit_img" alt="alt_02">
        <strong class="sit_tit_w">
            <span class="sit_tit">title_02</span>
        </strong>
    </a>

</div>

```

## Generate ContentInfoModel

It generates a data model from a parsing data.<br />
This is also modified in case of parsing other struecters of HTML.

```content_info_model
public class ContentInfoModel {

    private String imageURL;
    private String alt;
    private String title;
    private String hyperlink;
    private int loadingResult;
    
```

## Load images

As you know, Android should consider many case of logics and performance for loading images.<br />
So this project use [android universal image loader].
This is also modified in case of parsing other struecters of HTML.

```android_universal_image_loader
dependencies {
    ...
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
    ...
}
    
```

## Material design

This project uses a [material desing] for presenting list and grid UI.<br />

```material_design
dependencies {
    ...
    compile 'com.android.support:appcompat-v7:23.0.1'
    compile 'com.android.support:cardview-v7:23.0.1'
    compile 'com.android.support:recyclerview-v7:23.0.1'
    ...
}
```

If you have any question about this project.
Please send an email to "oemilk@naver.com".

[jsoup]: https://jsoup.org/
[SBS]: http://www.sbs.co.kr/main.do
[android universal image loader]: https://github.com/nostra13/Android-Universal-Image-Loader
[material desing]: https://www.google.com/design/spec/material-design/introduction.html
