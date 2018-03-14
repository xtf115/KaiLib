package cn.leekai.phone.kaidroid.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.datas.ParamsConsts;
import cn.leekai.phone.kaidroid.datas.entity.DialogInfoEntity;
import cn.leekai.phone.kaidroid.datas.entity.databases.CollectInfoEntity;
import cn.leekai.phone.kaidroid.ui.adapter.WatchListAdapter;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;
import cn.leekai.phone.kaidroid.ui.view.dialog.BaseCommonMenuDialog;
import cn.leekai.phone.kaidroid.ui.view.dialog.BaseCommonMenuDialog.CanDo;
import cn.leekai.phone.kaidroid.utils.CollectDbUtil;
import cn.leekai.phone.kaidroid.utils.DialogUtil;
import cn.leekai.phone.kailib.datas.entity.ShareEntity;
import cn.leekai.phone.kailib.managers.KRouterManager;

/**
 * description：看一看
 * created on：2018/1/24 11:21
 * @author likaip
 */
public class WatchActivity extends CommonTitleActivity {
	@BindView(R.id.watch_recyclerview)
	RecyclerView watchRecyclerview;
	/**
	 * 页码
	 */
	private int page = 1;
	/**
	 * 每页加载数量
	 */
	private int pageSize = 10;
	/**
	 * 列表数据
	 */
	private ArrayList<CollectInfoEntity> dataLists = new ArrayList<>();
	/**
	 * 列表adapter
	 */
	private WatchListAdapter adapter;

	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_watch_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setBackTitle("看一看");
		commonTitleBar.setRightTitle(R.drawable.icon_menu_camera, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				KRouterManager.getRouter()
						.build(ScanActivity.class)
						.go(mContext);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 刷新列表
		// 重新获取首页信息
		resetDataList();
	}

	/**
	 * 刷新页面数据
	 */
	private void resetDataList(){
		ArrayList<CollectInfoEntity> currentDataList = CollectDbUtil.getCollectInfoList(1, pageSize);
		if(currentDataList.size() > 0 && dataLists.size() > 0 && currentDataList.get(0).url.equals(dataLists.get(0).url)){
			// 之前页面有数据，现在重新获取的列表首页有数据
			// 之前页面的第一项数据与当前获取的新数据的首相数据不同，判断为需要刷新数据
			// 重新添加数据
			dataLists.clear();
			dataLists.addAll(currentDataList);
			// 重置为首页
			page = 1;
			// 重新刷新数据
			adapter.setNewData(dataLists);
		}
	}

	@Override
	protected void initViews() {
		// 初始化adapter
		adapter = new WatchListAdapter(dataLists);
		// 设置点击事件
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				CollectInfoEntity entity = dataLists.get(position);
				switch (entity.type){
					case 1:
						// 链接
						KRouterManager.getRouter().build(WebActivity.class).with(ParamsConsts.URL, entity.url).go(mContext);
						break;
					case 2:
						// 文字

						break;
					case 3:
						// 图片

						break;
					default:
						break;
				}
			}
		});
		// 长按事件
		adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
				final CollectInfoEntity collectInfo = (CollectInfoEntity)adapter.getData().get(position);
				// 操作菜单中的参数
				DialogInfoEntity dialogInfo = new DialogInfoEntity();
				dialogInfo.setTitle(collectInfo.title);
				dialogInfo.setUrl(collectInfo.url);
				//可分享的内容
				ShareEntity shareInfo = new ShareEntity();
				shareInfo.title = collectInfo.title;
				shareInfo.titleUrl = collectInfo.url;
				shareInfo.text = collectInfo.title;
				shareInfo.imageUrl = collectInfo.imgUrl;
				shareInfo.url = collectInfo.url;
				dialogInfo.setShareInfo(shareInfo);
				// 弹出操作菜单
				DialogUtil.showListMenu(mContext, dialogInfo, new BaseCommonMenuDialog.CallBackListener() {
					@Override
					public void onCallBack(CanDo tag) {
						switch (tag){
							case DELETE:
								// 删除一项

								break;
							case RENAME:
								// 重命名一项
								KRouterManager.getRouter().build(EditCollectActivity.class)
										.with(ParamsConsts.ROW_ID, collectInfo.id)
										.go(mContext);
								break;
							default:
								break;
						}
					}
				});
				return true;
			}
		});
		// 底部加载更多
		adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				getDataList(false);
			}
		}, watchRecyclerview);
		// 设置显示的layoutmanager
		watchRecyclerview.setLayoutManager(new LinearLayoutManager(this));
		// recycleView设置adapter
		watchRecyclerview.setAdapter(adapter);

		// 查询数据
		getDataList(false);
	}

	/**
	 * 获取列表数据
	 */
	private void getDataList(final boolean isRefresh){
		watchRecyclerview.postDelayed(new Runnable() {
			@Override
			public void run() {
				// 获取列表
				ArrayList<CollectInfoEntity> currentDataList = CollectDbUtil.getCollectInfoList(page, pageSize);

				if(currentDataList.size() <= 0) {
					// 新一页没有新数据
					adapter.loadMoreComplete();
					adapter.setEnableLoadMore(false);
				} else {
					// 如果是首页的情况，需要清除缓存，重新加载数据
					if(isRefresh){
						// 刷新首页
						page = 1;
						dataLists.clear();
						adapter.setNewData(currentDataList);
						adapter.setEnableLoadMore(true);
					} else {
						// 继续加载
						page++;
						dataLists.addAll(currentDataList);
						adapter.notifyDataSetChanged();
					}
					// 加载结束
					adapter.loadMoreComplete();
				}
			}
		}, 500);
	}
}
