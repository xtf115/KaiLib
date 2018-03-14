package cn.leekai.phone.kailib;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * description：kaiLib的主配置入口
 * created on：2018/1/18 18:13
 * @author likai
 */

public class KaiCore {
	/**
	 * kailib的配置信息
	 */
	private static KaiLibConfig kaiLibConfig;
	/**
	 * 主项目的application
	 */
	@SuppressLint("StaticFieldLeak")
	private static Application kaiApplication;

	/**
	 * 启动kailib
	 * @param app 主项目的application
	 * @param config kailib的配置信息
	 */
	public static void start(Application app, KaiLibConfig config){
		kaiApplication = app;
		kaiLibConfig = config;
	}

	/**
	 * 获取kailib的配置信息
	 */
	public static KaiLibConfig getConfig(){
		if(kaiLibConfig == null){
			throw new NullPointerException("未配置kaiLib的config");
		}
		return kaiLibConfig;
	}

	/**
	 * 获取kailib持有的context
	 */
	public static Context getContext(){
		if(kaiApplication == null){
			throw new NullPointerException("未配置kaiLib的context");
		}
		return kaiApplication.getApplicationContext();
	}

	/**
	 * 获取kailib持有的主项目的application
	 */
	public static Application getApplication(){
		if(kaiApplication == null){
			throw new NullPointerException("未配置kaiLib的application");
		}
		return kaiApplication;
	}

}
