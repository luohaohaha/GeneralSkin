package saltyfish.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;
import saltyfish.library.utils.SaltyfishUtilTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishRadioButton<br/>
 * Description: ${TODO} <br/>
 * Date: 2016/10/4 0:58 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishRadioButton extends AppCompatRadioButton implements ISaltyfishGeneralSkin {
    protected boolean isGeneralSkin = false;
    public SaltyfishRadioButton(Context context) {
        this(context,null);
    }

    public SaltyfishRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.radioButtonStyle);
    }

    public SaltyfishRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
        setRipple();
        if (!isGeneralSkin)
            return;
        setSupportButtonTintList( getSaltyfishInstance(SaltyfishSkinColorTool.class).getSwitchColorStateList());
    }

    public void setRipple(){
        SaltyfishUtilTool.setRipple(this);
    }


    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }
}
