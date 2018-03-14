package cn.leekai.phone.kailib.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cn.leekai.phone.kailib.R;
import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：自定义loading dialog
 * created on：2018/1/22 11:05
 * @author likai
 */

public class LoadingDialogUtils {
	private static Dialog loadingDialog = null;

	/**
	 * 弹出dialog
	 */
	public static void showLoading(Context context) {
		showLoading(context, "正在加载");
	}

	/**
	 * 弹出dialog
	 */
	public static void showLoading(Context context, String content) {
		dismissLoading();
		if (null == loadingDialog) {
			loadingDialog = showDialog(context, content, true, null);
		}
	}

	/**
	 * 关闭dialog
	 */
	public static void dismissLoading() {
		if (null != loadingDialog) {
			try {
				loadingDialog.dismiss();
			} catch (Exception e) {
				LogUtils.e(e);
			}
			loadingDialog = null;
		}
	}

	/**
	 * 弹出自定义ProgressDialog
	 * @param context 上下文
	 * @param message 提示
	 * @param cancelable 是否按返回键取消
	 * @param cancelListener 按下返回键监听
	 */
	private static CustomProgressDialog showDialog(Context context, CharSequence message, boolean cancelable,
												   DialogInterface.OnCancelListener cancelListener) {
		CustomProgressDialog dialog = new CustomProgressDialog(context, R.style.Custom_Progress);
		dialog.setTitle("");
		dialog.setContentView(R.layout.dialog_loading_layout);
		if (message == null || message.length() == 0) {
			dialog.findViewById(R.id.message).setVisibility(View.GONE);
		} else {
			TextView txt = dialog.findViewById(R.id.message);
			txt.setText(message);
		}
		// 按返回键是否取消
		dialog.setCancelable(cancelable);
		// 监听返回键处理
		dialog.setOnCancelListener(cancelListener);
		// 设置居中
		Window mWindow = dialog.getWindow();
		if(mWindow != null){
			mWindow.getAttributes().gravity = Gravity.CENTER;
			WindowManager.LayoutParams lp = mWindow.getAttributes();
			// 设置背景层透明度
			lp.dimAmount = 0.2f;
			mWindow.setAttributes(lp);
		}

		dialog.show();
		return dialog;
	}

	private static class CustomProgressDialog extends Dialog {
		CustomProgressDialog(Context context, int theme) {
			super(context, theme);
		}

		/**
		 * 当窗口焦点改变时调用
		 */
		@Override
		public void onWindowFocusChanged(boolean hasFocus) {
			ImageView imageView = findViewById(R.id.spinnerImageView);
			// 获取ImageView上的动画背景
			AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
			// 开始动画
			spinner.start();
		}

		/**
		 * 给Dialog设置提示信息
		 */
		public void setMessage(CharSequence message) {
			if (message != null && message.length() > 0) {
				findViewById(R.id.message).setVisibility(View.VISIBLE);
				TextView txt = findViewById(R.id.message);
				txt.setText(message);
				txt.invalidate();
			}
		}
	}
}
