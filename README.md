# Carousel

轮播图

## Gradle

在项目的build.gradle中,添加:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在使用库的module中添加,为避免重复引用,推荐使用exclude::
```groovy
dependencies {
    compile 'com.android.support:appcompat-v7:latest.version'
    compile ('com.github.shucc:Carousel:v1.7') {
        exclude group: 'com.android.support', module: 'appcompat-v7'
    }
}
```

## Attributes

|name|format|description|
|:---:|:---:|:---:|
| carousel_delayTime | integer | 自动切换间隔时间,默认5s
| carousel_auto_switch | boolean | 是否自动切换,默认不自动切换
| carousel_can_loop | boolean | 能否循环切换,默认可以循环切换
| carousel_show_indicator | boolean | 是否显示指示符,默认不显示
| carousel_indicator_width | dimension | 指示符宽度,默认5dp
| carousel_indicator_height | dimension | 指示符高度,默认5dp
| carousel_indicator_padding | dimension | 指示符之间间距,默认8dp
| carousel_indicator_margin_bottom | dimension | 指示符距离底部距离,默认16dp
| carousel_indicator_drawable_selected | reference | 指示符选中图标,默认图标为红色小圆点
| carousel_indicator_drawable_unselected | reference | 指示符未选中图标,默认图标为白色小圆点
| carousel_show_title | boolean | 是否显示标题,默认不显示
| carousel_title_size | dimension | 标题文字大小,默认16sp
| carousel_title_color | color | 标题文字颜色,默认白色
| carousel_title_margin_bottom | dimension | 标题距离底端距离，默认30dp

## How do I use Carousel?

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <org.cchao.carousel.CarouselView
        android:id="@+id/carouselView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:carousel_indicator_drawable_selected="@drawable/ic_indicator_selected"
        app:carousel_indicator_drawable_unselected="@drawable/ic_indicator_unselected"
        app:carousel_indicator_width="24dp"
        app:carousel_indicator_height="2dp"
        app:carousel_indicator_padding="8dp"
        app:carousel_indicator_margin_bottom="8dp"
        app:carousel_title_margin_bottom="18dp"/>

</LinearLayout>
```

```java
        carouselView.with(this)
                .setImageUrls(imageUrls)
                .setTitles(titles)
                .setDealyTime(4 * 1000)
                .setShowIndicator(true)
                .setShowTitle(true)
                .setAutoSwitch(true)
                .setImageLoaderListener(new ImageloaderListener() {
                    @Override
                    public void loadImage(Context context, ImageView imageView, int position) {
                        Glide.with(context)
                                .load(imageUrls.get(position))
                                .into(imageView);
                    }
                })
                .start();
```

## Demo

![](https://raw.githubusercontent.com/shucc/Carousel/master/demo/demo1.gif)
![](https://raw.githubusercontent.com/shucc/Carousel/master/demo/demo2.gif)
![](https://raw.githubusercontent.com/shucc/Carousel/master/demo/demo3.gif)
