package cn.leekai.phone.kaidroid.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.datas.ParamsConsts;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;
import cn.leekai.phone.kailib.utils.PhoneUtils;
import cn.leekai.phone.kailib.utils.StringUtils;
import cn.leekai.phone.kailib.utils.ToastUtils;

/**
 * description：扫一扫页面
 * created on：2018/1/24 11:09
 * @author likai
 */

public class ScanActivity extends CommonTitleActivity {
	/**
	 * 扫描fragment
	 */
	private CaptureFragment captureFragment;

	private Handler mHandler = new Handler();

	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_scan_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("二维码");
		commonTitleBar.setRightTitle(R.drawable.icon_menu, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 查看相册，进行扫描

			}
		});
	}

	@Override
	protected void initViews() {


		// 在Activity中执行Fragment的初始化操作
		initCapture();
	}
	/**
	 * 初始化自定义扫描框
	 */
	private void initCapture(){
		// 在Activity中执行Fragment的初始化操作
		// 执行扫面Fragment的初始化操作
		captureFragment = new CaptureFragment();
		// 为二维码扫描界面设置定制化界面
		CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_my_camera);
		// 设置扫描回调
		captureFragment.setAnalyzeCallback(analyzeCallback);
		// 替换我们的扫描控件
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
		// 延时显示动画
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				initLineAnimation();
			}
		}, 100);
	}

	/**
	 * 配置扫描线的动画
	 */
	private void initLineAnimation(){
		if(captureFragment.getView() != null){
			TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
					0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.9f);
			animation.setDuration(4500);
			animation.setRepeatCount(-1);
			animation.setRepeatMode(Animation.RESTART);
			ImageView scanLine = captureFragment.getView().findViewById(R.id.scan_image);
			scanLine.startAnimation(animation);
		}
	}

	/**
	 * 扫描结果
	 */
	private CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
		@Override
		public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
			if(PhoneUtils.isConnected()){
				// 有网络
				// 检查连接地址，如果是扫码登录的地址，跳转至扫码登录成功页
				if(StringUtils.isWebUrl(result)){
					// 是网址-跳转至web链接加载页
					Intent webIntent = new Intent(mContext, WebActivity.class);
					webIntent.putExtra(ParamsConsts.URL, result);
					startActivity(webIntent);
				} else {
					// 不是网址-跳转至内容显示页
					Intent webIntent = new Intent(mContext, ShowInfoActivity.class);
					webIntent.putExtra(ParamsConsts.CONTENT, result);
					startActivity(webIntent);
				}
				finish();
			} else {
				// 无网络
				ToastUtils.showToast("网络不可用");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						initCapture();
					}
				}, 1000);
			}
		}

		@Override
		public void onAnalyzeFailed() {
			// 扫码失败
			ToastUtils.showLongToast("解析二维码失败");
			// 延时1秒，重新初始化扫描配置
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					initCapture();
				}
			}, 1000);
		}
	};
}
