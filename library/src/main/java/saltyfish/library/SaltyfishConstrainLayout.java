package saltyfish.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.widget.SaltyfishAppCompatBackgroundHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;

import saltyfish.library.skin.ISaltyfishGeneralSkin;
import saltyfish.library.skin.SaltyfishSkinColorTool;
import saltyfish.library.utils.SaltyfishUtilTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * ClassName: SaltyfishConstrainLayout<br/>
 * Description: 约束布局基类 <br/>
 * Date: 2016/10/2 0:29 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishConstrainLayout extends ConstraintLayout implements TintableBackgroundView,ISaltyfishGeneralSkin {
	protected boolean isGeneralSkin = false;
	private SaltyfishAppCompatBackgroundHelper mBackgroundTintHelper;

	public SaltyfishConstrainLayout(Context context) {
		this(context,null);
	}

	public SaltyfishConstrainLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SaltyfishConstrainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(TintContextWrapper.wrap(context), attrs, defStyleAttr);

		mBackgroundTintHelper = new SaltyfishAppCompatBackgroundHelper(this);
		mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);

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

	@Override
	public void setBackgroundResource(int resId) {
		super.setBackgroundResource(resId);
		if (mBackgroundTintHelper != null) {
			mBackgroundTintHelper.onSetBackgroundResource(resId);
		}
	}

	@Override
	public void setBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);
		if (mBackgroundTintHelper != null) {
			mBackgroundTintHelper.onSetBackgroundDrawable(background);
		}
	}

	/**
	 * This should be accessed via
	 * {@link android.support.v4.view.ViewCompat#setBackgroundTintList(android.view.View, ColorStateList)}
	 *
	 * @hide
	 */
	@Override
	public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
		if (mBackgroundTintHelper != null) {
			mBackgroundTintHelper.setSupportBackgroundTintList(tint);
		}
	}

	/**
	 * This should be accessed via
	 * {@link android.support.v4.view.ViewCompat#getBackgroundTintList(android.view.View)}
	 *
	 * @hide
	 */
	@Override
	@Nullable
	public ColorStateList getSupportBackgroundTintList() {
		return mBackgroundTintHelper != null ? mBackgroundTintHelper.getSupportBackgroundTintList() : null;
	}

	/**
	 * This should be accessed via
	 * {@link android.support.v4.view.ViewCompat#setBackgroundTintMode(android.view.View, PorterDuff.Mode)}
	 *
	 * @hide
	 */
	@Override
	public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
		if (mBackgroundTintHelper != null) {
			mBackgroundTintHelper.setSupportBackgroundTintMode(tintMode);
		}
	}

	/**
	 * This should be accessed via
	 * {@link android.support.v4.view.ViewCompat#getBackgroundTintMode(android.view.View)}
	 *
	 * @hide
	 */
	@Override
	@Nullable
	public PorterDuff.Mode getSupportBackgroundTintMode() {
		return mBackgroundTintHelper != null ? mBackgroundTintHelper.getSupportBackgroundTintMode() : null;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		if (mBackgroundTintHelper != null) {
			mBackgroundTintHelper.applySupportBackgroundTint();
		}
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
		if(!isGeneralSkin)
			return;
        setSupportBackgroundTintList( getSaltyfishInstance(SaltyfishSkinColorTool.class).getDefaultColorStateList());
    	setRipple();
	}

	public void setRipple(){
		SaltyfishUtilTool.setRipple(this);
	}

    @Override
    public boolean isGeneralSkin() {
        return isGeneralSkin;
    }
}
