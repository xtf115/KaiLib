package cn.leekai.phone.kailib.managers.router;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;

import java.io.Serializable;

/**
 * description：
 * created on：2018/1/24 15:48
 * @author likai
 */

public class RouteRequest implements Serializable {
	/**
	 * 金融requestCode的标识
	 */
	private static final int INVALID_REQUEST_CODE = -1;
	/**
	 * 待加载的activity的class
	 */
	private Class activityClass;
	/**
	 * 页面跳转携带的参数
	 */
	private Bundle extras;
	/**
	 * flags标识
	 */
	private int flags;
	/**
	 * 传入的是Uri，用于数据的过滤。setData可以被系统用来寻找匹配目标组件。
	 */
	private Uri data;
	/**
	 * 文件类型（如选择图片时候设置为：intent.setType("image/*");）
	 */
	private String type;
	/**
	 * 设置action
	 */
	private String action;
	/**
	 * 进行startActivityForResult的需要传递的requestCode
	 */
	private int requestCode = INVALID_REQUEST_CODE;
	/**
	 * 入场动画
	 */
	private int enterAnim;
	/**
	 * 出场动画
	 */
	private int exitAnim;
	/**
	 * 页面动画
	 */
	@Nullable
	private ActivityOptionsCompat activityOptionsCompat;

	public RouteRequest(){

	}

	public RouteRequest(Class activityClass) {
		this.activityClass = activityClass;
	}

	public Class getActivityClass() {
		return activityClass;
	}

	public void setActivityClass(Class activityClass) {
		this.activityClass = activityClass;
	}

	public Bundle getExtras() {
		return extras;
	}

	public void setExtras(Bundle extras) {
		this.extras = extras;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public void addFlags(int flags) {
		this.flags |= flags;
	}

	public Uri getData() {
		return data;
	}

	public void setData(Uri data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		if (requestCode < 0) {
			this.requestCode = INVALID_REQUEST_CODE;
		} else {
			this.requestCode = requestCode;
		}
	}

	public int getEnterAnim() {
		return enterAnim;
	}

	public void setEnterAnim(int enterAnim) {
		this.enterAnim = enterAnim;
	}

	public int getExitAnim() {
		return exitAnim;
	}

	public void setExitAnim(int exitAnim) {
		this.exitAnim = exitAnim;
	}

	@Nullable
	public ActivityOptionsCompat getActivityOptionsCompat() {
		return activityOptionsCompat;
	}

	public void setActivityOptionsCompat(@Nullable ActivityOptionsCompat activityOptionsCompat) {
		this.activityOptionsCompat = activityOptionsCompat;
	}
}
