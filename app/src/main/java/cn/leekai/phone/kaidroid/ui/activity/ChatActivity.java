package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;

/**
 * description：聊天页面
 * created on：2018/1/26 17:29
 * @author likai
 */

public class ChatActivity extends CommonTitleActivity {
	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_chat_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("聊天");
	}

	@Override
	protected void initViews() {

	}
}
