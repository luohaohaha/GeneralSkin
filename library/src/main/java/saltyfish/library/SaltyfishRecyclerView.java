package saltyfish.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.EdgeEffect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logE;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishRecyclerView<br/>
 * Description: RecyclerView基类 <br/>
 * Date: 2016-7-13 19:30 <br/>
 * <p/>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p/>
 */
public class SaltyfishRecyclerView extends RecyclerView implements ISaltyfishGeneralSkin {
    protected boolean isGeneralSkin = true;

    public SaltyfishRecyclerView(Context context) {
        super(context);
        initAttrs(context,null);
    }

    public SaltyfishRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public SaltyfishRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        if (null == attrs)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Saltyfish_Custom);
        isGeneralSkin = a.getBoolean(R.styleable.Saltyfish_Custom_generalSkin, true);
        setScrollListener();
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
         setScrollListener();
    }

    private void setScrollListener(){
        if(isGeneralSkin){
            setOnScrollListener(null);
        }
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }


    private OnScrollListener mOnScrollListener;
    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        super.setOnScrollListener(mEdgeScrollListener);
        this.mOnScrollListener=listener;
    }


    private OnScrollListener mEdgeScrollListener=new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            onSkinChange();
            if(null!=mOnScrollListener)
                mOnScrollListener.onScrollStateChanged(recyclerView,newState);

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(null!=mOnScrollListener)
                mOnScrollListener.onScrolled(recyclerView,dx,dy);
        }
    };
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private  void setEdgeGlowColor() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    final Class<?> clazz = RecyclerView.class;
                    for (final String name : new String[] {"ensureTopGlow", "ensureBottomGlow"}) {
                        Method method = clazz.getDeclaredMethod(name);
                        method.setAccessible(true);
                        method.invoke(this);
                    }
                    for (final String name : new String[] {"mTopGlow", "mBottomGlow"}) {
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
