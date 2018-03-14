package cn.leekai.phone.kailib.managers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.R;
import cn.leekai.phone.kailib.utils.FileUtils;
import cn.leekai.phone.kailib.utils.LogUtils;
import cn.leekai.phone.kailib.utils.StringUtils;
import cn.leekai.phone.kailib.utils.ToastUtils;
import okhttp3.OkHttpClient;

/**
 * description：网络请求处理
 * created on：2018/1/19 09:42
 * @author likai
 */
public class KNetManager {
	/**
	 * 单例对象
	 */
	private static volatile KNetManager mInstance;

	/**
	 * 公共参数集合
	 *
	 */
	private HashMap<String, String> commonParams = new HashMap<>();

	private KNetManager(Application app){
		// 初始化网络配置
		initNetConfig(app);
	}

	/**
	 * 获单例
	 * @return 返回KNetManager对象
	 */
	public static KNetManager getInstance(){
		if(mInstance == null){
			synchronized (KNetManager.class){
				if(mInstance == null){
					mInstance = new KNetManager(KaiCore.getApplication());
				}
			}
		}
		return mInstance;
	}

	/**
	 * 设置网络请求的公共参数
	 * @param commonParams 公共参数集合
	 */
	public void setCommonParams(HashMap<String, String> commonParams){
		this.commonParams = commonParams;
	}

	/**
	 * 更新公共参数
	 * @param updateParams 待更新的参数集合
	 */
	public void updateCommonParams(HashMap<String, String> updateParams){
		if(updateParams != null) {
			for (Object obj : updateParams.entrySet()) {
				Map.Entry entry = (Map.Entry) obj;
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				commonParams.put(key, value);
			}
		}
	}

	/**
	 * 网络请求参数基础配置
	 */
	private void initNetConfig(Application app){
		//OkGo网络请求框架的初始化配置
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		//全局的读取超时时间(默认60秒)
		builder.readTimeout(KaiCore.getConfig().readTimeoutRequest, TimeUnit.MILLISECONDS);
		//全局的写入超时时间(默认60秒)
		builder.writeTimeout(KaiCore.getConfig().writeTimeoutRequest, TimeUnit.MILLISECONDS);
		//全局的连接超时时间(默认60秒)
		builder.connectTimeout(KaiCore.getConfig().connectTimeoutRequest, TimeUnit.MILLISECONDS);
		OkGo.getInstance()
				//必须调用初始化
				.init(app)
				//建议设置OkHttpClient，不设置将使用默认的
				.setOkHttpClient(builder.build())
				//全局统一缓存模式，默认不使用缓存，可以不传
				.setCacheMode(CacheMode.NO_CACHE)
				//全局统一缓存时间，默认永不过期，可以不传
				.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
				//全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
				.setRetryCount(KaiCore.getConfig().retryHttpTimes);
	}

	/**
	 * 加载图片
	 * @param url               图片url
	 * @param imageView         要加载的控件
	 */
	public void loadImg(Context context, String url, ImageView imageView){
		loadImg(context, url, imageView, R.drawable.icon_default_img, R.drawable.icon_default_img);
	}

	/**
	 * 加载图片
	 *
	 * @param url               图片url
	 * @param imageView         要加载的控件
	 * @param defaultDrawableID 默认图片
	 * @param errorDrawableID   错误图片
	 */
	public void loadImg(Context context, String url, ImageView imageView, int defaultDrawableID, int errorDrawableID) {
		if (imageView == null) {
			return;
		}
		if (StringUtils.isBlank(url)) {
			if (defaultDrawableID > 0) {
				imageView.setImageResource(defaultDrawableID);
			} else {
				imageView.setImageResource(R.drawable.icon_default_img);
			}
		}
		RequestManager requestManager = Glide.with(context);
		//加载图片
		DrawableTypeRequest drawableTypeRequest = requestManager.load(url);
		//设置缓存方式
		drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESULT);
		//设置默认占位图
		if (defaultDrawableID > 0) {
			drawableTypeRequest.placeholder(defaultDrawableID);
		}
		//设置错误占位图
		if (errorDrawableID > 0) {
			drawableTypeRequest.error(errorDrawableID);
		}
		//先加载缩略图，再加载原图。以原图1/10的缩略图为例
		drawableTypeRequest.thumbnail(0.1f);
		//取消加载动画
		drawableTypeRequest.dontAnimate();
		drawableTypeRequest.into(imageView);
	}

	/**
	 * Glide保存图片
	 * @param obj 可以传context、FragmentActivity、Activity、Fragment
	 * @param fileName 要保存到本地的图片文件名
	 * @param url 网络图片的url
	 */
	public void saveIMG(Object obj, final String fileName, String url) {
		RequestManager requestManager;
		try {
			if (obj instanceof FragmentActivity) {
				requestManager = Glide.with((FragmentActivity) obj);
			} else if (obj instanceof Activity) {
				requestManager = Glide.with((Activity) obj);
			} else if (obj instanceof android.app.Fragment) {
				requestManager = Glide.with((android.app.Fragment) obj);
			} else if (obj instanceof android.support.v4.app.Fragment) {
				requestManager = Glide.with((android.support.v4.app.Fragment) obj);
			} else {
				Context context = (obj instanceof Context) ? (Context) obj : KaiCore.getContext();
				requestManager = Glide.with(context);
			}
		} catch (Exception e) {
			requestManager = Glide.with(KaiCore.getContext());
		}
		requestManager.load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
			@Override
			public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
				try {
					//如果手机已插入sd卡,且app具有读写sd卡的权限
					boolean isSaved = FileUtils.savaFileToSD(fileName, bytes);
					if (isSaved) {
						ToastUtils.showToast("图片保存成功");
					} else {
						ToastUtils.showToast("图片保存失败");
					}
				} catch (Exception e) {
					LogUtils.e(e);
				}
			}
		});
	}

	public void httpGet(){

	}

	public void httpPost(){

	}
}
