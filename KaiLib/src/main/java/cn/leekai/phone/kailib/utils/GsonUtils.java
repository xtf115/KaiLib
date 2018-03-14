package cn.leekai.phone.kailib.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * description：gson操作
 * created on：2018/1/18 17:47
 * @author likai
 */
public class GsonUtils {
	private GsonUtils() {
	}

	/**
	 * 修正之后的解析数组的方法
	 */
	public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
		List<T> lst = new ArrayList<T>();

		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (final JsonElement elem : array) {
			lst.add(new Gson().fromJson(elem, clazz));
		}
		return lst;
	}

	/**
	 * 解析json数组
	 * @param obj 待解析的数组对象
	 * @param classname 解析后的class类
	 * @throws Exception 异常
	 */
	static <T> T baseJsonParse(Object obj, Class<T> classname) throws Exception {
		Gson gson = new Gson();
		JSONObject jobj;
		try {
			jobj = new JSONObject(String.valueOf(obj));
		} catch (JSONException e) {
			return null;
		}
		return gson.fromJson(jobj.toString(), classname);
	}


	/**
	 * 对象转换成json字符串
	 */
	static String toJson(Object obj) {
		return new Gson().toJson(obj);
	}

}
