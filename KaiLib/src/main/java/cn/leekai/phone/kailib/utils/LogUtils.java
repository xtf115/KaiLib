package cn.leekai.phone.kailib.utils;

import android.util.Log;

import cn.leekai.phone.kailib.KaiCore;

/**
 * 日志操作类
 * @author likai
 * created on：2018/1/18
 */
public class LogUtils {
	private LogUtils(){}

	/**
	 * 是否输出日志的配置标识
	 */
	private static boolean isPrintLog = KaiCore.getConfig().logEnable;

	public static void d(String tag, String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.d(tag, msg, tr);
	}

	public static void v(String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.i(getStackTraceName(), msg);
	}

	public static void v(String tag, String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.v(tag, msg, tr);
	}

	public static void i(String tag, String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.i(tag, msg);
	}

	public static void i(String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.i(getStackTraceName(), msg);
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.i(tag, msg, tr);
	}

	public static void w(String tag, String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.w(tag, msg);
	}

	public static void w(String tag, Throwable tr) {
		if (!isPrintLog) {
			return;
		}
		Log.w(tag, tr);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (!isPrintLog) {
			return;
		}
		Log.w(tag, msg, tr);
	}

	public static void e(String tag, String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.e(tag, msg);
	}

	public static void e(String msg) {
		if (!isPrintLog || msg == null) {
			return;
		}
		Log.e(getStackTraceName(), msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (!isPrintLog && tr != null) {
			return;
		}
		Log.e(tag, msg, tr);
	}

	public static void e(Throwable tr) {
		if (!isPrintLog || tr == null) {
			return;
		}
		tr.printStackTrace();
	}

	public static void wtf(String TAG, String content) {
		if (isPrintLog) {
			Log.wtf(TAG, content);
		}
	}

	public static void wtf(String TAG, String content, Throwable tr) {
		if (isPrintLog) {
			Log.wtf(TAG, content, tr);
		}
	}

	public static String getStackTraceName() {
		if (!isPrintLog) {
			return "";
		}
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String stackTraceName = null;
		if (stackTrace.length > 4) {
			stackTraceName = stackTrace[4].getClassName() + stackTrace[4].getMethodName();
		}
		return stackTraceName;
	}

}
