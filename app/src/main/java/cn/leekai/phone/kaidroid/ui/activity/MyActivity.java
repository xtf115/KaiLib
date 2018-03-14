package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;

/**
 * description：我的（账户）
 * created on：2018/2/1 11:39
 * @author likai
 */

public class MyActivity extends CommonTitleActivity {
	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_my_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("我的");
	}

	@Override
	protected void initViews() {

	}
}
