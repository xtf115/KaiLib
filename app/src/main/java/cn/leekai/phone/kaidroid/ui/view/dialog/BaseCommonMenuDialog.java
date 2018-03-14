package cn.leekai.phone.kaidroid.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.entity.DialogInfoEntity;
import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.datas.entity.MenuInfoEntity;

/**
 * description：
 * created on：2018/2/1 17:59
 * @author likai
 */

public abstract class BaseCommonMenuDialog extends Dialog {
	/**
	 * 菜单信息集合
	 */
	private ArrayList<MenuInfoEntity> menuDatas = new ArrayList<>();

	/**
	 * 当前菜单需要处理的内容信息
	 */
	private DialogInfoEntity dialogInfo;
	/**
	 * 回调方法
	 */
	private CallBackListener callBackListener;

	/**
	 * 操作标识
	 */
	public enum CanDo {
		// 刷新页面
		REFRESH,
		// 复制链接
		COPY,
		// 收藏
		FAVRATE,
		// 搜索
		SEARCH,
		// 打开浏览器
		EXPLORER,
		// 投诉
		REPORT,
		// 删除
		DELETE,
		// 重命名
		RENAME
	}

	/**
	 * 构造方法
	 * @param context context
	 * @param listener 回调方法
	 */
	public BaseCommonMenuDialog(Context context, BaseCommonMenuDialog.CallBackListener listener){
		super(context, R.style.dialog);
		this.callBackListener = listener;
	}

	/**
	 * 构造方法
	 * @param context context
	 * @param dialogInfo 菜单操作内容
	 * @param listener 回调方法
	 */
	public BaseCommonMenuDialog(Context context, DialogInfoEntity dialogInfo, BaseCommonMenuDialog.CallBackListener listener) {
		super(context, R.style.dialog);
		this.dialogInfo = dialogInfo;
		this.callBackListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_bottom_menu_layout);

		// 设置显示位置
		Window window = getWindow();
		if(window != null) {
			WindowManager.LayoutParams params = window.getAttributes();
			params.width = KaiCore.getConfig().screenWidth;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			window.setAttributes(params);
			window.setGravity(Gravity.BOTTOM);
			// 设置窗口弹出动画
			window.setWindowAnimations(R.style.AnimBottom);
		}

		// 初始化数据
		initDatas(menuDatas);

		// 设置页面控件
		initViews();
	}

	/**
	 * 初始化列表数据
	 * @param listDatas 列表项数据
	 */
	protected abstract void initDatas(ArrayList<MenuInfoEntity> listDatas);

	/**
	 * 点击回调方法
	 * @param adapter 列表adapter
	 * @param callBackListener 点击回调方法
	 */
	protected abstract void initCallBack(BaseQuickAdapter adapter, DialogInfoEntity dialogInfo, CallBackListener callBackListener);

	/**
	 * 设置页面控件
	 *
	 */
	private void initViews() {
		// 菜单列表
		RecyclerView menuRecyclerView = findViewById(R.id.dialog_menu_recyclerview);
		// 设置列表样式
		menuRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
		// 设置RecyclerView的adapter
		BaseCommonMenuDialog.MenuListAdapter adapter = new BaseCommonMenuDialog.MenuListAdapter(menuDatas);
		menuRecyclerView.setAdapter(adapter);

		// 设置点击后操作
		initCallBack(adapter, dialogInfo, callBackListener);
	}

	/**
	 * 列表adapter
	 */
	private class MenuListAdapter extends BaseQuickAdapter<MenuInfoEntity, BaseViewHolder> {

		public MenuListAdapter(List<MenuInfoEntity> data) {
			super(R.layout.item_dialog_menu_layout, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, MenuInfoEntity item) {
			// 设置icon
			helper.setImageResource(R.id.item_dialog_menu_icon, item.iconResId)
					//设置名称
					.setText(R.id.item_dialog_menu_name, item.name);
		}
	}

	/**
	 * 回调方法
	 */
	public interface CallBackListener{
		/**
		 * 回调方法
		 * @param tag 回调标识
		 */
		void onCallBack(CanDo tag);
	}
}
