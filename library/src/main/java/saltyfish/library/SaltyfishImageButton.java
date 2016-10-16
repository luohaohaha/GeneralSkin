package saltyfish.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;
import saltyfish.library.utils.SaltyfishUtilTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishImageButton<br/>
 * Description: ${TODO} <br/>
 * Date: 2016/10/2 1:01 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishImageButton extends AppCompatImageButton implements ISaltyfishGeneralSkin {
    protected boolean isGeneralSkin = false;
    public SaltyfishImageButton(Context context) {
        this(context,null);
    }

    public SaltyfishImageButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaltyfishImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Saltyfish_Custom);
        isGeneralSkin = a.getBoolean(R.styleable.Saltyfish_Custom_generalSkin, false);
        onSkinChange();
        a.recycle();
    }
    public void setGeneralSkin(boolean generalSkin) {
        isGeneralSkin = generalSkin;
        if (!isGeneralSkin)
            return;
        onSkinChange();
    }

    @Override
    public void onSkinChange() {
        if (isInEditMode()) {
            return;
        }
        if (!isGeneralSkin)
            return;
        setSupportBackgroundTintList( getSaltyfishInstance(SaltyfishSkinColorTool.class).getDefaultColorStateList());
        Drawable drawable =   getSaltyfishInstance(SaltyfishSkinColorTool.class).changeDrawableColor(getDrawable());
        if (null == drawable)
            return;
        setImageDrawable(null);
        setImageDrawable(drawable);

    }

    public void setRipple(){
        SaltyfishUtilTool.setRipple(this);
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }
}
