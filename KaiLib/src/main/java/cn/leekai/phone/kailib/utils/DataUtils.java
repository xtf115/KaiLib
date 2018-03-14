package cn.leekai.phone.kailib.utils;

import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * description：
 * created on：2018/1/18 18:04
 * @author likai
 */

public class DataUtils {
	/**
	 * int类型转换格式
	 */
	private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
	/**
	 * 浮点类型转换格式
	 */
	private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");

	private DataUtils() {
	}

	/**
	 * object对象转string
	 */
	public static String object2String(Object obj) {
		return GsonUtils.toJson(obj);
	}

	/**
	 * String转object对象
	 */
	public static <T> T string2Object(String str, Class<T> clazz) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		try {
			return GsonUtils.baseJsonParse(str, clazz);
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return null;
	}

	/**
	 * 获取文件大小
	 * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
	 * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	 * @throws Exception 异常
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		if (file != null) {
			try {
				File[] fileList = file.listFiles();

				for (File singleFile : fileList) {
					// 如果下面还有文件
					if (singleFile.isDirectory()) {
						size = size + getFolderSize(singleFile);
					} else {
						size = size + singleFile.length();
					}
				}
			} catch (Exception e) {
				LogUtils.e(e);
			}
		}
		return size;
	}

	/**
	 * 格式化单位
	 * @param size 带格式化的数字大小
	 * @return 格式化后的内容（K/M/GB/TB）
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return "0K";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	/**
	 * 党委转换
	 * @param size 要转换的数据
	 * @param isInteger 是否int类型数据（用于不同格式转换）
	 * @return 转换后的数据
	 */
	public static String formatFileSize(long size, boolean isInteger) {
		DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
		String fileSizeString;
		if (size < 1024 && size > 0) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1024 * 1024) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1024 * 1024 * 1024) {
			fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
		} else {
			fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
		}
		return fileSizeString;
	}
}
