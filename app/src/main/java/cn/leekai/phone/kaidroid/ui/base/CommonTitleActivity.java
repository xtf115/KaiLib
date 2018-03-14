package cn.leekai.phone.kaidroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;

/**
 * description：带有处理公共导航栏的activity基类
 * created on：2018/1/22 11:30
 * @author likai
 */

public abstract class CommonTitleActivity extends BaseActivity {
	private CommonTitleBar commonTitleBar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_layout);

		//设置页面显示view
		ViewGroup mContentView = findViewById(R.id.layout_content);
		mContentView.addView(setContentView());

		// 设置公共标题栏
		initTitleView();

		// butterknife初始化
		ButterKnife.bind(this);

		//设置页面
		initViews();
	}

	/**
	 * 公共标题设置
	 */
	private void initTitleView(){
		commonTitleBar = new CommonTitleBar(this);
		commonTitleBar.titleBar = findViewById(R.id.titlebar);
		commonTitleBar.backButton = findViewById(R.id.titlebar_backbtn);
		commonTitleBar.backTitle = findViewById(R.id.titlebar_backname);
		commonTitleBar.homeTitle = findViewById(R.id.titlebar_homename);
		commonTitleBar.rightImgButton = findViewById(R.id.titlebar_right_imgbtn);
		commonTitleBar.rightTxtButton = findViewById(R.id.titlebar_right_txtbtn);
		//设置titleBar
		setTitleViews(commonTitleBar);
	}

	/**
	 * 获取titleBar
	 */
	protected CommonTitleBar getTitleViews(){
		return commonTitleBar;
	}

	/**
	 * 设置加载页面的layout
	 */
	protected abstract View setContentView();

	/**
	 * 自定义公共titlebar
	 * @param commonTitleBar 当前页面的actionbar
	 */
	protected abstract void setTitleViews(CommonTitleBar commonTitleBar);

	/**
	 * 页面控件初始化
	 */
	protected abstract void initViews();
}
