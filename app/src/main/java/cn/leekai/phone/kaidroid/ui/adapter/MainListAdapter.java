package cn.leekai.phone.kaidroid.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.entity.MainMenuEntity;

/**
 * description：主页主菜单列表adapter
 * created on：2018/1/23 18:29
 * @author likai
 */

public class MainListAdapter extends BaseQuickAdapter<MainMenuEntity, BaseViewHolder> {

	public MainListAdapter(List<MainMenuEntity> data) {
		super(R.layout.item_main_menu_layout, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, MainMenuEntity item) {
		// 加载icon图标
		helper.setImageResource(R.id.item_menu_icon, item.menuImgResId)
				// 加载菜单名称
				.setText(R.id.item_menu_name, item.menuName);
	}
}
