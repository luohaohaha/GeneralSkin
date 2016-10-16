package saltyfish.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishSideBar<br/>
 * Description: 定位栏 <br/>
 * Date: 2016-5-23 14:41 <br/>
 * <p/>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p/>
 */
public class SaltyfishSideBar extends View implements ISaltyfishGeneralSkin {

    /**
     * 文字大小
     */
    private float textSize;

    /**
     * 文字颜色
     */
    private int textColor;

    /**
     * 选中文字颜色
     */
    private int selectTextColor;

    /**
     * 选中背景颜色
     */
    private int selectBackGroudColor;

    /**
     * 选中监听器
     */
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    /**
     * 需要绘制的文字
     */
    private String[] b;

    /**
     * 选中位置
     */
    private int choose = -1;

    /**
     * 画笔
     */
    private Paint paint = new Paint();

    /**
     * 是否选中
     */
    private boolean showSelectBackGroudColor;

    public SaltyfishSideBar(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public SaltyfishSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public SaltyfishSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (null == attrs)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Saltyfish_Custom);
        textSize = a.getDimension(R.styleable.Saltyfish_Custom_textSize, 20.0f);
        textColor = a.getColor(R.styleable.Saltyfish_Custom_textColor, Color.BLACK);
        selectTextColor = a.getColor(R.styleable.Saltyfish_Custom_selectTextColor, Color.parseColor("#ffffff"));
        selectBackGroudColor = a.getColor(R.styleable.Saltyfish_Custom_selectBackGroudColor, Color.parseColor("#40000000"));
        showSelectBackGroudColor = a.getBoolean(R.styleable.Saltyfish_Custom_showSelectBackGroudColor, true);
        isGeneralSkin = a.getBoolean(R.styleable.Saltyfish_Custom_generalSkin, true);
        int arrayId = a.getResourceId(R.styleable.Saltyfish_Custom_letter, -1);
        if (-1 != arrayId) {
            b = getResources().getStringArray(arrayId);
        }
        onSkinChange();
        a.recycle();
    }

    public void setStringSideData(String[] strings) {
        this.b = strings;
        requestLayout();
    }

    public void setStringSideData(List<String> strings) {
        b = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            b[i] = strings.get(i);
        }
        requestLayout();
    }


    public void setLetterTextSize(float textSize) {
        this.textSize = textSize;
        requestLayout();
    }


    public void setLetterTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public void setSelectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        requestLayout();
    }

    public void setSelectBackGroudColor(int selectBackGroudColor) {
        this.selectBackGroudColor = selectBackGroudColor;
        requestLayout();
    }

    public void setShowSelectBackGroudColor(boolean showSelectBackGroudColor) {
        this.showSelectBackGroudColor = showSelectBackGroudColor;
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(null==b||0==b.length)
            return;
        //有选中 并且需要显示选中背景
        if (-1 != choose && showSelectBackGroudColor) {
            canvas.drawColor(selectBackGroudColor);
        } else {
            canvas.drawColor(Color.TRANSPARENT);
        }
        //获取宽高
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int width = getWidth();
        float singleHeight = height / b.length;

        //画笔颜色
        paint.setColor(textColor);
        //加粗
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        //抗锯齿
        paint.setAntiAlias(true);
        //字体大小
        paint.setTextSize(textSize);


        for (int i = 0; i < b.length; i++) {
            //选中文字颜色
            if (i == choose) {
                paint.setColor(selectTextColor);
                paint.setFakeBoldText(true);
            }

            //
            String drawtext = b[i];
            if (TextUtils.isEmpty(drawtext))
                continue;
            //计算文字位置
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float fontHeight = fontMetrics.bottom - fontMetrics.top;
            float xPos = width / 2 - paint.measureText(drawtext) / 2;
            float yPos = i * singleHeight + getPaddingTop() + singleHeight - (singleHeight - fontHeight) / 2 - fontMetrics.bottom;
            //绘制文字
            canvas.drawText(drawtext, xPos, yPos, paint);
            // 恢复
            if (i == choose) {
                paint.setColor(textColor);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final int c = (int) ((y / getMeasuredHeight()) * b.length);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (oldChoose == c)
                    break;
                if (c >= 0 && c < b.length) {
                    if (null != onTouchingLetterChangedListener) {
                        onTouchingLetterChangedListener.onTouchingLetterChanged(c, b[c]);
                    }
                    choose = c;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose == c)
                    break;
                if (c >= 0 && c < b.length) {
                    if (null != onTouchingLetterChangedListener) {
                        onTouchingLetterChangedListener.onTouchingLetterChanged(c, b[c]);
                    }
                    choose = c;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                choose = -1;
                if (null != onTouchingLetterChangedListener) {
                    onTouchingLetterChangedListener.onTouchCancel();
                }
                invalidate();
                break;
        }

        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 计算宽高
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public void setGeneralSkin(boolean generalSkin) {
        isGeneralSkin = generalSkin;
        if (!isGeneralSkin)
            return;
        onSkinChange();
    }

    protected boolean isGeneralSkin = true;
    @Override
    public void onSkinChange() {

        if(!isGeneralSkin)
            return ;
        setLetterTextColor(getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor());
        selectTextColor=getSaltyfishInstance(SaltyfishSkinColorTool.class).getPressedColor();
        int enableColor=getSaltyfishInstance(SaltyfishSkinColorTool.class).getEnableColor();
        selectBackGroudColor=Color.argb(122,Color.red(enableColor),Color.green(enableColor),Color.blue(enableColor));


    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }

    /**
     * 选中监听
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(int position, String s);

        void onTouchCancel();
    }

    /**
     * 测量宽度
     *
     * @param measureSpec 模式
     * @return 宽度
     */
    private int measureWidth(int measureSpec) {
        int result = 0;

        if (null == b || 0 == b.length) {
            return result;
        }
        float maxTextSize = 0f;
        Paint textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        for (String s : b) {
            float textWidth = textPaint.measureText(s);
            if (textWidth > maxTextSize) {
                maxTextSize = textWidth;
            }
        }
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (maxTextSize + getPaddingLeft() + getPaddingRight());
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 测量高度
     *
     * @param measureSpec 模式
     * @return 高度
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        if (null == b || 0 == b.length) {
            return result;
        }
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (textSize * 1.5 * b.length) + getPaddingBottom() + getPaddingTop();
        }
        return Math.min(result, specSize);
    }
}
