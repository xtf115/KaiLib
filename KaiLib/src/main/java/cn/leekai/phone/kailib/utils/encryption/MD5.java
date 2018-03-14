package cn.leekai.phone.kailib.utils.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：md5加密
 * created on：2018/1/23 09:21
 * @author likai
 */

public class MD5 {
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	/**
	 * byte to hexString
	 */
	private static String toHexString(byte[] b) {
		// String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);

		for (byte singleByte : b) {
			sb.append(HEX_DIGITS[(singleByte & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[singleByte & 0x0f]);
		}

		return sb.toString();
	}

	/**
	 * 执行md5加密
	 * @param s 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte[] messageDigest = digest.digest();

			return toHexString(messageDigest).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			LogUtils.e(e);
		}

		return "";
	}
}
