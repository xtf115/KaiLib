package cn.leekai.phone.kaidroid.ui.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.ParamsConsts;
import cn.leekai.phone.kaidroid.datas.entity.DialogInfoEntity;
import cn.leekai.phone.kaidroid.datas.entity.ReportInfoEntity;
import cn.leekai.phone.kaidroid.datas.entity.databases.CollectInfoEntity;
import cn.leekai.phone.kaidroid.ui.activity.ReportActivity;
import cn.leekai.phone.kaidroid.utils.CollectDbUtil;
import cn.leekai.phone.kailib.datas.entity.MenuInfoEntity;
import cn.leekai.phone.kailib.managers.KRouterManager;
import cn.leekai.phone.kailib.utils.ShareUtils;
import cn.leekai.phone.kailib.utils.StringUtils;
import cn.leekai.phone.kailib.utils.ToastUtils;

/**
 * description：菜单弹窗
 * created on：2018/1/26 17:42
 * @author likai
 */
public class WebMenuDialog extends BaseCommonMenuDialog {

	public WebMenuDialog(Context context, DialogInfoEntity dialogInfo, CallBackListener listener) {
		super(context, dialogInfo, listener);
	}

	/**
	 * 初始化数据
	 */
	@Override
	protected void initDatas(ArrayList<MenuInfoEntity> listDatas){
		int[] menuIcons = {R.drawable.icon_sync,
				R.drawable.icon_fav,
				R.drawable.icon_search_m,
				R.drawable.icon_link,
				R.drawable.icon_explorer,
				R.drawable.icon_tousu,
				R.drawable.icon_menu_share};
		String[] menuNames = {"刷新",
				"收藏",
				"搜索页面内容",
				"复制链接",
				"在浏览器打开",
				"投诉",
				"分享"};
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
				switch (position){
					case 0:
						// 刷新
						listener.onCallBack(CanDo.REFRESH);
						break;
					case 1:
						// 收藏
						CollectInfoEntity collectInfo = new CollectInfoEntity();
						collectInfo.title = dialogInfo.getTitle();
						collectInfo.url = dialogInfo.getUrl();
						collectInfo.type = 1;
						boolean isCollect = CollectDbUtil.addOneCollectInfo(collectInfo);
						if(isCollect){
							ToastUtils.showToast("已收藏");
						} else {
							ToastUtils.showToast("提醒：收藏失败");
						}
						break;
					case 2:
						// 搜索页面内容
						listener.onCallBack(CanDo.SEARCH);
						break;
					case 3:
						// 复制链接
						StringUtils.copy(dialogInfo.getUrl());
						ToastUtils.showToast("已复制");
						break;
					case 4:
						// 在浏览器打开
						KRouterManager.getRouter().build()
								.addAction(Intent.ACTION_VIEW)
								.setData(Uri.parse(dialogInfo.getUrl()))
								.go(getContext());
						break;
					case 5:
						// 投诉
						// 设置投诉内容
						ReportInfoEntity reportInfo = new ReportInfoEntity();
						reportInfo.url = dialogInfo.getUrl();
						reportInfo.title = dialogInfo.getTitle();
						// 设置投诉参数
						Bundle mBundle = new Bundle();
						mBundle.putSerializable(ParamsConsts.REPORT_INFO, reportInfo);
						// 执行跳转
						KRouterManager.getRouter().build(ReportActivity.class)
								.with(mBundle)
								.go(getContext());
						break;
					case 6:
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
