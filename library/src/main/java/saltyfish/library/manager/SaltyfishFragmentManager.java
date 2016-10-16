package saltyfish.library.manager;

import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

import static saltyfish.library.log.SaltyfishLogManager.logD;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.manager<br/>
 * ClassName: CMFragmentManager<br/>
 * Description: Fragment管理 <br/>
 * Date: 2016-9-17 16:14 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishFragmentManager implements ISaltyfishInstanceInterface {

	/**
	 * 单例
	 */
	private static SaltyfishFragmentManager mInstance = null;

	/**
	 * 保存的Fragment引用
	 */
	private static final ArrayMap<String, WeakReference<Fragment>> mTakeFragments = new ArrayMap<String, WeakReference<Fragment>>();

	/**
	 * 不允许实例化
	 */
	private SaltyfishFragmentManager() {
		super();
	}

	/**
	 * 获取单例
	 * 
	 * @return {@link SaltyfishFragmentManager}
	 */
	synchronized static SaltyfishFragmentManager getInstance() {
		if (null == mInstance) {
			mInstance = new SaltyfishFragmentManager();
		}
		return mInstance;
	}

	/**
	 * 保存当前fragment
	 * 
	 * @param name
	 *            名称
	 * @param fragment
	 *            当前fragment
	 */
	public void addFragment(String name, Fragment fragment) {
		String fragmentName = TextUtils.isEmpty(name) ? fragment.getClass().getName() : name;
		WeakReference<Fragment> currentFragmentWeakReference = new WeakReference<Fragment>(fragment);
		mTakeFragments.put(fragmentName, currentFragmentWeakReference);

	}

	/**
	 * 保存当前fragment 保存key为类名(默认使用)
	 * 
	 * @param fragment
	 */
	public void addFragment(Fragment fragment) {
		addFragment(null, fragment);

	}

	/**
	 * 移除当前记录的Fragment
	 * 
	 * @param name
	 *            名称
	 */
	public void removeFragment(String name) {
		mTakeFragments.remove(name);
	}

	/**
	 * 移除当前记录的Fragment
	 * 
	 * @param fragment
	 *            需要移除的fragment
	 */
	public void removeFragment(Fragment fragment) {
		removeFragment(fragment.getClass().getName());
	}

	/**
	 * 移除当前记录的Fragment
	 * 
	 * @param tClass
	 *            需要移除的Fragment类
	 * @param <T>
	 *            泛型{@link Fragment}
	 */
	public <T extends Fragment> void removeFragment(Class<T> tClass) {
		removeFragment(tClass.getName());
	}

	/**
	 * 根据key获取已经保存的fragment实例
	 * 
	 * @param name
	 *            名称
	 * @return {@link Fragment}
	 */
	public Fragment getFragmentByName(String name) {
		return mTakeFragments.get(name).get();
	}

	/**
	 * 根据类名获取已经保存的fragment实例(默认使用)
	 * 
	 * @param tClass
	 *            类
	 * @param <T>
	 *            泛型Fragment
	 * @return {@link Fragment}
	 */
	public <T extends Fragment> Fragment getFragmentByClass(Class<T> tClass) {
		return getFragmentByName(tClass.getName());
	}

	/**
	 * 获取顶层Fragment
	 * 
	 * @return {@link Fragment}
	 */
	public Fragment getCurrentTopFragment() {
		return mTakeFragments.valueAt(mTakeFragments.size() - 1).get();
	}

	/**
	 * 获取底层fragment
	 * 
	 * @return {@link Fragment}
	 */
	public Fragment getCurrentBottomFragment() {
		return mTakeFragments.valueAt(0).get();
	}

	/**
	 * 获取上一个Fragment
	 * 
	 * @param currentFragment
	 *            当前Fragment
	 * @return {@link Fragment}
	 */
	public Fragment getPreFragment(Fragment currentFragment) {

		int index = -1;

		for (int i = 0, count = mTakeFragments.size(); i < count; i++) {
			if (mTakeFragments.valueAt(i).get() == currentFragment) {
				index = i;
				break;
			}
		}
		if (-1 == index || index - 1 < 0)
			return null;
		return mTakeFragments.valueAt(index - 1).get();
	}

	/**
	 * 根据名称(key)返回到某一个fragment(注意其他fragment会全部销毁)
	 * 
	 * @param name
	 *            保存的key
	 */
	public void backToFragment(String name) {
		int backIndex = mTakeFragments.indexOfKey(name);
		if (-1 == backIndex) {
			logD(String.format("has no fragment called key is  %s", name));
			return;
		}
		for (int i = backIndex, count = mTakeFragments.size(); i < count; i++) {
			WeakReference<Fragment> currentReference = mTakeFragments.valueAt(i);
			Fragment fragment = currentReference.get();
			if (null == fragment || fragment.isDetached()) {
				logD("current fragment is null or isDetached ");
				continue;
			}
			fragment.getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		}

	}

	/**
	 * 根据类名(key)返回到某一个fragment(注意其他fragment会全部销毁)
	 * 
	 * @param tClass
	 *            类
	 * @param <T>
	 *            泛型Fragment
	 */
	public <T extends Fragment> void backToFragment(Class<T> tClass) {

		backToFragment(tClass.getName());
	}

	public ArrayMap<String, WeakReference<Fragment>> getAllFragments(){
		return mTakeFragments;
	}

	@Override
	public void recycle() {
		mInstance = null;
	}
}
