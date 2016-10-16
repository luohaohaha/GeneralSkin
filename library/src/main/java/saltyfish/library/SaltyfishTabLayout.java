package saltyfish.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logE;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;
public class SaltyfishTabLayout extends TabLayout implements ISaltyfishGeneralSkin {

    private boolean isGeneralSkin = false;

    public SaltyfishTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public SaltyfishTabLayout(Context context) {
        this(context,null);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        if (null == attrs)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Saltyfish_Custom);
        isGeneralSkin = a.getBoolean(R.styleable.Saltyfish_Custom_generalSkin, false);
        a.recycle();
    }

    @Override
    public void onSkinChange() {

        if(!isGeneralSkin)
            return;
        try {
            Field mTabStripField=TabLayout.class.getDeclaredField("mTabStrip");
            mTabStripField.setAccessible(true);
            Object mTabStrip=mTabStripField.get(this);
            Class mTabStripClass=mTabStrip.getClass();
            Method selectIndicatorColorMethod=mTabStripClass.getDeclaredMethod("setSelectedIndicatorColor",new Class[]{int.class});
            selectIndicatorColorMethod.setAccessible(true);
            int skinColor=getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor();
            selectIndicatorColorMethod.invoke(mTabStrip, skinColor);
            ColorStateList colorStateList=getTabTextColors();
            setTabTextColors(colorStateList.getDefaultColor(),skinColor);
        } catch (Exception e) {
            logE(e.toString());
        }
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }
}
