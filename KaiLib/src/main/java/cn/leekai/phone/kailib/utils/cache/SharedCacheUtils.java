package cn.leekai.phone.kailib.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import cn.leekai.phone.kailib.KaiCore;

/**
 * description：通过sharedPreference进行缓存的数据
 * created on：2018/1/18 17:45
 * @author likai
 */

public class SharedCacheUtils {
	/**
	 * sharedpreference实例
	 */
	private static volatile SharedPreferences sp;

	private final static String SP_NAME = "kai_sharedpreference";

	private SharedCacheUtils(){}

	/**
	 * 初始化sharedPreference
	 */
	private static void init(){
		if(sp == null){
			sp = KaiCore.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
	}

	/**
	 * 保存string数据
	 * @param key 保存的键
	 * @param value 保存的值
	 */
	public static void save(String key, String value){
		init();
		sp.edit().putString(key, value).apply();
	}

	/**
	 * 获取缓存的String数据
	 * @param key 键
	 * @param defaultValue 值
	 * @return 查询到的数据
	 */
	public static String get(String key, String defaultValue){
		init();
		return sp.getString(key, defaultValue);
	}

	/**
	 * 保存集合数据
	 * @param params 集合数据
	 */
	public static void saveStringMap(HashMap<String, String> params){
		init();
		if(params != null) {
			SharedPreferences.Editor editor = sp.edit();
			for (Object obj : params.entrySet()) {
				Map.Entry entity = (Map.Entry) obj;
				String key = entity.getKey().toString();
				String value = entity.getValue().toString();
				editor.putString(key, value);
			}
			editor.apply();
		}
	}

	/**
	 * 保存int数据
	 * @param key 保存的键
	 * @param value 保存的值
	 */
	public static void save(String key, int value){
		init();
		sp.edit().putInt(key, value).apply();
	}

	/**
	 * 获取缓存的int数据
	 * @param key 键
	 * @param defaultValue 值
	 * @return 查询到的数据
	 */
	public static int get(String key, int defaultValue){
		init();
		return sp.getInt(key, defaultValue);
	}

	/**
	 * 保存集合数据
	 * @param params 集合数据
	 */
	public static void saveIntegerMap(HashMap<String, Integer> params){
		init();
		if(params != null) {
			SharedPreferences.Editor editor = sp.edit();
			for (Object obj : params.entrySet()) {
				Map.Entry entity = (Map.Entry) obj;
				String key = entity.getKey().toString();
				int value = Integer.valueOf(entity.getValue().toString());
				editor.putInt(key, value);
			}
			editor.apply();
		}
	}
}
