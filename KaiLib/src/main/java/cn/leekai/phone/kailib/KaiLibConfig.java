package cn.leekai.phone.kailib;

import android.app.Activity;

import com.baidu.android.pushservice.PushMessageReceiver;

import cn.leekai.phone.kailib.utils.CommonUtils;
import cn.leekai.phone.kailib.utils.StringUtils;
import cn.leekai.phone.kailib.utils.cache.SharedCacheUtils;

/**
 * description：kaiLib的主配置文件
 * created on：2018/1/18 18:14
 * @author likai
 */
public class KaiLibConfig {
	/**
	 * 日志开启状态标识
	 */
	public boolean logEnable = false;
	/**
	 * 建立连接的超时时间，单位：毫秒 默认30000
	 */
	public long connectTimeoutRequest = 30000;
	/**
	 * 读取传递数据的超时时间,单位: 毫秒 默认30000
	 */
	public long readTimeoutRequest = 30000;
	/**
	 * 写入传递请求超时时间，单位：毫秒 默认30000
	 */
	public int writeTimeoutRequest = 30000;
	/**
	 * 普通Http请求重试次数 默认1次
	 */
	public int retryHttpTimes = 1;
	/**
	 * 百度push绑定的channelId
	 */
	private static String BAIDU_CHANNELID = "";
	/**
	 * 百度推送项目扩展类
	 * 用于接收推送后的推送处理
	 */
	public PushMessageReceiver pushMessageReceiver;
	/**
	 * 屏幕宽度
	 */
	public int screenWidth;
	/**
	 * 屏幕高度
	 */
	public int screenHeight;

	/**
	 * 设置百度推送的channelId
	 */
	public void setBaiduChannelId(String baiduChannelId) {
		if (!StringUtils.isBlank(baiduChannelId)) {
			BAIDU_CHANNELID = baiduChannelId;

			SharedCacheUtils.save("baidu_channel_id", baiduChannelId);
		}
	}

	/**
	 * 获取百度推送的channelId
	 */
	public String getBaiduChannelId() {
		String channelId = BAIDU_CHANNELID;
		if (StringUtils.isBlank(channelId)) {
			channelId = SharedCacheUtils.get("baidu_channel_id", "");
			BAIDU_CHANNELID = channelId;
		}
		return channelId;
	}

	/**
	 * 初始化系统常量参数
	 */
	public void initSystemInfo(Activity mActivity){
		// ①获取屏幕的宽
		screenWidth = CommonUtils.getScreenWidth(mActivity);
		// ①获取屏幕的高
		screenHeight = CommonUtils.getScreenHeight(mActivity);
	}
}
