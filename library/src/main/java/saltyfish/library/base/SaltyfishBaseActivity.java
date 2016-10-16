package saltyfish.library.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import saltyfish.library.manager.SaltyfishActivityManager;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logD;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.base<br/>
 * ClassName: CMBaseActivity<br/>
 * Description: ${TODO} <br/>
 * Date: 2016-9-17 22:41 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishBaseActivity extends AppCompatActivity {


	protected  int mSkinColor;
	private boolean isSkinChanged = false;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addToManager();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		removeFromManager();
	}




	/**
	 * 是否需要换肤
	 * @return
	 */
	protected boolean needChangeSkin() {
		return mSkinColor !=  getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor() || !isSkinChanged;
	}

	public final void notifySkinChange(){
		ChangeSkin();
	}

	protected void ChangeSkin(){
		//换肤
		if (needChangeSkin()) {
			logD(String.format("=============current act  %s need change skin==============",this.getClass().getSimpleName()));
			getSaltyfishInstance(SaltyfishSkinColorTool.class).changeSkin(this);
			isSkinChanged = true;
			int skinColor =  getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor();
			mSkinColor = mSkinColor == skinColor ? mSkinColor : skinColor;
			setStatusBarColor();
		}else{
			logD(String.format("=============current act  %s skin is changed==============",this.getClass().getSimpleName()));
		}
	}


	protected void setStatusBarColor(){
		ActionBar actionBar =getSupportActionBar();
		if(null == actionBar)
			return;
		actionBar.setBackgroundDrawable(new ColorDrawable(mSkinColor));
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return;
		}
		getWindow().setStatusBarColor(mSkinColor);
	}




	protected void addToManager() {
		getSaltyfishInstance(SaltyfishActivityManager.class).addActivity(this);
	}

	protected void removeFromManager() {
		getSaltyfishInstance(SaltyfishActivityManager.class).removeActivity(this);
	}


	@Override
	protected void onStart() {
		super.onStart();
		ChangeSkin();
	}


}
