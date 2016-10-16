package saltyfish.library.log;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.log<br/>
 * ClassName: CMLogManager<br/>
 * Description: 日志信息打印类 <br/>
 * Date: 2016-9-16 20:30 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishLogManager {
	public static final String TAG = "CustomerManager";
	public static final boolean IS_DEBUG = true;

	public static void logV(String msg) {
		if (!IS_DEBUG)
			return;

		if (msg.length() < LOG_LENGTH) {
			android.util.Log.v(TAG, msg);
		} else {
			// msg过长
			String str1 = msg.substring(0, LOG_LENGTH);
			android.util.Log.v(TAG, str1);
			logV(msg.substring(LOG_LENGTH));
		}
	}

	public static void logD(String msg) {
		if (!IS_DEBUG)
			return;

		if (msg.length() < LOG_LENGTH) {
			android.util.Log.d(TAG, msg);
		} else {
			// msg过长
			String str1 = msg.substring(0, LOG_LENGTH);
			android.util.Log.d(TAG, str1);
			logD(msg.substring(LOG_LENGTH));
		}
	}

	public static void logI(String msg) {
		if (!IS_DEBUG)
			return;

		if (msg.length() < LOG_LENGTH) {
			android.util.Log.i(TAG, msg);
		} else {
			// msg过长
			String str1 = msg.substring(0, LOG_LENGTH);
			android.util.Log.i(TAG, str1);
			logI(msg.substring(LOG_LENGTH));
		}
	}

	public static void logW(String msg) {
		if (!IS_DEBUG)
			return;

		if (msg.length() < LOG_LENGTH) {
			android.util.Log.w(TAG, msg);
		} else {
			// msg过长
			String str1 = msg.substring(0, LOG_LENGTH);
			android.util.Log.w(TAG, str1);
			logW(msg.substring(LOG_LENGTH));
		}
	}

	public static void logE(String msg) {
		if (!IS_DEBUG)
			return;

		if (msg.length() < LOG_LENGTH) {
			String errorPoint = "error at " + Thread.currentThread().getStackTrace()[2].getMethodName() + " called by "
					+ Thread.currentThread().getStackTrace()[3].getClassName() + "::"
					+ Thread.currentThread().getStackTrace()[3].getMethodName();

			android.util.Log.e(TAG, msg);
			android.util.Log.e(TAG, errorPoint);
		} else {
			// msg过长
			String str1 = msg.substring(0, LOG_LENGTH);
			android.util.Log.e(TAG, str1);
			logE(msg.substring(LOG_LENGTH));
		}
	}

	/**
	 * 打印流程,默认i levle
	 *
	 * @param msg
	 *            可以为null
	 */
	public static void printProcess(String msg) {
		if (!IS_DEBUG)
			return;

		String info = Thread.currentThread().getStackTrace()[3].getClassName() + //
				"::" + Thread.currentThread().getStackTrace()[3].getMethodName();
		if (msg != null)
			info += "  " + msg;

		logI(info);
	}

	/**
	 * 在方法内部调用，打印调用信息
	 */
	public static void whoInvokeMe() {
		if (!IS_DEBUG)
			return;

		String callerInfo = Thread.currentThread().getStackTrace()[3].getMethodName() + //
				" called by " + Thread.currentThread().getStackTrace()[4].getClassName() + //
				"::" + Thread.currentThread().getStackTrace()[4].getMethodName();

		logI(callerInfo);
	}

	private static final int LOG_LENGTH = 3000; // android对多余某个长度的string,logcat会截断
}
