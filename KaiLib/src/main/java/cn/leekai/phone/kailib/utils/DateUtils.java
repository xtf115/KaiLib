package cn.leekai.phone.kailib.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期操作工具类
 *
 * @author likai
 *
 * Created by likai
 * 2018/1/18
 */

public class DateUtils {
	/**
	 * yyyy-MM-dd
	 */
	public static final String TIME_FORMAT_ONE = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String TIME_FORMAT_TWO = "yyyy-MM-dd HH:mm";
	/**
	 * yyyy-MM-dd HH:mmZ
	 */
	public static final String TIME_FORMAT_THREE = "yyyy-MM-dd HH:mmZ";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String TIME_FORMAT_FOUR = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss.SSSZ
	 */
	public static final String TIME_FORMAT_FIVE = "yyyy-MM-dd HH:mm:ss.SSSZ";
	/**
	 * yyyy-MM-dd'T'HH:mm:ss.SSSZ
	 */
	public static final String TIME_FORMAT_SIX = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	/**
	 * HH:mm:ss
	 */
	public static final String TIME_FORMAT_SEVEN = "HH:mm:ss";
	/**
	 * HH:mm:ss.SS
	 */
	public static final String TIME_FORMAT_EIGHT = "HH:mm:ss.SS";
	/**
	 * yyyy.MM.dd
	 */
	public static final String TIME_FORMAT_9 = "yyyy.MM.dd";
	/**
	 * yyyy-MM-dd'T'HH:mm:ss
	 */
	public static final String TIME_FORMAT_10 = "yyyy-MM-dd'T'HH:mm:ss";
	/**
	 * MM-dd HH:mm
	 */
	public static final String TIME_FORMAT_11 = "MM-dd HH:mm";
	/**
	 * MM-dd
	 */
	public static final String TIME_FORMAT_12 = "MM-dd";
	/**
	 * HH:mm
	 */
	public static final String TIME_FORMAT_13 = "HH:mm";
	/**
	 * mm月dd日
	 */
	public static final String TIME_FORMAT_14 = "MM月dd日 HH:mm";

	private DateUtils(){}

	/**
	 * 获取时区 返回+8:00 这种格式
	 *
	 */
	public static String getTimeZoneName() {
		SimpleDateFormat myFmt = new SimpleDateFormat("z");
		Date date = Calendar.getInstance().getTime();
		String zone = myFmt.format(date);
		if (zone.contains("GMT")) {
			zone = zone.replace("GMT", "");
		}
		return zone;
	}

	/**
	 * 格式化时间
	 */
	public static String formatTime(long time, String format) {
		return new SimpleDateFormat(format, Locale.CHINA).format(new Date(time));
	}
}
