package saltyfish.library.manager;


import android.support.v4.util.ArrayMap;

import java.lang.reflect.Method;

import static saltyfish.library.log.SaltyfishLogManager.logE;

/**
 * Project: CustomerManager<br/>
 * Package: com.ice.customer.manager.library.manager<br/>
 * ClassName: CMInstanceManager<br/>
 * Description: 单例管理 <br/>
 * Date: 2016-9-17 16:55 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class SaltyfishInstanceManager implements ISaltyfishInstanceInterface {

    /**
     * 当前单例
     */
    private static SaltyfishInstanceManager mInstance = null;

    /**
     * 保存Activity的引用
     */
    private static final ArrayMap<String, ISaltyfishInstanceInterface> mTakeInstance = new ArrayMap<String, ISaltyfishInstanceInterface>();

    /**
     * 不允许实例化
     */
    private SaltyfishInstanceManager() {
        super();
    }

    /**
     * 单例
     * @return {@link SaltyfishInstanceManager}
     */
    public static synchronized SaltyfishInstanceManager getInstance() {
        if (null == mInstance) {
            mInstance = new SaltyfishInstanceManager();
        }
        return mInstance;
    }

    /**
     * 获取不用的单例对象 统一管理
     * @param tClass 类
     * @param <T> 泛型{@link ISaltyfishInstanceInterface}
     * @return {@link ISaltyfishInstanceInterface}
     */
    public <T extends ISaltyfishInstanceInterface> T getManagerInstance(Class<T> tClass) {

        String instanceKey = tClass.getName();
        if (mTakeInstance.containsKey(instanceKey))
            return (T) mTakeInstance.get(instanceKey);
        T t = null;
        try {
            Method tMethod = tClass.getDeclaredMethod("getInstance");
            tMethod.setAccessible(true);
            t = (T) tMethod.invoke(tClass);
            mTakeInstance.put(instanceKey, t);
        } catch (Exception e) {
            logE(e.toString());
        }

        return t;

    }

    public void recycle() {
        for (ISaltyfishInstanceInterface instance : mTakeInstance.values()) {
            instance.recycle();
        }
        mTakeInstance.clear();
        mInstance = null;
    }
}
