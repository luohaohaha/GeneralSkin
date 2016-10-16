package saltyfish.library.manager;

import android.app.Activity;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

import static saltyfish.library.log.SaltyfishLogManager.logD;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.manager<br/>
 * ClassName: CMActivityManager<br/>
 * Description: ${TODO} <br/>
 * Date: 2016-9-17 16:19 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishActivityManager implements ISaltyfishInstanceInterface {

	/**
	 * 当前单例
	 */
	private static SaltyfishActivityManager mInstance = null;
	/**
	 * 保存的activity引用
	 */
	private static final ArrayMap<String, WeakReference<Activity>> mTakeActivities = new ArrayMap<String, WeakReference<Activity>>();

	/**
	 * 不允许实例化
	 */
	private SaltyfishActivityManager() {
		super();
	}

	/**
	 * 获取单例
	 * 
	 * @return {@link SaltyfishActivityManager}
	 */
	synchronized static SaltyfishActivityManager getInstance() {
		if (null == mInstance) {
			mInstance = new SaltyfishActivityManager();
		}
		return mInstance;
	}

	/**
	 * 保存当前activity
	 *
	 * @param name
	 *            名称
	 * @param activity
	 *            当前activity
	 */
	public synchronized void addActivity(String name, Activity activity) {
		String activityName = TextUtils.isEmpty(name) ? activity.getClass().getName() : name;
		WeakReference<Activity> currentActivityWeakReference = new WeakReference<Activity>(activity);
		mTakeActivities.put(activityName, currentActivityWeakReference);

	}

	/**
	 * 保存当前activity 保存key为类名(默认使用)
	 *
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		addActivity(null, activity);

	}

	/**
	 * 移除当前记录的activity
	 *
	 * @param name
	 *            名称
	 */
	public synchronized void removeActivity(String name) {
		mTakeActivities.remove(name);
	}

	/**
	 * 移除当前记录的activity
	 *
	 * @param activity
	 *            需要移除的activity
	 */
	public void removeActivity(Activity activity) {
		removeActivity(activity.getClass().getName());
	}

	/**
	 * 移除当前记录的activity
	 *
	 * @param tClass
	 *            需要移除的activity类
	 * @param <T>
	 *            泛型{@link Activity}
	 */
	public <T extends Activity> void removeActivity(Class<T> tClass) {
		removeActivity(tClass.getName());
	}

	/**
	 * 根据key获取已经保存的activity实例
	 *
	 * @param name
	 *            名称
	 * @return {@link Activity}
	 */
	public Activity getActivityByName(String name) {
		return mTakeActivities.get(name).get();
	}

	/**
	 * 根据类名获取已经保存的activity实例(默认使用)
	 *
	 * @param tClass
	 *            类
	 * @param <T>
	 *            泛型activity
	 * @return {@link Activity}
	 */
	public <T extends Activity> Activity getActivityByClass(Class<T> tClass) {
		return getActivityByName(tClass.getName());
	}

	/**
	 * 获取顶层activity
	 *
	 * @return {@link Activity}
	 */
	public Activity getCurrentTopActivity() {
		return mTakeActivities.valueAt(mTakeActivities.size() - 1).get();
	}

	/**
	 * 获取底层activity
	 *
	 * @return {@link Activity}
	 */
	public Activity getCurrentBottomactivity() {
		return mTakeActivities.valueAt(0).get();
	}

	/**
	 * 获取上一个Activity
	 *
	 * @param currentActivity
	 *            当前Activity
	 * @return {@link Activity}
	 */
	public Activity getPreActivity(Activity currentActivity) {

		int index = -1;

		for (int i = 0, count = mTakeActivities.size(); i < count; i++) {
			if (mTakeActivities.valueAt(i).get() == currentActivity) {
				index = i;
				break;
			}
		}
		if (-1 == index || index - 1 < 0)
			return null;
		return mTakeActivities.valueAt(index - 1).get();
	}

	/**
	 * 根据名称(key)返回到某一个activity(注意其他activity会全部销毁)
	 *
	 * @param name
	 *            保存的key
	 */
	public void backToActivity(String name) {
		int backIndex = mTakeActivities.indexOfKey(name);
		if (-1 == backIndex) {
			logD(String.format("has no activity called key is  %s", name));
			return;
		}
		for (int i = backIndex, count = mTakeActivities.size(); i < count; i++) {
			WeakReference<Activity> currentReference = mTakeActivities.valueAt(i);
			Activity activity = currentReference.get();
			if (null == activity || activity.isFinishing()) {
				logD("current activity is null or isFinishing ");
				continue;
			}
			activity.finish();
		}
	}

	/**
	 * 根据类名(key)返回到某一个activity(注意其他activity会全部销毁)
	 *
	 * @param tClass
	 *            类
	 * @param <T>
	 *            泛型activity
	 */
	public <T extends Activity> void backToActivity(Class<T> tClass) {

		backToActivity(tClass.getName());
	}


	public ArrayMap<String, WeakReference<Activity>> getAllActivities(){
		return  mTakeActivities;
	}
	@Override
	public void recycle() {
		mInstance = null;
	}
}
