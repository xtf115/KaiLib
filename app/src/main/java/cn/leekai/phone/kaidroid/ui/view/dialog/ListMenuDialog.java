package cn.leekai.phone.kaidroid.ui.view.dialog;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.entity.DialogInfoEntity;
import cn.leekai.phone.kailib.datas.entity.MenuInfoEntity;
import cn.leekai.phone.kailib.utils.ShareUtils;

/**
 * description：列表操作菜单弹窗
 * created on：2018/2/1 18:20
 * @author likai
 */

public class ListMenuDialog extends BaseCommonMenuDialog {

	public ListMenuDialog(Context context, DialogInfoEntity dialogInfo, CallBackListener listener) {
		super(context, dialogInfo, listener);
	}

	@Override
	protected void initDatas(ArrayList<MenuInfoEntity> listDatas) {
		// 菜单图标
		int[] menuIcons = {R.drawable.icon_sync, R.drawable.icon_fav, R.drawable.icon_menu_share};
		// 菜单名称
		String[] menuNames = {"删除", "重命名", "分享"};
		// 创建菜单项
		for (int i = 0; i < menuIcons.length; i++) {
			MenuInfoEntity entity = new MenuInfoEntity();
			entity.iconResId = menuIcons[i];
			entity.name = menuNames[i];
			listDatas.add(entity);
		}
	}


	@Override
	protected void initCallBack(BaseQuickAdapter adapter, final DialogInfoEntity dialogInfo, final CallBackListener listener) {
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				switch (position) {
					case 0:
						// 删除
						listener.onCallBack(CanDo.DELETE);
						break;
					case 1:
						// 收藏

						break;
					case 2:
						// 分享
						ShareUtils.share(getContext(), dialogInfo.getShareInfo());
						break;
					default:
						break;
				}
				// 关闭弹窗
				dismiss();
			}
		});
	}
}
