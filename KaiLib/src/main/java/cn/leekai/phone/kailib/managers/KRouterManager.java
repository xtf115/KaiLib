package cn.leekai.phone.kailib.managers;

import cn.leekai.phone.kailib.managers.router.Router;

/**
 * description：app页面路由处理
 * created on：2018/1/22 11:34
 * @author likai
 */
public class KRouterManager {
	/**
	 * 单例模式
	 */
	private KRouterManager(){}

	/**
	 * 获取router路由
	 */
	public static Router getRouter(){
		return new Router();
	}
}
