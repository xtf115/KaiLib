package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;

/**
 * description：设置页面
 * created on：2018/1/26 17:33
 * @author likai
 */
public class SettingActivity extends CommonTitleActivity {
	@Override
	protected View setContentView() {
		return View.inflate(mContext, R.layout.activity_setting_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("设置");
	}

	@Override
	protected void initViews() {

	}
}
