package cn.leekai.phone.kaidroid.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.datas.ParamsConsts;
import cn.leekai.phone.kaidroid.datas.entity.MainMenuEntity;
import cn.leekai.phone.kaidroid.ui.adapter.MainListAdapter;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;
import cn.leekai.phone.kailib.datas.entity.ShareEntity;
import cn.leekai.phone.kailib.managers.KRouterManager;
import cn.leekai.phone.kailib.utils.ShareUtils;

/**
 * description：项目主页面
 * created on：2018/1/20 11:03
 * @author likai
 */

public class MainActivity extends CommonTitleActivity {
	/**
	 * 主logo图
	 */
	@BindView(R.id.main_logo)
	ImageView mainLogo;
	/**
	 * 可操作菜单
	 */
	@BindView(R.id.main_recyclerview)
	RecyclerView mainRecyclerview;

	/**
	 * 菜单列表数据
	 */
	private ArrayList<MainMenuEntity> menuList = new ArrayList<>();

	@Override
	protected View setContentView() {
		return View.inflate(mContext, R.layout.activity_main_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setTitle(getString(R.string.app_name));
	}

	@Override
	protected void initViews() {
		// 设置数据
		initListDatas();

		// 设置recyclerView
		mainRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));

		// 创建菜单列表adapter
		MainListAdapter menuListAdapter = new MainListAdapter(menuList);
		menuListAdapter.openLoadAnimation();
		menuListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				switch (position) {
					case 0:
						// 扫一扫
						KRouterManager.getRouter().build(ScanActivity.class).go(mContext);
						break;
					case 1:
						// 拍一拍
						KRouterManager.getRouter().build(CameraActivity.class).go(mContext);
						break;
					case 2:
						// 搜一搜
						KRouterManager.getRouter().build(SearchActivity.class).go(mContext);
						break;
					case 3:
						// 看一看
						KRouterManager.getRouter().build(WatchActivity.class).go(mContext);
						break;
					case 4:
						// 收藏
						KRouterManager.getRouter().build(CameraActivity.class).go(mContext);
						break;
					case 5:
						// 分享
						ShareEntity entity = new ShareEntity();
						entity.title = "我的分享";
						entity.titleUrl = "http://sharesdk.cn";
						entity.text = "我是分享文本";
						entity.imageUrl = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=646318226,1713875929&fm=173&s=7A9C07C1021207CA4CACE31F0300E054&w=218&h=146&img.JPEG";
						entity.url = "http://sharesdk.cn";
						ShareUtils.share(mContext, entity);
						break;
					case 6:
						// 聊天
						KRouterManager.getRouter().build(ChatActivity.class).go(mContext);
						break;
					case 7:
						// 账户
						KRouterManager.getRouter().build(MyActivity.class).go(mContext);
						break;
					case 8:
						// 设置
						KRouterManager.getRouter().build(SettingActivity.class).go(mContext);
						break;
					default:
						break;
				}
			}
		});
		// 设置adapter
		mainRecyclerview.setAdapter(menuListAdapter);
	}

	/**
	 * 设置列表数据
	 */
	private void initListDatas() {
		// 图标数据
		int[] allIconIds = {R.drawable.icon_menu_scan,
				R.drawable.icon_menu_camera,
				R.drawable.icon_menu_search,
				R.drawable.icon_menu_watch,
				R.drawable.icon_menu_favorites,
				R.drawable.icon_menu_share,
				R.drawable.icon_menu_chat,
				R.drawable.icon_menu_account,
				R.drawable.icon_menu_setting};
		// 名称数据
		String[] allNameStr = {"扫一扫", "拍一拍", "搜一搜", "看一看", "收藏", "分享", "聊天", "账户", "设置"};
		// 添加列表数据
		for (int i = 0; i < allIconIds.length; i++) {
			MainMenuEntity menuEntity = new MainMenuEntity();
			menuEntity.menuImgResId = allIconIds[i];
			menuEntity.menuName = allNameStr[i];
			menuList.add(menuEntity);
		}
	}

	@OnClick({R.id.main_logo})
	public void onViewClick(View view) {
		switch (view.getId()) {
			case R.id.main_logo:
				// 主logo点击跳转（用于不同图片或活动图片的不同地址的跳转）
				KRouterManager.getRouter().build(WebActivity.class)
						.with(ParamsConsts.URL, "http://news.baidu.com")
						.go(mContext);
				break;
			default:
				break;
		}
	}

	/**
	 * 返回键处理（返回后台）
	 */
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
}
