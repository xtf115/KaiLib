package cn.leekai.phone.kaidroid.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.ui.activity.SplashActivity;
import cn.leekai.phone.kailib.datas.entity.events.EventEntity;
import cn.leekai.phone.kailib.managers.KEventManager;
import cn.leekai.phone.kailib.utils.AppUtils;
import cn.leekai.phone.kailib.utils.PermissionUtils;
import cn.leekai.phone.kailib.utils.dialog.LoadingDialogUtils;

/**
 * description：activity基类，所有的activity都要集成该基类
 * created on：2018/1/20 10:52
 * @author likai
 */
public abstract class BaseActivity extends FragmentActivity {
	protected Context mContext;
	protected Activity mActivity;
	/**
	 * 标识当前activity是否在前台展示
	 */
	protected volatile boolean isActivityInFront;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		mActivity = this;

		// 添加监听事件
		KEventManager.getInstance().registerEvent(this);

		// 获取授权
		if(!SplashActivity.class.getSimpleName().equals(getClass().getSimpleName())){
			PermissionUtils.requestMultiPermissions(this, new PermissionUtils.PermissionGrant() {
				@Override
				public void onPermissionGranted(int requestCode) {
				}
			});
		}

		// 清除桌面图标角标
		AppUtils.clearShortcutBadger();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 标识activity处于前台
		isActivityInFront = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 标识activity处于后台
		isActivityInFront = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 取消监听事件
		KEventManager.getInstance().unRegisterEvent(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void requestOnMainThread(EventEntity eventInf) {
		if (isActivityInFront) {
			switch (eventInf.getId()) {
				case R.id.eventbus_show_loading_dialog:
					// 请求展示加载对话框
					LoadingDialogUtils.showLoading(mContext);
					break;
				case R.id.eventbus_dismiss_loading_dialog:
					// 请求关闭加载对话框
					LoadingDialogUtils.dismissLoading();
					break;
				default:
					break;
			}
		}
	}
}
