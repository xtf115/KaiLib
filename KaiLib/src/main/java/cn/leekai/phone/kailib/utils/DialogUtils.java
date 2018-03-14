package cn.leekai.phone.kailib.utils;

import cn.leekai.phone.kailib.R;
import cn.leekai.phone.kailib.datas.entity.events.EventEntity;
import cn.leekai.phone.kailib.managers.KEventManager;

/**
 * description：自定义dialog
 * created on：2018/1/22 09:30
 * @author likai
 */

public class DialogUtils {

	/**
	 * 弹出loading加载框
	 */
	public static void showLoading(){
		EventEntity eventInf = new EventEntity(R.id.eventbus_show_loading_dialog);
		KEventManager.getInstance().postEvent(eventInf);
	}

	/**
	 * 关闭loading加载框
	 */
	public static void dismissLoading(){
		EventEntity eventInf = new EventEntity(R.id.eventbus_dismiss_loading_dialog);
		KEventManager.getInstance().postEvent(eventInf);
	}

	/**
	 *
	 */
	public static void showBottomMenuDialog(){

	}
}
