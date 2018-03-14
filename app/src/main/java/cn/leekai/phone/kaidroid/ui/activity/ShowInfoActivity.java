package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.datas.ParamsConsts;
import cn.leekai.phone.kaidroid.datas.entity.databases.CollectInfoEntity;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;
import cn.leekai.phone.kaidroid.utils.CollectDbUtil;
import cn.leekai.phone.kailib.utils.StringUtils;

/**
 * description：内容展示页
 * created on：2018/1/25 11:48
 * @author likai
 */

public class ShowInfoActivity extends CommonTitleActivity {

	@BindView(R.id.show_info_tv)
	TextView showInfoTv;

	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_show_info_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setCloseTitle("提示");
		commonTitleBar.setRightTitle(R.drawable.icon_menu, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 弹出菜单

			}
		});
	}

	@Override
	protected void initViews() {
		// 展示内容
		String content = "";
		// 链接地址
		String url = "";
		if (getIntent().hasExtra(ParamsConsts.CONTENT)) {
			content = getIntent().getStringExtra(ParamsConsts.CONTENT);
			showInfoTv.setText(content);
		}
		if (getIntent().hasExtra(ParamsConsts.URL)){
			url = getIntent().getStringExtra(ParamsConsts.URL);
		}

		// 添加数据库
		if(StringUtils.isBlank(content) && StringUtils.isBlank(url)){
			CollectInfoEntity entity = new CollectInfoEntity();
			entity.url = url;
			entity.content = content;
			entity.type = 2;
			CollectDbUtil.addOneCollectInfo(entity);
		}
	}
}
