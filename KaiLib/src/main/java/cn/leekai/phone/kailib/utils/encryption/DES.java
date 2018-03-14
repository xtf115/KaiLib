package cn.leekai.phone.kailib.utils.encryption;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：DES加解密
 * created on：2018/1/23 09:24
 * @author likai
 */
public class DES {
	private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * 简单可逆加密解密
	 * @param data 数据源
	 * @return 解密或加密后的数据
	 */
	public static String encryptDataReversible(String data) {
		char[] chars = data.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char) (chars[i] ^ '1');
		}
		return new String(chars);
	}

	/**
	 * DES算法，加密
	 * @param data 待加密字符串
	 * @param key 加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws Exception 异常
	 */
	public static String encode(String key, String data) throws Exception {
		return encode(key, data.getBytes());
	}

	/**
	 * DES算法，加密
	 * @param data 待加密字符串
	 * @param key 加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws Exception 异常
	 */
	private static String encode(String key, byte[] data) throws Exception {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec("12345678".getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

			byte[] bytes = cipher.doFinal(data);

			return Base64.encode(bytes);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * DES算法，解密
	 * @param data 待解密字符串
	 * @param key 解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception 异常
	 */
	private static byte[] decode(String key, byte[] data) throws Exception {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec("12345678".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			LogUtils.e(e);
			throw new Exception("DES解密错误");
		}
	}

	/**
	 * 获取编码后的值
	 * @param key 键
	 * @param data 值
	 * @return 编码后的值
	 */
	public static String decodeValue(String key, String data) {
		byte[] datas;
		String value;
		try {
			datas = decode(key, Base64.decode(data));
			value = new String(datas);
		} catch (Exception e) {
			value = "";
		}
		return value;
	}
}
