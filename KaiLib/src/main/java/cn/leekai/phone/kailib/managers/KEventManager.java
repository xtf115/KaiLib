package cn.leekai.phone.kailib.managers;

import org.greenrobot.eventbus.EventBus;

import cn.leekai.phone.kailib.datas.entity.events.EventEntity;

/**
 * description：evnet管理
 * created on：2018/1/18 17:53
 * @author likai
 */

public class KEventManager {
	/** 单例模式初始化 */
	private KEventManager(){}

	private static class SingletonHolder {
		private static final KEventManager INSTANCE = new KEventManager();
	}

	public static KEventManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 注册一个监听页面
	 */
	public void registerEvent(Object mContext){
		EventBus.getDefault().register(mContext);
	}

	/**
	 * 取消一个监听页面
	 */
	public void unRegisterEvent(Object mContext){
		EventBus.getDefault().unregister(mContext);
	}

	/**
	 * 发送事件
	 */
	public void postEvent(EventEntity event){
		EventBus.getDefault().post(event);
	}
}
