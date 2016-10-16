package saltyfish.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import saltyfish.library.manager.SaltyfishFragmentManager;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logD;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.base<br/>
 * ClassName: CMBaseFragment<br/>
 * Description: 上层实现基类 <br/>
 * Date: 2016-9-17 16:02 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public abstract class SaltyfishBaseFragment extends Fragment {
	protected   int mSkinColor;

	private boolean isSkinChanged = false;

	public final void notifySkinChange(){
		ChangeSkin();
	}

	/**
	 * 是否需要换肤
	 * @return
	 */
	protected boolean needChangeSkin() {
		return mSkinColor != getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor() || !isSkinChanged;
	}

	protected void ChangeSkin(){
		//换肤
		if (needChangeSkin()) {
			logD(String.format("=============current fragment  %s need change skin==============",this.getClass().getSimpleName()));
			getSaltyfishInstance(SaltyfishSkinColorTool.class).changeSkin(getView());
			isSkinChanged = true;
			mSkinColor=getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor();
		}else{
			logD(String.format("=============current fragment  %s skin is changed==============",this.getClass().getSimpleName()));
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ChangeSkin();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addToManager();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		removeFromManager();
	}

	protected void addToManager(){
		getSaltyfishInstance(SaltyfishFragmentManager.class).addFragment(this);
	}


	protected void removeFromManager(){
		getSaltyfishInstance(SaltyfishFragmentManager.class).removeFragment(this);
	}



}
