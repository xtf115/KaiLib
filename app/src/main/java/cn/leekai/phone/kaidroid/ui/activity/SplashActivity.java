package cn.leekai.phone.kaidroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.ui.base.BaseActivity;
import cn.leekai.phone.kailib.KaiCore;

/**
 * 闪屏页面
 *
 * @author likai
 */
public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_layout);

		// 项目初始化
		KaiCore.getConfig().initSystemInfo(this);

		// 闪屏页、1秒后跳转
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent mainIntent = new Intent(mContext, MainActivity.class);
				startActivity(mainIntent);
				finish();
			}
		}, 1000);
	}
}
