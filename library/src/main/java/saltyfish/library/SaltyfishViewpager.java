package saltyfish.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.EdgeEffect;

import java.lang.reflect.Field;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logE;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishViewpager<br/>
 * Description: Viewpager基类 <br/>
 * Date: 2016-7-16 9:30 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 */
public class SaltyfishViewpager extends ViewPager implements ISaltyfishGeneralSkin {

    protected boolean isGeneralSkin = true;

    public SaltyfishViewpager(Context context) {
        this(context,null);
    }

    public SaltyfishViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    public void setGeneralSkin(boolean generalSkin) {
        isGeneralSkin = generalSkin;
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private  void setEdgeGlowColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                final Class<?> clazz = ViewPager.class;
                for (final String name : new String[] {"mLeftEdge", "mRightEdge"}) {
                    final Field field = clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    final Object edge = field.get(this); // android.support.v4.widget.EdgeEffectCompat
                    final Field fEdgeEffect = edge.getClass().getDeclaredField("mEdgeEffect");
                    fEdgeEffect.setAccessible(true);
                    ((EdgeEffect) fEdgeEffect.get(edge)).setColor(getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor());
                }
            } catch (final Exception ignored) {
               logE(ignored.toString());
            }
        }

    }

}
