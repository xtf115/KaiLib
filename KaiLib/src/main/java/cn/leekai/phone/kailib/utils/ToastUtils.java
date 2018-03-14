package cn.leekai.phone.kailib.utils;

import android.widget.Toast;

import cn.leekai.phone.kailib.KaiCore;

/**
 * description：自定义toast
 * created on：2018/1/20 10:56
 * @author likai
 */

public class ToastUtils {
	private ToastUtils(){}

	/**
	 * 默认的toast，使用stringId
	 * @param strId 提示内容的stringId
	 */
	public static void showToast(int strId){
		showToast(KaiCore.getContext().getString(strId));
	}

	/**
	 * 默认的toast，使用string
	 * @param str 提示内容的string
	 */
	public static void showToast(String str){
		showLongToast(str);
	}

	/**
	 * 展示长toast
	 * @param str 提示内容string
	 */
	public static void showLongToast(String str){
		Toast.makeText(KaiCore.getContext(), str, Toast.LENGTH_LONG).show();
	}

	/**
	 * 展示短toast
	 * @param str 提示内容的string
	 */
	public static void showShortToast(String str){
		Toast.makeText(KaiCore.getContext(), str, Toast.LENGTH_SHORT).show();
	}
}
