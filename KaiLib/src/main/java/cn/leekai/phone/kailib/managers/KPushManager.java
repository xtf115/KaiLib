package cn.leekai.phone.kailib.managers;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.datas.consts.ConfigConst;

/**
 * description：推送管理
 * created on：2018/1/18 17:52
 * @author likai
 */
public class KPushManager {
	private volatile static KPushManager mInstance;

	private KPushManager() {
	}

	public static KPushManager getInstance() {
		if (mInstance == null) {
			synchronized (KPushManager.class) {
				if (mInstance == null) {
					mInstance = new KPushManager();
				}
			}
		}

		return mInstance;
	}

	/**
	 * 注册百度云推送
	 */
	public void start() {
		PushManager.startWork(KaiCore.getContext(), PushConstants.LOGIN_TYPE_API_KEY, ConfigConst.BAIDU_PUSH_API_KEY);
	}

	/**
	 * 关闭百度推送
	 */
	public void stop() {
		PushManager.stopWork(KaiCore.getContext());
	}
}
