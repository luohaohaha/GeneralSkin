package saltyfish.library.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import saltyfish.library.R;
import saltyfish.library.manager.ISaltyfishInstanceInterface;
import saltyfish.library.manager.SaltyfishInstanceManager;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logW;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.utils<br/>
 * ClassName: CMUtilTool<br/>
 * Description: 常用工具类 <br/>
 * Date: 2016-9-16 20:57 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishUtilTool {


	/**
	 * 通过反射显示光标
	 *
	 * @param currEt
	 */
	public static void setCursorColorAndVisible(EditText currEt) {
		// 设置显示光标
		currEt.setCursorVisible(true);
		currEt.setHighlightColor(getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColorWithAlpha(0.5f));
		changeCursorArrow(currEt,"mCursorDrawableRes","mCursorDrawable");
		changeCursorArrow(currEt,"mTextSelectHandleLeftRes","mSelectHandleLeft");
		changeCursorArrow(currEt,"mTextSelectHandleRightRes","mSelectHandleRight");
		changeCursorArrow(currEt,"mTextSelectHandleRes","mSelectHandleCenter");

	}
	//"mCursorDrawableRes"
	//"mEditor"
	//"mCursorDrawableRes"
	private static void changeCursorArrow(EditText currEt,String resName,String drawableName){
		try {
			//获取光标资源id
			Field cursorField = TextView.class.getDeclaredField(resName);
			cursorField.setAccessible(true);
			int mCursorDrawableRes = cursorField.getInt(currEt);

			// cursorField.set(currEt, 0);
			//获取TextView mEditor对象
			Field fEditor = TextView.class.getDeclaredField("mEditor");
			fEditor.setAccessible(true);
			Object editor = fEditor.get(currEt);
			Class<?> clazz = Class.forName("android.widget.Editor");
			//获取TextView 光标对象
			Field fCursorDrawable = clazz.getDeclaredField(drawableName);
			fCursorDrawable.setAccessible(true);
			//获取光标图片
			Drawable cursorDrawable = null;
			Drawable [] cursorDrawables=null;
			if(!fCursorDrawable.getType().isArray()) {
				cursorDrawable = (Drawable) fCursorDrawable.get(editor);
			}else{
				cursorDrawables = (Drawable[]) fCursorDrawable.get(editor);
			}
			if(null==cursorDrawable) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					cursorDrawable = currEt.getContext().getDrawable(mCursorDrawableRes);
				} else {
					cursorDrawable = currEt.getContext().getResources().getDrawable(mCursorDrawableRes);
				}
			}
			if(null == cursorDrawables || 0==cursorDrawables.length){
				cursorDrawables =new Drawable[2];
				cursorDrawables[0]=cursorDrawable;
				cursorDrawables[1]=cursorDrawable;
			}
			if (cursorDrawable == null) {
				return;
			}
			//改变图片颜色
			cursorDrawable = getSaltyfishInstance(SaltyfishSkinColorTool.class).changeDrawableColor(cursorDrawable);
			if(fCursorDrawable.getType().isArray()) {
//				Drawable[] drawables = new Drawable[]{cursorDrawable, cursorDrawable};
				cursorDrawables[0]=getSaltyfishInstance(SaltyfishSkinColorTool.class).changeDrawableColor(cursorDrawables[0]);
				cursorDrawables[1]=getSaltyfishInstance(SaltyfishSkinColorTool.class).changeDrawableColor(cursorDrawables[1]);
				fCursorDrawable.set(editor, cursorDrawables);
			}else{
				fCursorDrawable.set(editor,cursorDrawable);
			}
			logW("drawable set success");
		} catch (Exception e) {
			logW("drawable set failure"+e.toString());
		}
	}

	public static void setRipple(View rippleView) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
			return;
		Drawable drawable =rippleView.getBackground();
		if(null==drawable)
			return;
		if(drawable instanceof RippleDrawable){
			((RippleDrawable)drawable).setColor(ColorStateList.valueOf(getSaltyfishInstance(SaltyfishSkinColorTool.class).getEnableColor()));
			drawable.invalidateSelf();
			return;
		}
		RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(getSaltyfishInstance(SaltyfishSkinColorTool.class).getEnableColor()), rippleView.getBackground(), null);
		rippleView.setBackground(rippleDrawable);
	}

	public static  <T extends ISaltyfishInstanceInterface> T getSaltyfishInstance(Class<T> tClass){

		return SaltyfishInstanceManager.getInstance().getManagerInstance(tClass);
	}

	/***
	 * 读取图片
	 *
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/***
	 * 读取图片
	 *
	 * @param context
	 * @param resId
	 * @param inSampleSize
	 *            采样比率
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId, int inSampleSize) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = inSampleSize;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public static String defType = "drawable";
	public static String defTypeMipmap = "mipmap";

	/**
	 * 读取drawable下的图片
	 *
	 * @param context
	 * @param imageName
	 *            不包括图片后缀名
	 * @return
	 */
	public static Bitmap readBitMap(Context context, String imageName) {
		int resId = context.getResources().getIdentifier(imageName, defType, context.getPackageName());
		if (0 == resId) {
			resId = context.getResources().getIdentifier(imageName, defTypeMipmap, context.getPackageName());
		}
		if (0 == resId)
			return null;
		return readBitMap(context, resId);
	}

	public static int getRelativeTop(View myView) {
//	    if (myView.getParent() == myView.getRootView())
		if(myView.getId() == android.R.id.content)
			return myView.getTop();
		else
			return myView.getTop() + getRelativeTop((View) myView.getParent());
	}

	public static int getRelativeLeft(View myView) {
//	    if (myView.getParent() == myView.getRootView())
		if(myView.getId() == android.R.id.content)
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}


	private static int getMiddleValue(int prev, int next, float factor){
		return Math.round(prev + (next - prev) * factor);
	}

	public static int getMiddleColor(int prevColor, int curColor, float factor){
		if(prevColor == curColor)
			return curColor;

		if(factor == 0f)
			return prevColor;
		else if(factor == 1f)
			return curColor;

		int a = getMiddleValue(Color.alpha(prevColor), Color.alpha(curColor), factor);
		int r = getMiddleValue(Color.red(prevColor), Color.red(curColor), factor);
		int g = getMiddleValue(Color.green(prevColor), Color.green(curColor), factor);
		int b = getMiddleValue(Color.blue(prevColor), Color.blue(curColor), factor);

		return Color.argb(a, r, g, b);
	}

	public static int getColor(int baseColor, float alphaPercent){
		int alpha = Math.round(Color.alpha(baseColor) * alphaPercent);

		return (baseColor & 0x00FFFFFF) | (alpha << 24);
	}

	public static final long FRAME_DURATION = 1000 / 60;

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	@SuppressLint("NewApi")
	public static int generateViewId() {
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
			for (;;) {
				final int result = sNextGeneratedId.get();
				// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
				int newValue = result + 1;
				if (newValue > 0x00FFFFFF)
					newValue = 1; // Roll over to 1, not 0.
				if (sNextGeneratedId.compareAndSet(result, newValue))
					return result;
			}
		}
		else
			return android.view.View.generateViewId();
	}

	public static boolean hasState(int[] states, int state){
		if(states == null)
			return false;

		for (int state1 : states)
			if (state1 == state)
				return true;

		return false;
	}

	@SuppressLint("NewApi")
	public static void setBackground(View v, Drawable drawable){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			v.setBackground(drawable);
		else
			v.setBackgroundDrawable(drawable);
	}
	private static TypedValue value;
	private static int getColor(Context context, int id, int defaultValue){
		if(value == null)
			value = new TypedValue();

		try{
			Resources.Theme theme = context.getTheme();
			if(theme != null && theme.resolveAttribute(id, value, true)){
				if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT)
					return value.data;
				else if (value.type == TypedValue.TYPE_STRING)
					return context.getResources().getColor(value.resourceId);
			}
		}
		catch(Exception ex){}

		return defaultValue;
	}

	public static int windowBackground(Context context, int defaultValue){
		return getColor(context, android.R.attr.windowBackground, defaultValue);
	}

	public static int textColorPrimary(Context context, int defaultValue){
		return getColor(context, android.R.attr.textColorPrimary, defaultValue);
	}

	public static int textColorSecondary(Context context, int defaultValue){
		return getColor(context, android.R.attr.textColorSecondary, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorPrimary(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorPrimary, defaultValue);

		return getColor(context, R.attr.colorPrimary, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorPrimaryDark(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorPrimaryDark, defaultValue);

		return getColor(context, R.attr.colorPrimaryDark, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorAccent(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorAccent, defaultValue);

		return getColor(context, R.attr.colorAccent, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorControlNormal(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorControlNormal, defaultValue);

		return getColor(context, R.attr.colorControlNormal, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorControlActivated(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorControlActivated, defaultValue);

		return getColor(context, R.attr.colorControlActivated, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorControlHighlight(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorControlHighlight, defaultValue);

		return getColor(context, R.attr.colorControlHighlight, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorButtonNormal(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorButtonNormal, defaultValue);

		return getColor(context, R.attr.colorButtonNormal, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorSwitchThumbNormal(Context context, int defaultValue){
		return getColor(context, R.attr.colorSwitchThumbNormal, defaultValue);
	}

	@SuppressLint("NewApi")
	public static int getType(TypedArray array, int index){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return array.getType(index);
		else{
			TypedValue value = array.peekValue(index);
			return value == null ? TypedValue.TYPE_NULL : value.type;
		}
	}

	/**
	 * 从Context中获取Activity
	 *
	 * @param mContext
	 * @return
	 */
	public static Activity getActFromContext(Context mContext) {
		if (mContext == null)
			return null;
		else if (mContext instanceof Activity)
			return (Activity) mContext;
		else if (mContext instanceof ContextWrapper)
			return getActFromContext(((ContextWrapper) mContext).getBaseContext());
		return null;
	}
}
