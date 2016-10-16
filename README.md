动态换肤
=====
1、此工程只是简单实现了一种动态换肤的方式，具体其他方式还有很多，最主要的实现还是着色部分，`SaltyfishSkinColorTool`里面实现了图片着色。

2、方法介绍

SaltyfishSkinColorTool类里面` changeDrawableColor`里面用 `DrawableCompat`进行图片着色，这里我尝试过全部用` DrawableCompat.setTintList`来实现，但是在5.0以下的版本似乎不可行，或许我的使用方式有问题也有可能。常规控件直接继承Appcompat系列控件即可，调用`setSupportBackgroundTintList`就可以实现换肤。自定义控件例如 LinearLayout、RelativeLayout、FrameLayout等则需要自己实现TintableBackgroundView接口，利用AppCompatBackgroundHelper来进行控件背景着色。

`getDefaultColorStateList`和`getSwitchColorStateList`方法可以根据自己的需求去变更，`getPressedColor`我个人觉得有点深了，可以适当改变一下偏移量。

使用方法非常简单，只需要在控件加自定义属性`generalSkin="true"`(换背景和字体颜色)或者`generalSkinText="true"`(只换字体颜色)。如果没有style的控件还可以使用`style=@style/generalSkin`、`style=@style/generalSkinText`、`style=@style/generalSkinBackground`

Listview、scrollview、recyclerview、viewpager的换肤主要换EdgeEffect颜色 5.0之后才会有效果。

----------------------------------------------------------

<font color="blue">另外从网易云音乐偷了几张图，换肤选择颜色那一些色块和挖空的背景图片，感谢网易。</font>

[颜色选择条控件详见我另外一个工程](https://github.com/luohaohaha/LinearColorPicker)

Material样式的Radiobutton、CheckBox、Seekbar来自[MaterialDesignLibrary](https://github.com/navasmdc/MaterialDesignLibrary)，感谢原作者。


----------------------------------------------------------
<font color="red">强调一下，控件无背景的情况`setSupportBackgroundTintList`是无效的。</font>

----------------------------------------------------------
另外简单放几张图

![](https://github.com/luohaohaha/GeneralSkin/blob/master/device-2016-10-12-142841.png)