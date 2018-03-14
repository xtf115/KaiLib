package cn.leekai.phone.kailib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.leekai.phone.kailib.R;

/**
 * description：手机权限相关
 * packageName：cn.leekai.phone.kailib.utils
 * created on：2018/1/18 17:39
 * @author likai
 */

public class PermissionUtils {

	private static final String TAG = "J_Permission";

	/**
	 * requestCode
	 */
	private static final int CODE_MULTI_PERMISSION = 100;

	/**
	 * permisssion
	 */
	private static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
	private static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
	private static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
	private static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
	private static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
	private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
	private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

	/**
	 * 需要请求的权限
	 */
	private static final String[] REQUEST_PERMISSIONS = {PERMISSION_READ_PHONE_STATE,
			PERMISSION_CALL_PHONE,
			PERMISSION_ACCESS_FINE_LOCATION,
			PERMISSION_ACCESS_COARSE_LOCATION,
			PERMISSION_READ_EXTERNAL_STORAGE,
			PERMISSION_WRITE_EXTERNAL_STORAGE,
			PERMISSION_CAMERA};

	private PermissionUtils() {
	}

	/**
	 * 回调
	 */
	public interface PermissionGrant {
		/**
		 * 回调方法
		 * @param requestCode 回调code
		 */
		void onPermissionGranted(int requestCode);
	}

	/**
	 * 权限请求
	 * @param requestCode request code,
	 * if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
	 */
	public static void requestPermission(final Activity activity, final int requestCode, PermissionGrant permissionGrant) {
		if (activity == null) {
			return;
		}

		LogUtils.i(TAG, "申请权限的requestCode:" + requestCode);

		//检查code是否合法
		if (requestCode < 0 || requestCode >= REQUEST_PERMISSIONS.length) {
			LogUtils.w(TAG, "错误的权限requestCode:" + requestCode);
			return;
		}

		//根据code获取到对应的权限内容
		final String requestPermission = REQUEST_PERMISSIONS[requestCode];

		//获取权限开启情况
		int checkSelfPermission;
		try {
			checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
		} catch (RuntimeException e) {
			ToastUtils.showToast("请打开该权限");
			LogUtils.e(TAG, "获取权限开启状态失败：" + e.getMessage());
			return;
		}
		if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
			LogUtils.i(TAG, "该权限没有开启");
			if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
				LogUtils.i(TAG, "获取权限：需要展示用户确认提示框");
				shouldShowRationale(activity, requestCode, requestPermission);
			} else {
				LogUtils.d(TAG, "获取权限：");
				ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
			}

		} else {
			LogUtils.d(TAG, "该权限已经开启");
			ToastUtils.showToast("已开启：" + REQUEST_PERMISSIONS[requestCode]);
			permissionGrant.onPermissionGranted(requestCode);
		}
	}

	/***
	 * 获取多个权限结果
	 */
	private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionGrant permissionGrant) {
		if (activity == null) {
			return;
		}

		LogUtils.d(TAG, "申请权限结果，所申请权限的数量:" + permissions.length);


		ArrayList<String> notGranted = new ArrayList<>();
		for (int i = 0; i < permissions.length; i++) {
			LogUtils.d(TAG, "权限: [i]:" + i + ", 权限[i]" + permissions[i] + ",授权结果[i]:" + grantResults[i]);
			if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
				notGranted.add(permissions[i]);
			}
		}

		if (notGranted.size() == 0) {
			ToastUtils.showToast("所有申请成功的权限" + notGranted);
			permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
		} else {
			openSettingActivity(activity, "这些权限需要允许后才能使用!");
		}
	}

	/**
	 * 一次申请多个权限
	 */
	public static void requestMultiPermissions(final Activity activity, PermissionGrant grant) {
		final List<String> permissionsList = getNoGrantedPermission(activity, false);
		final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, true);

		if (permissionsList == null || shouldRationalePermissionsList == null) {
			return;
		}
		LogUtils.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + "," + "shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

		if (permissionsList.size() > 0) {
			ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), CODE_MULTI_PERMISSION);
			LogUtils.d(TAG, "需要用户手动去开启权限");
		} else if (shouldRationalePermissionsList.size() > 0) {
			// rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
			// 获取用户拒绝的权限描述信息
			String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < REQUEST_PERMISSIONS.length; i++){
				String permission = REQUEST_PERMISSIONS[i];
				if(shouldRationalePermissionsList.contains(permission)){
					sb.append("\n").append("·").append(permissionsHint[i]);
				}
			}
			showMessageOKCancel(activity, "需要开启如下权限:"+sb.toString(), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]), CODE_MULTI_PERMISSION);
					LogUtils.d(TAG, "需要用户手动去开启权限");
				}
			});
		} else {
			if (grant != null) {
				grant.onPermissionGranted(CODE_MULTI_PERMISSION);
			}
		}

	}

	/**
	 * 需要展示权限获取说明内容
	 */
	public static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
		String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
		showMessageOKCancel(activity, "说明: " + permissionsHint[requestCode], new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
				LogUtils.d(TAG, "展示请求权限的确认弹窗:" + requestPermission);
			}
		});
	}

	/**
	 * 弹出权限申请提醒框
	 */
	private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(context).setMessage(message).setPositiveButton("确认", okListener).setNegativeButton("取消", null).create().show();
	}

	/**
	 * @param requestCode Need consistent with requestPermission
	 */
	public static void requestPermissionsResult(final Activity activity, final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionGrant permissionGrant) {
		if (activity == null) {
			return;
		}
		LogUtils.d(TAG, "申请权限结果requestCode:" + requestCode);


		if (requestCode == CODE_MULTI_PERMISSION) {
			requestMultiResult(activity, permissions, grantResults, permissionGrant);
			return;
		}

		//检查code是否合法
		if (requestCode < 0 || requestCode >= REQUEST_PERMISSIONS.length) {
			LogUtils.w(TAG, "申请权限返回结果：非法的requestCode:" + requestCode);
			ToastUtils.showToast("非法的requestCode:" + requestCode);
			return;
		}

		LogUtils.i(TAG, "申请权限返回结果requestCode:" + requestCode + ",permissions:" + Arrays.toString(permissions) + "," + "grantResults:" + Arrays.toString(grantResults) + ",length:" + grantResults.length);

		if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			LogUtils.i(TAG, "申请权限返回结果：已授予此权限");
			//获取成功，函数返回成功的code
			permissionGrant.onPermissionGranted(requestCode);
		} else {
			//获取失败
			LogUtils.i(TAG, "申请权限返回结果：权限未被授权");
			String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
			openSettingActivity(activity, "结果：" + permissionsHint[requestCode]);
		}
	}

	/**
	 * 打开设置界面
	 */
	private static void openSettingActivity(final Activity activity, String message) {
		showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
				intent.setData(uri);
				activity.startActivity(intent);
			}
		});
	}

	/**
	 * 获取未授权的权限列表
	 * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions,
	 * false:return no granted and !shouldShowRequestPermissionRationale
	 */
	public static ArrayList<String> getNoGrantedPermission(Activity activity, boolean isShouldRationale) {
		ArrayList<String> permissions = new ArrayList<>();

		for (String requestPermission : REQUEST_PERMISSIONS) {
			//检查权限开启结果
			int checkSelfPermission;
			try {
				checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
			} catch (RuntimeException e) {
				ToastUtils.showToast("请开启该权限");
				LogUtils.e(TAG, "检查权限开启结果失败:" + e.getMessage());
				return null;
			}

			if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
				LogUtils.i(TAG, "获取未授权的权限 ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);

				if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
					LogUtils.d(TAG, "需要展示权限申请提醒说明框的权限");
					if (isShouldRationale) {
						permissions.add(requestPermission);
					}
				} else {
					LogUtils.d(TAG, "不需要展示权限申请提醒说明框的权限");
					if (!isShouldRationale) {
						permissions.add(requestPermission);
					}
				}
			}
		}

		return permissions;
	}

}
