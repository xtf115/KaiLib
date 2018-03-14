package cn.leekai.phone.kailib.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.leekai.phone.kailib.KaiCore;

import static java.util.regex.Pattern.compile;

/**
 * 手机电话操作相关
 *
 * @author likai
 *
 * 2018/1/18
 */

public class PhoneUtils {
	/**
	 * Unknown network class.
	 */
	private static final int NETWORK_CLASS_UNKNOWN = 0;
	private static final int NETWORK_CLASS_WIFI = 1;
	/**
	 * Class of broadly defined "2G" networks.
	 */
	private static final int NETWORK_CLASS_2_G = 2;
	/**
	 * Class of broadly defined "3G" networks.
	 */
	private static final int NETWORK_CLASS_3_G = 3;
	/**
	 * Class of broadly defined "4G" networks.
	 */
	private static final int NETWORK_CLASS_4_G = 4;
	private PhoneUtils(){}


	/**
	 * 给指定的电话号码，进行处理后，拨打电话
	 * call GeneralMethod.call instead
	 */
	public static void call(Context context, String number) {
		if (number != null && !"".equals(number)) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number.replace("-", "")));
			if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			context.startActivity(intent);
		}
	}

	/**
	 * 检查网络连接状态
	 */
	public static boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) KaiCore.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		//新版本调用方法获取网络状态
		if(connectivityManager != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Network[] networks = connectivityManager.getAllNetworks();
				NetworkInfo networkInfo;
				for (Network mNetwork : networks) {
					networkInfo = connectivityManager.getNetworkInfo(mNetwork);
					if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
						return true;
					}
				}
			} else {
				//否则调用旧版本方法
				NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
				if (info != null) {
					for (NetworkInfo anInfo : info) {
						if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
							Log.d("Network", "NETWORKNAME: " + anInfo.getTypeName());
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * 获取活动网络信息
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
	 * @return NetworkInfo
	 */
	private static NetworkInfo getActiveNetworkInfo() {
		ConnectivityManager connectivityManager = (ConnectivityManager) KaiCore.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			return connectivityManager.getActiveNetworkInfo();
		} else {
			return null;
		}
	}

	/**
	 * 判断网络是否连接
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isConnected() {
		NetworkInfo info = getActiveNetworkInfo();
		return info != null && info.isConnected();
	}

	/**
	 * 验证手机号
	 */
	public static boolean isMobile(String mobiles) {
		if (StringUtils.isBlank(mobiles)) {
			return false;
		}
		Pattern p = compile("^1[0-9]{10}");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 获取当前手机的运营商 0:其它 1：中国移动 2：中国联通 3：中国电信
	 * @return 运行商的名称
	 */
	public static String getOperatorName(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operatorName = "";
		if (telephonyManager != null) {
			String operator = telephonyManager.getSimOperator();
			if (operator != null) {
				if ("46000".equals(operator) || "46002".equals(operator)) {
					operatorName = "1";
				} else if ("46001".equals(operator)) {
					operatorName = "2";
				} else if ("46003".equals(operator)) {
					operatorName = "3";
				} else {
					operatorName = "0";
				}
			}
		}
		return operatorName;
	}

	/**
	 * 获取当前网络的类型2g,3g,4g,wifi
	 */
	public static String getCurrentNetType(Context context) {
		String type = "";
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = (cm == null) ? null : cm.getActiveNetworkInfo();

		if (info == null) {
			type = "null";
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			type = NETWORK_CLASS_WIFI + "";
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			int subType = info.getSubtype();
			switch (subType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN:
					type = NETWORK_CLASS_2_G + "";
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B:
				case TelephonyManager.NETWORK_TYPE_EHRPD:
				case TelephonyManager.NETWORK_TYPE_HSPAP:
					type = NETWORK_CLASS_3_G + "";
					break;
				case TelephonyManager.NETWORK_TYPE_LTE:
					type = NETWORK_CLASS_4_G + "";
					break;
				default:
					type = NETWORK_CLASS_UNKNOWN + "";
					break;
			}
		}
		return type;
	}

	/**
	 * 获取设备imei
	 */
	@SuppressLint("HardwareIds")
	public static String getIMEI(Context context) {
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			return "";
		} else {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return telephonyManager != null ? telephonyManager.getDeviceId() : "";
		}
	}

	/**
	 * 发送短信
	 * SmsManager 是android.telephony.gsm.SmsManager中定义的用户管理短信应用的类。
	 * 开发人员不必实例化SmsManager类，只需要调用静态方法getDefault()获得SmsManager对象，
	 * PendingIntent对象用于指向TinySMSActivity.当用户按下‘发送短信’按键后，用户界面 重新回到TinySMS的初始界面
	 * @param phonenumber 电话号码
	 * @param msg 短信内容
	 */
	public static int sendSMS(Activity activity, String phonenumber, String msg) {// 发送短信的类
		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, new Intent(), 0);
		if (msg.length() > 70) {
			List<String> texts = smsManager.divideMessage(msg);
			for (String str : texts) {
				smsManager.sendTextMessage(phonenumber, null, str, pendingIntent, null);
			}
		} else {
			smsManager.sendTextMessage(phonenumber, null, msg, pendingIntent, null);
		}
		return 0;
	}
}
