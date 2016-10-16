package saltyfish.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EdgeEffect;
import android.widget.ScrollView;

import java.lang.reflect.Field;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logE;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishScrollView<br/>
 * Description: ${TODO} <br/>
 * Date: 2016/10/2 0:52 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */
public class SaltyfishScrollView extends ScrollView implements ISaltyfishGeneralSkin {

    protected boolean isGeneralSkin = true;

    public SaltyfishScrollView(Context context) {
        this(context,null);
    }

    public SaltyfishScrollView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SaltyfishScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        if (null == attrs)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Saltyfish_Custom);
        isGeneralSkin = a.getBoolean(R.styleable.Saltyfish_Custom_generalSkin, true);
        a.recycle();
    }

    @Override
    public void onSkinChange() {
        if (!isGeneralSkin)
            return;
        setEdgeGlowColor();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onSkinChange();
    }

    public void setGeneralSkin(boolean generalSkin) {
        isGeneralSkin = generalSkin;
        if(isGeneralSkin){
            setEdgeGlowColor();
        }
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private  void setEdgeGlowColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                final Class<?> clazz = ScrollView.class;
                for (final String name : new String[] {"mEdgeGlowTop", "mEdgeGlowBottom"}) {
                    final Field field = clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    ((EdgeEffect) field.get(this)).setColor(getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor());
                }
            } catch (final Exception ignored) {
               logE(ignored.toString());
            }
        }

    }
}
