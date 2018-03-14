package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;

/**
 * description：拍一拍
 * created on：2018/1/24 11:35
 * @author likai
 */

public class CameraActivity extends CommonTitleActivity {
	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_camera_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("拍一拍");
	}

	@Override
	protected void initViews() {

	}
}
