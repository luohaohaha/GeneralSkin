package saltyfish.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.ListView;

import java.lang.reflect.Field;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logE;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

public class SaltyfishListView extends ListView implements ISaltyfishGeneralSkin {

    protected boolean isGeneralSkin = true;

    public SaltyfishListView(Context context) {
        this(context,null);
    }

    public SaltyfishListView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SaltyfishListView(Context context, AttributeSet attrs, int defStyle) {
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

    public void setGeneralSkin(boolean generalSkin) {
        isGeneralSkin = generalSkin;
        if(isGeneralSkin)
            onSkinChange();
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private  void setEdgeGlowColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            EdgeEffect edgeEffectTop = new EdgeEffect(getContext());
            edgeEffectTop.setColor(getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor());
            try {
                for (String fieldName :new String[]{"mEdgeGlowTop","mEdgeGlowBottom"}){

                    Field field= AbsListView.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(this,edgeEffectTop);
                }
            } catch (final Exception ignored) {
               logE(ignored.toString());
            }
        }

    }
}
