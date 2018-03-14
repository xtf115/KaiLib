package cn.leekai.phone.kaidroid.ui.view.dialog;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kailib.datas.entity.MenuInfoEntity;

/**
 * description：简单弹出菜单(暂时废弃）
 * created on：2018/2/1 15:45
 * @author likai
 */

public class ClickMenuDialog extends PopupWindow {
	/**
	 * 列表数据
	 */
	private ArrayList<MenuInfoEntity> menuList = new ArrayList<>();

	public ClickMenuDialog(Context context) {
		super(context);
		View view = View.inflate(context, R.layout.dialog_menu_pop_layout, null);
		setContentView(view);

		// 这个设置还是必须的
		setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
		// 背景透明，和布局文件是分开的
		setBackgroundDrawable(new PaintDrawable(0x00000000));
		setAnimationStyle(R.style.animTranslate);

		// 设置列表数据
		setDataList(1);

		// 初始化列表adapter
		MenuListAdapter adapter = new MenuListAdapter(menuList);
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				switch (position) {
					case 0:

						break;
					case 1:

						break;
					default:
						break;
				}
			}
		});

		// 初始化页面控件
		RecyclerView menuPopLv = view.findViewById(R.id.menuPopLv);
		menuPopLv.setLayoutManager(new LinearLayoutManager(context));
		menuPopLv.setAdapter(adapter);

		// 外部点击可以消失
		setOutsideTouchable(true);
		// 拦截事件防止事件穿透
		setFocusable(true);
	}

	/**
	 * 设置列表数据
	 */
	private void setDataList(int type){
		String[] names = new String[]{"删除", "重命名"};
		switch (type){
			case 1:
				for (String name : names){
					MenuInfoEntity entity = new MenuInfoEntity();
					entity.name = name;
					menuList.add(entity);
				}
				break;
			case 2:

				break;
			default:
				break;
		}

	}

	/**
	 * 列表adapter
	 */
	private class MenuListAdapter extends BaseQuickAdapter<MenuInfoEntity, BaseViewHolder> {

		public MenuListAdapter(List<MenuInfoEntity> data) {
			super(R.layout.popup_menu_layout, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, MenuInfoEntity item) {
			// 设置显示文字
			helper.setText(R.id.popupMenuTv, item.name);
		}
	}
}
