package saltyfish.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;
import saltyfish.library.utils.SaltyfishUtilTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishButton<br/>
 * Description: 按钮基类 <br/>
 * Date: 2016/10/2 0:52 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishButton extends AppCompatButton implements ISaltyfishGeneralSkin {

    protected boolean isGeneralSkin = false;

    private boolean isGeneralSkinText=false;

    public SaltyfishButton(Context context) {
        this(context,null);
    }

    public SaltyfishButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaltyfishButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Saltyfish_Custom);
        isGeneralSkin = a.getBoolean(R.styleable.Saltyfish_Custom_generalSkin, false);
        isGeneralSkinText = a.getBoolean(R.styleable.Saltyfish_Custom_generalSkinText, false);
        onSkinChange();
        a.recycle();
    }

    public void setGeneralSkin(boolean generalSkin,boolean generalSkinText ) {
        isGeneralSkin = generalSkin;
        isGeneralSkinText=generalSkinText;
        if (isGeneralSkin||isGeneralSkinText) {
            onSkinChange();
        }
    }

    @Override
    public void onSkinChange() {

        if (isGeneralSkin) {
            setSupportBackgroundTintList(getSaltyfishInstance(SaltyfishSkinColorTool.class).getDefaultColorStateList());

        }
        if (isGeneralSkinText) {
            setTextColor(getSaltyfishInstance(SaltyfishSkinColorTool.class).getDefaultColorStateList());
        }
//        setSupportBackgroundTintList( CMInstanceManager.getInstance().getManagerInstance(CMSkinColorTool.class).getDefaultColorStateList());
//        Palette.Swatch swatch=new Palette.Swatch( CMInstanceManager.getInstance().getManagerInstance(CMSkinColorTool.class).getSkinColor(),0);
//        setTextColor(swatch.getBodyTextColor());
    }


    public void setRipple(){
        SaltyfishUtilTool.setRipple(this);
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }
}
