package saltyfish.library.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import saltyfish.library.R;
import saltyfish.library.material.drawable.CheckBoxDrawable;
import saltyfish.library.material.radiobutton.CompoundButton;
import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;


public class CheckBox extends CompoundButton implements ISaltyfishGeneralSkin {

    public CheckBox(Context context) {
        super(context);

        init(context, null, 0, 0);
    }

    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0, 0);
    }

	public CheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		init(context, attrs, defStyleAttr, 0);
	}

    public CheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, defStyleRes);
    }

	private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
		applyStyle(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context,attrs);
    }

    protected boolean isGeneralSkin = false;

    @Override
    public void onSkinChange() {
        if (isInEditMode()) {
            return;
        }
        if (!isGeneralSkin)
            return;
        ColorStateList colorStateList=getSaltyfishInstance(SaltyfishSkinColorTool.class).getSwitchColorStateList();
        ((CheckBoxDrawable)mButtonDrawable).setColor(colorStateList);


//        invalidate();
    }

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }

    public void setGeneralSkin(boolean generalSkin) {
        isGeneralSkin = generalSkin;
        if (!isGeneralSkin)
            return;
        onSkinChange();
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

    public void applyStyle(int resId){
        applyStyle(getContext(), null, 0, resId);
    }

    private void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        CheckBoxDrawable drawable = new CheckBoxDrawable.Builder(context, attrs, defStyleAttr, defStyleRes).build();
        drawable.setInEditMode(isInEditMode());
        drawable.setAnimEnable(false);
        setButtonDrawable(drawable);
        drawable.setAnimEnable(true);
    }

    /**
     * Change the checked state of this button immediately without showing animation.
     * @param checked The checked state.
     */
    public void setCheckedImmediately(boolean checked){
        if(mButtonDrawable instanceof CheckBoxDrawable){
            CheckBoxDrawable drawable = (CheckBoxDrawable)mButtonDrawable;
            drawable.setAnimEnable(false);
            setChecked(checked);
            drawable.setAnimEnable(true);
        }
        else
            setChecked(checked);
    }

}
