package cn.leekai.phone.kailib.utils.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：AES加解密
 * created on：2018/1/23 09:22
 * @author likai
 */

public class AES {
	private static final String AES_ENCRYPT_KEY = "xD}nW(az3eH[ng9>";

	/**
	 * 加密（2转16）
	 * @param sSrc 待加密的字符串
	 */
	public static String encrypt(String sSrc) {
		// 判断Key是否为16位
		if (AES_ENCRYPT_KEY.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		try {
			byte[] raw = AES_ENCRYPT_KEY.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			//"算法/模式/补码方式"
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

			return parseByte2HexStr(encrypted);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			LogUtils.e(e);
		}
		return null;
	}

	/**
	 * 解密(16转2)
	 * @param sSrc 待解密的字符串
	 */
	public static String decrypt(String sSrc) {
		try {
			// 判断Key是否为16位
			if (AES_ENCRYPT_KEY.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = AES_ENCRYPT_KEY.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = parseHexStr2Byte(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				return new String(original, "utf-8");
			} catch (Exception e) {
				LogUtils.e(e);
				return null;
			}
		} catch (Exception ex) {
			LogUtils.e(ex);
			return null;
		}
	}

	/**
	 * 将二进制转换成16进制
	 */
	private static String parseByte2HexStr(byte[] buf) {
		StringBuilder sb = new StringBuilder();

		for (byte singleByte : buf) {
			String hex = Integer.toHexString(singleByte & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}

		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}
