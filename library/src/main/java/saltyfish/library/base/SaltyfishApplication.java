package saltyfish.library.base;

import android.app.Application;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.base<br/>
 * ClassName: CMApplication<br/>
 * Description: 入口 <br/>
 * Date: 2016-9-17 20:44 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishApplication extends Application {

	/**
	 * 当前单例
	 */
	private static SaltyfishApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
	}

	/**
	 * 获取当前Application的实例
	 *
	 * @return
	 */
	public static SaltyfishApplication getInstance() {
		if (instance == null) {
			throw new RuntimeException("IlleagelStateExp : instance is null, application error");
		}
		return instance;
	}

}
