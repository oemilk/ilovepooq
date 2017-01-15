
ilovepooq
====================

This is a project for 1st code review interview.

![alt tag](https://github.com/oemilk/images/blob/master/ilovepooq_screenshot_01.png)
![alt tag](https://github.com/oemilk/images/blob/master/ilovepooq_screenshot_02.png)

This can parse HTML by using the [jsoup] java library and gets content data from [SBS PD Note] website.<br />
It use an [android universal image loader] java library for loading images.

## Parse HTML

Parsing a below HTML code structure.<br />
If the structure is changed, codes should be modified for getting correct data.

```test_html_parsing

    <li name="endListLi" class="pdnl_cont" style="display:none;">
        <a href="hyperlink_01" class="pdnl_link">
            <strong class="pdnl_name">name_01</strong>
            <img src="image_url_01" alt="alt_01" class="pdnl_logo_img" width="180" height="180" pagespeed_url_hash="hash_01" onload="pagespeed.CriticalImages.checkImageForCriticality(this);">
            <i class="icn pdnl_mask"></i>
        </a>
    </li>

    <li name="endListLi" class="pdnl_cont" style="display:none;">
        <a href="hyperlink_02" class="pdnl_link">
            <strong class="pdnl_name">name_02</strong>
            <img src="image_url_02" alt="alt_02" class="pdnl_logo_img" width="180" height="180" pagespeed_url_hash="hash_02" onload="pagespeed.CriticalImages.checkImageForCriticality(this);">
            <i class="icn pdnl_mask"></i>
        </a>
    </li>

```

## Generate ContentInfoModel

It generates a data model from a parsing data.<br />
This is also modified in case of parsing other structers of HTML.

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
This is also modified in case of parsing other structers of HTML.

```android_universal_image_loader
dependencies {
    ...
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
    ...
}
    
```

## Material design

This project uses a [material design] for presenting list and grid UI.<br />

```material_design
dependencies {
    ...
    compile 'com.android.support:appcompat-v7:23.0.1'
    compile 'com.android.support:cardview-v7:23.0.1'
    compile 'com.android.support:recyclerview-v7:23.0.1'
    ...
}
```

If you have any questions about this project.
Please send an email to "oemilk@naver.com".

[jsoup]: https://jsoup.org/
[SBS PD Note]: http://w3.sbs.co.kr/pdNote/pdNoteProgram.do?pdnote_div=p_program
[android universal image loader]: https://github.com/nostra13/Android-Universal-Image-Loader
[material design]: https://www.google.com/design/spec/material-design/introduction.html
