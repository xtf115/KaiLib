package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;

/**
 * description：投诉页面
 * created on：2018/1/31 18:08
 * @author likai
 */

public class ReportActivity extends CommonTitleActivity {
	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_report_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("投诉");
	}

	@Override
	protected void initViews() {

	}
}
