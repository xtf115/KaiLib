package cn.leekai.phone.kailib.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * description：通用方法类
 * created on：2018/1/26 17:47
 * @author likai
 */

public class CommonUtils {

	/**
	 * 获取屏幕宽度
	 *
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 *
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * string转int
	 *
	 */
	public static int getInteger(String largeNum) {
		try {
			if (!StringUtils.isBlank(largeNum)) {
				return Integer.parseInt(largeNum);
			}
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return 0;
	}

	public static float getFloat(String num) {
		try {
			if (!StringUtils.isBlank(num)) {
				return Float.parseFloat(num);
			}
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return 0.0f;
	}

	/**
	 * string转long
	 *
	 */
	public static long getLong(String largeNum) {
		try {
			if (!StringUtils.isBlank(largeNum)) {
				return Long.parseLong(largeNum);
			}
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return 0L;
	}
}
