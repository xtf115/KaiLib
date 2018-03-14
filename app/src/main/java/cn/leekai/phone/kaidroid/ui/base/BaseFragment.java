package cn.leekai.phone.kaidroid.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kailib.datas.entity.events.EventEntity;
import cn.leekai.phone.kailib.managers.KEventManager;
import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：fragment基类
 * created on：2018/1/25 10:23
 * @author likai
 */
public abstract class BaseFragment extends Fragment {
	public Context mContext;
	public Activity mActivity;
	protected LayoutInflater mInflater;
	protected ViewGroup mContainer;
	protected View mContentView;
	Unbinder unbinder;
	/**
	 * 是否已经初始化完成
	 */
	private boolean isPrepared;
	/**
	 * 是否可见
	 */
	private boolean isVisible = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.d("J_PAGE", "pageName:" + getClass().getSimpleName());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		mContainer = container;
		mActivity = getActivity();
		mContext = getContext();

		//订阅事件
		KEventManager.getInstance().registerEvent(this);

		if (mContentView == null) {
			mContentView = createView();
			unbinder = ButterKnife.bind(this, mContentView);
			initViews();
		}
		if (container != null) {
			ViewGroup viewGroup = (ViewGroup) container.getParent();
			if (viewGroup != null) {
				viewGroup.removeView(mContentView);
			}
		}
		isPrepared = true;
		if (isVisible) {
			lazyLoad();
		}
		return mContentView;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(getContext());
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(getContext());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//注销事件
		KEventManager.getInstance().unRegisterEvent(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isVisible = getUserVisibleHint();
		if (isPrepared && isVisible) {
			lazyLoad();
		}
	}

	/**
	 * 数据懒加载
	 */
	public void lazyLoad() {

	}

	/**
	 * 创建view
	 * @return 返回要当前页面的View
	 */
	public abstract View createView();

	/**
	 * 初始化控件和数据
	 */
	public abstract void initViews();

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * 提示框：加载中
	 */
	public void showLoading() {
		showLoading(R.string.loadings);
	}

	/**
	 * 提示框
	 * @param msg 提示信息
	 */
	public void showLoading(String msg) {
		EventEntity eventInfEntity = new EventEntity(R.id.eventbus_show_loading_dialog, msg);
		KEventManager.getInstance().postEvent(eventInfEntity);
	}

	/**
	 * 提示框
	 * @param res stringID
	 */
	public void showLoading(@StringRes int res) {
		showLoading(getString(res));
	}

	/**
	 * 关闭提醒框
	 */
	public void dismissLoading() {
		EventEntity entity = new EventEntity(R.id.eventbus_dismiss_loading_dialog);
		KEventManager.getInstance().postEvent(entity);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEventMainThread(EventEntity event) {
		//统一处理
	}
}
