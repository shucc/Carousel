# Carousel

绑定Activity/Fragment生命周期的轮播图

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
    implementation 'com.android.support:appcompat-v7:latest.version'
    implementation ('com.github.shucc:Carousel:v1.9.1') {
        exclude group: 'com.android.support', module: 'appcompat-v7'
    }
}
```

## Attributes

|name|format|description|
|---|---|---|
| app:carousel_delayTime | integer | 自动切换间隔时间,默认5s
| app:carousel_auto_switch | boolean | 是否自动切换,默认不自动切换
| app:carousel_can_loop | boolean | 能否循环切换,默认可以循环切换
| app:carousel_show_indicator | boolean | 是否显示指示符,默认不显示
| app:carousel_indicator_width | dimension | 统一指示符宽度,默认5dp
| app:carousel_indicator_height | dimension | 统一指示符高度,默认5dp
| app:carousel_indicator_selected_width | dimension | 当前选中指示符宽度,默认0dp,优先级高于统一指示符宽度
| app:carousel_indicator_selected_height | dimension | 当前选中指示符高度,默认0dp,优先级高于统一指示符高度
| app:carousel_indicator_unselected_width | dimension | 未选中指示符宽度,默认0dp,优先级高于统一指示符宽度
| app:carousel_indicator_unselected_height | dimension | 未选中指示符高度,默认0dp,优先级高于统一指示符高度
| app:carousel_indicator_padding | dimension | 指示符之间间距,默认8dp
| app:carousel_indicator_margin_bottom | dimension | 指示符距离底部距离,默认16dp
| app:carousel_indicator_drawable_selected | reference | 指示符选中图标,默认图标为红色小圆点
| app:carousel_indicator_drawable_unselected | reference | 指示符未选中图标,默认图标为白色小圆点

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
        app:carousel_indicator_margin_bottom="8dp"/>

</LinearLayout>
```
```java
public class MyAdapter implements CarouselAdapter {

    private List<MyModel> data;

    public MyAdapter(List<MyModel> data) {
        this.data = data;
    }

    @Override
    public View getView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        ImageView imgCarousel = view.findViewById(R.id.img_carousel);
        Glide.with(parent.getContext())
                .load(data.get(position).getImageUrl())
                .into(imgCarousel);
        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

}
```
```java
carouselView.with(this)
        .setAdapter(new MyAdapter(data))
        .setDelayTime(4 * 1000)
        .setShowIndicator(true)
        .setAutoSwitch(false)
        .setCanLoop(false)
        .start();
```
轮播图切换已绑定Activity/Fragment生命周期，若设置了轮播图自动切换，不需要在Activity/Fragment的onResume等方法中手动设置开始与暂停播放轮播图，但是Fragment之间切换需要在onHiddenChanged设置自动切换的轮播图
```java
@Override
public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (hidden) {
        carouselView.stop();
    } else {
        carouselView.resume();
    }
}
```

## Demo

![](https://raw.githubusercontent.com/shucc/Carousel/master/demo/demo1.gif)
![](https://raw.githubusercontent.com/shucc/Carousel/master/demo/demo2.gif)

## 更新说明

### v1.9.1
    修改初始化时ViewPager方法onAttachedToWindow中获取adapter为空情况

#### v1.9
    修改RecyclerView配合CarouselView使用时动画停止bug

#### v1.8
    轮播图展示使用Adapter自定义界面

#### v1.7.1
    新增指示器配置选项
  * 添加选中与未选中指示器的宽高设置
  * 添加设置轮播图切换动画方法
