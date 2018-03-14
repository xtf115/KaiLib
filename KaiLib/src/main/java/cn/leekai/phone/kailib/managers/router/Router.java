package cn.leekai.phone.kailib.managers.router;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

import cn.leekai.phone.kailib.utils.LogUtils;
import cn.leekai.phone.kailib.utils.ToastUtils;

/**
 * description：路由主类
 * created on：2018/1/24 14:18
 * @author likai
 */

public class Router {
	/**
	 * routeRequest实例
	 */
	private RouteRequest mRouteRequest;

	public Router build(){
		mRouteRequest = new RouteRequest();
		return this;
	}

	/**
	 * 创建一个新的路由
	 * @param activityClass 跳转页面的activity.class
	 */
	public Router build(Class activityClass) {
		mRouteRequest = new RouteRequest(activityClass);
		return this;
	}

	/**
	 * 添加Bundle参数
	 * @param bundle Bundle参数
	 */
	public Router with(Bundle bundle) {
		if (bundle != null && !bundle.isEmpty()) {
			Bundle extras = mRouteRequest.getExtras();
			if (extras == null) {
				extras = new Bundle();
			}
			extras.putAll(bundle);
			mRouteRequest.setExtras(extras);
		}
		return this;
	}

	/**
	 * 添加PersistableBundle参数
	 * @param bundle PersistableBundle参数
	 */
	@RequiresApi(21)
	public Router with(PersistableBundle bundle) {
		if (bundle != null && !bundle.isEmpty()) {
			Bundle extras = mRouteRequest.getExtras();
			if (extras == null) {
				extras = new Bundle();
			}
			extras.putAll(bundle);
			mRouteRequest.setExtras(extras);
		}
		return this;
	}

	/**
	 * 单项添加参数
	 * @param key 键
	 * @param value 值
	 */
	@SuppressLint("ObsoleteSdkInt")
	@SuppressWarnings("unchecked")
	public Router with(String key, Object value) {
		if (value == null) {
			LogUtils.e("Ignored: The extra value is null.");
			return this;
		}
		Bundle bundle = mRouteRequest.getExtras();
		if (bundle == null) {
			bundle = new Bundle();
		}
		if (value instanceof Bundle) {
			bundle.putBundle(key, (Bundle) value);
		} else if (value instanceof Byte) {
			bundle.putByte(key, (byte) value);
		} else if (value instanceof Short) {
			bundle.putShort(key, (short) value);
		} else if (value instanceof Integer) {
			bundle.putInt(key, (int) value);
		} else if (value instanceof Long) {
			bundle.putLong(key, (long) value);
		} else if (value instanceof Character) {
			bundle.putChar(key, (char) value);
		} else if (value instanceof Boolean) {
			bundle.putBoolean(key, (boolean) value);
		} else if (value instanceof Float) {
			bundle.putFloat(key, (float) value);
		} else if (value instanceof Double) {
			bundle.putDouble(key, (double) value);
		} else if (value instanceof String) {
			bundle.putString(key, (String) value);
		} else if (value instanceof CharSequence) {
			bundle.putCharSequence(key, (CharSequence) value);
		} else if (value instanceof byte[]) {
			bundle.putByteArray(key, (byte[]) value);
		} else if (value instanceof short[]) {
			bundle.putShortArray(key, (short[]) value);
		} else if (value instanceof int[]) {
			bundle.putIntArray(key, (int[]) value);
		} else if (value instanceof long[]) {
			bundle.putLongArray(key, (long[]) value);
		} else if (value instanceof char[]) {
			bundle.putCharArray(key, (char[]) value);
		} else if (value instanceof boolean[]) {
			bundle.putBooleanArray(key, (boolean[]) value);
		} else if (value instanceof float[]) {
			bundle.putFloatArray(key, (float[]) value);
		} else if (value instanceof double[]) {
			bundle.putDoubleArray(key, (double[]) value);
		} else if (value instanceof String[]) {
			bundle.putStringArray(key, (String[]) value);
		} else if (value instanceof CharSequence[]) {
			bundle.putCharSequenceArray(key, (CharSequence[]) value);
		} else if (value instanceof IBinder) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				bundle.putBinder(key, (IBinder) value);
			} else {
				LogUtils.e("putBinder() requires api 18.");
			}
		} else if (value instanceof ArrayList) {
			if (!((ArrayList) value).isEmpty()) {
				Object obj = ((ArrayList) value).get(0);
				if (obj instanceof Integer) {
					bundle.putIntegerArrayList(key, (ArrayList<Integer>) value);
				} else if (obj instanceof String) {
					bundle.putStringArrayList(key, (ArrayList<String>) value);
				} else if (obj instanceof CharSequence) {
					bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) value);
				} else if (obj instanceof Parcelable) {
					bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) value);
				}
			}
		} else if (value instanceof SparseArray) {
			bundle.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) value);
		} else if (value instanceof Parcelable) {
			bundle.putParcelable(key, (Parcelable) value);
		} else if (value instanceof Parcelable[]) {
			bundle.putParcelableArray(key, (Parcelable[]) value);
		} else if (value instanceof Serializable) {
			bundle.putSerializable(key, (Serializable) value);
		} else {
			LogUtils.e("Unknown object type.");
		}
		mRouteRequest.setExtras(bundle);
		return this;
	}

	/**
	 * 添加requestCode
	 * @param requestCode code值
	 */
	public Router requestCode(int requestCode) {
		mRouteRequest.setRequestCode(requestCode);
		return this;
	}

	/**
	 * 给跳转intent设置flags
	 * @param flags intent的flags
	 * @return 返回router实例
	 */
	public Router addFlags(int flags) {
		mRouteRequest.addFlags(flags);
		return this;
	}

	/**
	 * 给跳转路由中的intent设置action
	 * @param action intent的action
	 */
	public Router addAction(String action) {
		mRouteRequest.setAction(action);
		return this;
	}

	/**
	 * 给跳转路由中的intent设置data
	 */
	public Router setData(Uri uri){
		mRouteRequest.setData(uri);
		return this;
	}

	/**
	 * 设置进入场动画
	 * @param enterAnim 进场动画
	 * @param exitAnim 入场动画
	 */
	public Router anim(int enterAnim, int exitAnim) {
		mRouteRequest.setEnterAnim(enterAnim);
		mRouteRequest.setExitAnim(exitAnim);
		return this;
	}

	/**
	 * 设置activityOption
	 */
	public Router activityOptions(ActivityOptionsCompat activityOptions) {
		mRouteRequest.setActivityOptionsCompat(activityOptions);
		return this;
	}

	/**
	 * 获取跳转Intent
	 */
	private Intent getIntent(Context context) {
		if (mRouteRequest.getActivityClass() == null && mRouteRequest.getAction() == null) {
			ToastUtils.showToast("没有设置目标页面");
			return null;
		}

		// 初始化intent
		Intent mIntent = new Intent();
		if(mRouteRequest.getActivityClass() != null){
			mIntent = new Intent(context, mRouteRequest.getActivityClass());
		}

		// 设置传递参数
		if (mRouteRequest.getExtras() != null && !mRouteRequest.getExtras().isEmpty()) {
			mIntent.putExtras(mRouteRequest.getExtras());
		}
		// 设置flags
		if (mRouteRequest.getFlags() != 0) {
			mIntent.addFlags(mRouteRequest.getFlags());
		}
		// 设置action
		if (mRouteRequest.getAction() != null) {
			mIntent.setAction(mRouteRequest.getAction());
		}
		// 传入的是Uri，用于数据的过滤。setData可以被系统用来寻找匹配目标组件。
		if (mRouteRequest.getData() != null) {
			mIntent.setData(mRouteRequest.getData());
		}
		// 设置文件类型
		if (mRouteRequest.getType() != null) {
			mIntent.setType(mRouteRequest.getType());
		}

		return mIntent;
	}

	/**
	 * 执行跳转操作
	 * @param context 页面的context
	 */
	@SuppressLint("ObsoleteSdkInt")
	public void go(Context context) {
		Intent intent = getIntent(context);
		if (intent == null) {
			return;
		}

		Bundle options = mRouteRequest.getActivityOptionsCompat() == null ? null : mRouteRequest.getActivityOptionsCompat().toBundle();

		if (context instanceof Activity) {
			ActivityCompat.startActivityForResult((Activity) context, intent, mRouteRequest.getRequestCode(), options);

			if (mRouteRequest.getEnterAnim() != 0 && mRouteRequest.getExitAnim() != 0) {
				// 添加进入场动画
				((Activity) context).overridePendingTransition(mRouteRequest.getEnterAnim(), mRouteRequest.getExitAnim());
			}
		} else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// The below api added in v4:25.1.0
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				context.startActivity(intent, options);
			} else {
				context.startActivity(intent);
			}
		}
	}

	/**
	 * 执行跳转操作
	 * @param fragment 当前fragment
	 */
	public void go(Fragment fragment) {
		FragmentActivity activity = fragment.getActivity();
		Context context = fragment.getContext();
		Intent intent = getIntent(activity != null ? activity : context);
		if (intent == null) {
			return;
		}
		Bundle options = mRouteRequest.getActivityOptionsCompat() == null ? null : mRouteRequest.getActivityOptionsCompat().toBundle();

		// 启动activity
		if (mRouteRequest.getRequestCode() < 0) {
			fragment.startActivity(intent, options);
		} else {
			fragment.startActivityForResult(intent, mRouteRequest.getRequestCode(), options);
		}

		// 添加过场动画
		if (activity != null && mRouteRequest.getEnterAnim() != 0 && mRouteRequest.getExitAnim() != 0) {
			// Add transition animation.
			activity.overridePendingTransition(mRouteRequest.getEnterAnim(), mRouteRequest.getExitAnim());
		}
	}

	/**
	 * 执行跳转操作
	 * @param fragment 当前fragment
	 */
	@SuppressLint("ObsoleteSdkInt")
	public void go(android.app.Fragment fragment) {
		Activity activity = fragment.getActivity();
		Intent intent = getIntent(activity);
		if (intent == null) {
			return;
		}
		Bundle options = mRouteRequest.getActivityOptionsCompat() == null ? null : mRouteRequest.getActivityOptionsCompat().toBundle();

		// 启动activity
		if (mRouteRequest.getRequestCode() < 0) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				// 4.1
				fragment.startActivity(intent, options);
			} else {
				fragment.startActivity(intent);
			}
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				// 4.1
				fragment.startActivityForResult(intent, mRouteRequest.getRequestCode(), options);
			} else {
				fragment.startActivityForResult(intent, mRouteRequest.getRequestCode());
			}
		}

		// 添加过场动画
		if (activity != null && mRouteRequest.getEnterAnim() != 0 && mRouteRequest.getExitAnim() != 0) {
			activity.overridePendingTransition(mRouteRequest.getEnterAnim(), mRouteRequest.getExitAnim());
		}
	}
}
