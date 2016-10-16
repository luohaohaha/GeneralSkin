package saltyfish.library.skin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import saltyfish.library.base.SaltyfishApplication;
import saltyfish.library.base.SaltyfishBaseActivity;
import saltyfish.library.base.SaltyfishBaseFragment;
import saltyfish.library.manager.ISaltyfishInstanceInterface;
import saltyfish.library.manager.SaltyfishActivityManager;
import saltyfish.library.manager.SaltyfishFragmentManager;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.ui.skin<br/>
 * ClassName: CMSkinColorTool<br/>
 * Description: 换肤工具类<br/>
 * Date: 2016/10/2 0:18 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishSkinColorTool implements ISaltyfishInstanceInterface{

	private static SaltyfishSkinColorTool instance = null;

	private static final String GENERAL_SKIN_KEY = "general_skin";
	private static final String GENERAL_SKIN_COLOR_KEY = "general_skin_color";
	private static final String GENERAL_SKIN_COLOR_PROGRESS_KEY = "general_skin_progress";
	private static final String GENERAL_SKIN_COLOR_SUB_PROGRESS_KEY = "general_skin_sub_progress";

	private static SharedPreferences sp;

	 synchronized static SaltyfishSkinColorTool getInstance() {
		if (null == instance) {
			instance = new SaltyfishSkinColorTool();
			sp = SaltyfishApplication.getInstance().getSharedPreferences(GENERAL_SKIN_KEY, Context.MODE_PRIVATE);
		}
		return instance;
	}

	private int mSkinColor = Color.parseColor("#0066B3");

	/**
	 * 主颜色进度
	 */
	private int mainProgress = -1;
	/**
	 * 次要颜色进度
	 */
	private int mSubProgress = -1;

	/**
	 * 获取主要颜色条进度
	 *
	 * @return
	 */
	public int getMainProgress() {
		return -1 == mainProgress ? sp.getInt(GENERAL_SKIN_COLOR_PROGRESS_KEY, 0) : mainProgress;
	}

	/**
	 * 获取饱和度颜色条进度
	 *
	 * @return
	 */
	public int getSubProgress() {
		return -1 == mSubProgress ? sp.getInt(GENERAL_SKIN_COLOR_SUB_PROGRESS_KEY, 0) : mSubProgress;
	}

	/**
	 * 设置主要颜色条进度
	 *
	 * @param progress
	 *            进度
	 */
	private void setMainProgress(int progress) {
		this.mainProgress = progress;
	}

	/**
	 * 设置饱和颜色条进度
	 *
	 * @param progress
	 *            进度
	 */
	private void setSubProgress(int progress) {
		this.mSubProgress = progress;
	}

	/**
	 * 获取当前换肤颜色
	 *
	 * @return
	 */
	public int getSkinColor() {
		mSkinColor = sp.getInt(GENERAL_SKIN_COLOR_KEY, mSkinColor);
		return mSkinColor;
	}

	/**
	 *
	 * @param alpha
	 *            0-1.0
	 * @return return color with alpha*255
	 */
	public int getSkinColorWithAlpha(float alpha) {
		int red = Color.red(mSkinColor);
		int green = Color.green(mSkinColor);
		int blue = Color.blue(mSkinColor);
		return Color.argb((int) (alpha * 255), red, green, blue);
	}

	/**
	 * 保存当前选中换肤的颜色
	 *
	 * @param color
	 *            换肤颜色
	 * @param mainProgress
	 *            主要进度
	 * @param subProgress
	 *            饱和度进度
	 */
	public void saveThemeColor(int color, int mainProgress, int subProgress) {
		setSkinColor(color);
		SharedPreferences.Editor editor = sp.edit();

		if (-1 != mainProgress) {
			setMainProgress(mainProgress);
			editor.putInt(GENERAL_SKIN_COLOR_PROGRESS_KEY, mainProgress);
		}
		if (-1 != subProgress) {
			setSubProgress(subProgress);
			editor.putInt(GENERAL_SKIN_COLOR_SUB_PROGRESS_KEY, subProgress);
		}
		editor.putInt(GENERAL_SKIN_COLOR_KEY, color);
		editor.commit();
		notifyChangeSkin();

	}

	private void notifyChangeSkin() {
		ArrayMap<String, WeakReference<Activity>> allActivities = getSaltyfishInstance(SaltyfishActivityManager.class).getAllActivities();
		ArrayMap<String, WeakReference<Fragment>> allFragments = getSaltyfishInstance(SaltyfishFragmentManager.class).getAllFragments();
		for (String name : allActivities.keySet()) {
			Activity act = allActivities.get(name).get();
			if (null == act)
				continue;
			if (!(act instanceof SaltyfishBaseActivity))
				continue;
			((SaltyfishBaseActivity) act).notifySkinChange();
		}
		for (String name : allFragments.keySet()) {
			Fragment ft = allFragments.get(name).get();
			if (null == ft)
				continue;
			if (!(ft instanceof SaltyfishBaseFragment))
				continue;
			((SaltyfishBaseFragment) ft).notifySkinChange();
		}
	}

	/**
	 * 设置换肤颜色
	 *
	 * @param skinColor
	 *            换肤颜色
	 */
	public void setSkinColor(int skinColor) {
		this.mSkinColor = skinColor;
	}

	/**
	 * 换肤主要实现
	 *
	 * @param act
	 *            当前界面
	 */
	public void changeSkin(Activity act) {
		View rootView = act.getWindow().getDecorView();
		if(act instanceof SaltyfishBaseActivity){
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
				act.getWindow().setStatusBarColor(mSkinColor);
			}

		}
		changeSkin(rootView);
	}

	public void changeSkin(View rootView) {
		mSkinViews.clear();
		findSkinView(rootView);

		for (ISaltyfishGeneralSkin skin : mSkinViews) {
			skin.onSkinChange();
		}
	}

	/**
	 * 换肤控件集合
	 */
	private List<ISaltyfishGeneralSkin> mSkinViews = Collections.synchronizedList(new LinkedList<ISaltyfishGeneralSkin>());

	/**
	 * 获取当前界面需要换肤的控件集合
	 *
	 * @param v
	 */
	private void findSkinView(View v) {

		if (v instanceof ISaltyfishGeneralSkin) {
			mSkinViews.add((ISaltyfishGeneralSkin) v);
		}
		if (v instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) v;
			for (int i = 0, count = group.getChildCount(); i < count; i++) {
				View child = group.getChildAt(i);
				findSkinView(child);
			}
		}
	}

	/**
	 * 换肤颜色不可点击状态偏移量
	 */
	public static final float LIGHT_OFFSET = 0.372328f;

	/**
	 * 换肤颜色选中状态偏移量
	 */
	public static final float DARK_OFFSET = 0.611073f;

	/**
	 * 获取点击状态颜色
	 *
	 * @return
	 */
	public int getPressedColor(int targetColor) {
		int skinRed = Color.red(targetColor);
		int skinGreen = Color.green(targetColor);
		int skinBlue = Color.blue(targetColor);
		return Color.rgb((int) (DARK_OFFSET * skinRed), (int) (DARK_OFFSET * skinGreen),
				(int) (DARK_OFFSET * skinBlue));
	}

	public int getPressedColor() {

		return getPressedColor(mSkinColor);
	}

	/**
	 * 获取不可点击状态颜色
	 *
	 * @return
	 */
	public int getEnableColor(int targetColor) {
		int skinRed = Color.red(targetColor);
		int skinGreen = Color.green(targetColor);
		int skinBlue = Color.blue(targetColor);

		int enableRed = (int) (255 * (1 - LIGHT_OFFSET));
		int enableGreen = (int) (255 * (1 - LIGHT_OFFSET));
		int enableBlue = (int) (255 * (1 - LIGHT_OFFSET));

		return Color.rgb((int) (LIGHT_OFFSET * skinRed + enableRed), (int) (LIGHT_OFFSET * skinGreen + enableGreen),
				(int) (LIGHT_OFFSET * skinBlue + enableBlue));
	}

	public int getEnableColor() {
		return getEnableColor(mSkinColor);
	}

	/**
	 * 获取默认颜色状态
	 *
	 * @return
	 */
	public ColorStateList getDefaultColorStateList(int targetColor) {
		int[][] states = new int[][] { { android.R.attr.state_pressed }, { -android.R.attr.state_enabled }, {} };
		int pressedColor = getInstance().getPressedColor(targetColor);
		int enableColor = getInstance().getEnableColor(targetColor);
		int skinColor = targetColor;
		return new ColorStateList(states, new int[] { pressedColor, enableColor, skinColor });
	}


	public ColorStateList getSwitchColorStateList(){
		int[][] states = new int[][] { { android.R.attr.state_checked },{ -android.R.attr.state_checked }};
//		int pressedColor = getInstance().getPressedColor();
		int enableColor = getInstance().getEnableColor();
		return new ColorStateList(states, new int[] { mSkinColor,enableColor});
	}

	public ColorStateList getDefaultColorStateList() {
		return getDefaultColorStateList(mSkinColor);
	}

	/**
	 * 更改图片颜色 只有正常状态
	 *
	 * @param drawable
	 * @return
	 */
	public Drawable changeDrawableColor(Drawable drawable) {

		return changeDrawableColor(drawable, COLOR_NULL);
	}

	private static final int COLOR_NULL = Color.TRANSPARENT;

	/**
	 * 更改图片颜色
	 *
	 * @param drawable
	 * @return
	 */
	public Drawable changeDrawableColor(Drawable drawable, int color) {

		if (null == drawable)
			return null;
		int targetColor = COLOR_NULL == color ? mSkinColor : color;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			DrawableCompat.setTint(drawable, targetColor);
			return drawable;
		}
		if (drawable instanceof ColorDrawable) {
			drawable = drawable.mutate();
			((ColorDrawable) drawable).setColor(targetColor);
		} else {
			drawable = DrawableCompat.wrap(drawable);
			DrawableCompat.setTint(drawable, targetColor);
		}
		return drawable;
	}

	@Override
	public void recycle() {
		instance=null;
		mSkinViews.clear();
		mSkinViews=null;
	}
}
