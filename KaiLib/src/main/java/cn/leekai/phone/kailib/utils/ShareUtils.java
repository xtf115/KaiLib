package cn.leekai.phone.kailib.utils;

import android.content.Context;

import java.util.HashMap;

import cn.leekai.phone.kailib.datas.entity.ShareEntity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

/**
 * description：
 * created on：2018/2/1 11:44
 * @author likai
 */

public class ShareUtils {
	private final static String TAG = "shareUtils";

	/**
	 * 执行分享
	 * @param context 页面context
	 * @param shareInfo 分享内容
	 */
	public static void share(Context context, ShareEntity shareInfo){
		share(context, shareInfo, null);
	}


	/**
	 * 执行分享
	 * @param context 页面context
	 * @param shareInfo 分享内容
	 * @param listener 回调方法
	 */
	public static void share(Context context, ShareEntity shareInfo, final PlatformActionListener listener) {
		if(shareInfo == null){
			return;
		}

		LogUtils.d(TAG, "title:"+shareInfo.title);
		LogUtils.d(TAG, "titleUrl:"+shareInfo.titleUrl);
		LogUtils.d(TAG, "text:"+shareInfo.text);
		LogUtils.d(TAG, "imagePath:"+shareInfo.imagePath);
		LogUtils.d(TAG, "imageUrl:"+shareInfo.imageUrl);
		LogUtils.d(TAG, "url:"+shareInfo.url);
		LogUtils.d(TAG, "comment:"+shareInfo.comment);

		OnekeyShare oks = new OnekeyShare();
		oks.setTheme(OnekeyShareTheme.CLASSIC);
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，微信、QQ和QQ空间等平台使用
		oks.setTitle(shareInfo.title);
		// titleUrl QQ和QQ空间跳转链接
		oks.setTitleUrl(shareInfo.titleUrl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(shareInfo.text);
		if (!StringUtils.isBlank(shareInfo.imagePath)) {
			// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
			// 确保SDcard下面存在此张图片
			oks.setImagePath(shareInfo.imagePath);
		}
		// imageUrl 图片链接地址
		oks.setImageUrl(shareInfo.imageUrl);
		// url在微信、微博，Facebook等平台中使用
		oks.setUrl(shareInfo.url);
		// comment是我对这条分享的评论，仅在人人网使用
		if(!StringUtils.isBlank(shareInfo.comment)){
			oks.setComment(shareInfo.comment);
		}
		// 分享回调方法
		if (listener != null) {
			oks.setCallback(new PlatformActionListener() {
				@Override
				public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
					listener.onComplete(platform, i, hashMap);
				}

				@Override
				public void onError(Platform platform, int i, Throwable throwable) {
					listener.onError(platform, i, throwable);
				}

				@Override
				public void onCancel(Platform platform, int i) {
					listener.onCancel(platform, i);
				}
			});
		}
		// 启动分享GUI
		oks.show(context);
	}
}
