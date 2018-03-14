package cn.leekai.phone.kaidroid.utils;

import android.content.Context;

import cn.leekai.phone.kaidroid.datas.entity.DialogInfoEntity;
import cn.leekai.phone.kaidroid.ui.view.dialog.BaseCommonMenuDialog;
import cn.leekai.phone.kaidroid.ui.view.dialog.ListMenuDialog;
import cn.leekai.phone.kaidroid.ui.view.dialog.WebMenuDialog;

/**
 * description：
 * created on：2018/1/26 18:39
 * @author likai
 */

public class DialogUtil {

	/**
	 * 显示底部弹出的操作菜单
	 * @param mContext activity的context
	 * @param dialogInfo 弹窗中操作的数据
	 * @param listener 回调方法
	 */
	public static void showBottomMenu(Context mContext, DialogInfoEntity dialogInfo, BaseCommonMenuDialog.CallBackListener listener){
		new WebMenuDialog(mContext, dialogInfo, listener).show();
	}

	/**
	 * 显示列表弹出的操作菜单
	 * @param mContext activity的context
	 * @param dialogInfo 弹窗中操作的数据
	 * @param listener 回调方法
	 */
	public static void showListMenu(Context mContext, DialogInfoEntity dialogInfo, BaseCommonMenuDialog.CallBackListener listener){
		new ListMenuDialog(mContext, dialogInfo, listener).show();
	}
}
