package cn.leekai.phone.kailib.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import java.util.regex.Pattern;

import cn.leekai.phone.kailib.KaiCore;

import static java.util.regex.Pattern.compile;

/**
 * description：字符串操作
 * created on：2018/1/18
 * @author likai
 */
public class StringUtils {
	private StringUtils(){}

	/**
	 * 检查字符串是否为空
	 * @param str 待检查的字符串
	 * @return 检查结果
	 */
	public static boolean isBlank(String str){
		return str == null || str.length() == 0 || "".equals(str);
	}

	/**
	 * 验证url是否为正确的网址
	 */
	public static boolean isWebUrl(String url) {
		boolean isWebUrl = false;

		if (!isBlank(url)) {
			String regEx = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
			Pattern pattern = compile(regEx);
			isWebUrl = pattern.matcher(url).matches();
		}

		return isWebUrl;
	}

	/**
	 * 拷贝内容至剪切板
	 * @param content 待拷贝的内容
	 */
	public static void copy(String content){
		ClipboardManager clipboard = (ClipboardManager) KaiCore.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
		if(clipboard != null){
			clipboard.setPrimaryClip(ClipData.newPlainText("text", content));
		}
	}

	/**
	 * 获取剪贴板的文本
	 *
	 * @return 剪贴板的文本
	 */
	public static CharSequence getCopy() {
		ClipboardManager clipboard = (ClipboardManager) KaiCore.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
		if(clipboard != null){
			ClipData clip = clipboard.getPrimaryClip();
			if (clip != null && clip.getItemCount() > 0) {
				return clip.getItemAt(0).coerceToText(KaiCore.getContext());
			}
		}
		return null;
	}

	/**
	 * 字符串转换为int
	 * @param str 待转换的字符串
	 */
	public static int getInteger(String str){
		if(isBlank(str)){
			return 0;
		} else {
			return Integer.valueOf(str);
		}
	}

}
