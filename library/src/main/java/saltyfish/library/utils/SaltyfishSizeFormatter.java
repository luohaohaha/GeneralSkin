package saltyfish.library.utils;

import android.content.Context;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.utils<br/>
 * ClassName: CMSizeFormatter<br/>
 * Description: 大小转换类 <br/>
 * Date: 2016-9-16 21:16 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishSizeFormatter {

	/**
	 * dp转px
	 *
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int getDpToPx(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
		return px;
	}

	/**
	 * px转dp
	 *
	 * @param context
	 * @param px
	 * @return
	 */
	public static int getDp(Context context, int px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int dp = (int) (px / scale + 0.5f);
		return dp;
	}

	/**
	 * px转sp
	 *
	 * @param context
	 * @param px
	 * @return
	 */
	public static int getSp(Context context, int px) {
		final float fontscale = context.getResources().getDisplayMetrics().scaledDensity;
		int sp = (int) (px / fontscale + 0.5f);
		return sp;
	}

	/**
	 * sp转px
	 *
	 * @param context
	 * @param sp
	 * @return
	 */
	public static int getSpToPx(Context context, int sp) {
		final float fontscale = context.getResources().getDisplayMetrics().scaledDensity;
		int px = (int) (sp * fontscale + 0.5f);
		return px;
	}

	/**
	 * dp转sp
	 *
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int getDpToSp(Context context, int dp) {
		int px = getDpToPx(context, dp);
		return getSp(context, px);
	}
}
