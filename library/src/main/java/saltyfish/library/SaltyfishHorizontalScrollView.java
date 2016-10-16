package saltyfish.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EdgeEffect;
import android.widget.HorizontalScrollView;

import java.lang.reflect.Field;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logE;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

class SaltyfishHorizontalScrollView extends HorizontalScrollView implements ISaltyfishGeneralSkin {

    protected boolean isGeneralSkin = true;

    public SaltyfishHorizontalScrollView(Context context) {
        this(context,null);
    }

    public SaltyfishHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaltyfishHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
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
        if (!isGeneralSkin)
            return;
        onSkinChange();
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private  void setEdgeGlowColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                final Class<?> clazz = HorizontalScrollView.class;
                for (final String name : new String[] {"mEdgeGlowLeft", "mEdgeGlowRight"}) {
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
