package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;

/**
 * description：搜一搜
 * created on：2018/1/24 11:18
 * @author likai
 */

public class SearchActivity extends CommonTitleActivity {
	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_search_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("搜一搜");
	}

	@Override
	protected void initViews() {

	}
}
