package cn.leekai.phone.kaidroid.ui.activity;

import android.view.View;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.datas.ParamsConsts;
import cn.leekai.phone.kaidroid.datas.entity.databases.CollectInfoEntity;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;
import cn.leekai.phone.kaidroid.utils.CollectDbUtil;

/**
 * description：编辑收藏内容
 * created on：2018/2/1 19:28
 * @author likai
 */
public class EditCollectActivity extends CommonTitleActivity {
	/**
	 * 代操作的收藏信息在数据库中的id
	 */
	private int rowId;

	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_edit_collect_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("编辑");
	}

	@Override
	protected void initViews() {
		if(getIntent().hasExtra(ParamsConsts.ROW_ID)){
			rowId = getIntent().getIntExtra(ParamsConsts.ROW_ID, 0);

			CollectInfoEntity entity = CollectDbUtil.getOneCollectItem(rowId);
		}

	}
}
