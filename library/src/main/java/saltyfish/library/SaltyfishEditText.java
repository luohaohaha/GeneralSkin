package saltyfish.library;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;
import saltyfish.library.utils.SaltyfishUtilTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishEditText<br/>
 * Description: 输入框基类 <br/>
 * Date: 2016/10/2 1:00 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishEditText extends AppCompatEditText implements ISaltyfishGeneralSkin {
    public SaltyfishEditText(Context context) {
        this(context,null);
    }

    public SaltyfishEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public SaltyfishEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onSkinChange();

    }

    @Override
    public void onSkinChange() {
        setSupportBackgroundTintList(getSaltyfishInstance(SaltyfishSkinColorTool.class).getDefaultColorStateList());
//        setTextColor( CMInstanceManager.getInstance().getManagerInstance(CMSkinColorTool.class).getDefaultColorStateList());
        SaltyfishUtilTool.setCursorColorAndVisible(this);
    }

    @Override
    public boolean isGeneralSkin() {
        return true;
    }
}
