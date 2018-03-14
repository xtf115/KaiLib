package cn.leekai.phone.kailib.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import cn.leekai.phone.kailib.R;
import cn.leekai.phone.kailib.datas.consts.ConfigConst;
import cn.leekai.phone.kailib.datas.consts.MagicConst;
import cn.leekai.phone.kailib.utils.LogUtils;
import cn.leekai.phone.kailib.utils.ToastUtils;

/**
 * description：自定义的webview
 * created on：2018/1/22 09:29
 * @author likai
 */

public class MyWebView extends WebView implements View.OnLongClickListener {
	private final static String TAG = "mywebview";
	private Context context;
	/**
	 * 长按事件监听
	 */
	private LongClickCallBack mCallBack;
	/**
	 * 进度条progressBar
	 */
	private ProgressBar mProgressBar;
	/**
	 * 页面加载完毕的回调方法
	 */
	private WebViewLoadCompleteListener completeListener;

	public MyWebView(Context context) {
		super(context);
	}

	public MyWebView(Context context, LongClickCallBack mCallBack) {
		super(context);
		this.context = context;
		this.mCallBack = mCallBack;
		// 初始化加载进度条
		initProgressBar();
		// 初始化webview
		initSettings();
	}

	/**
	 * 设置页面加载完毕后的回调方法
	 * @param listener 回调方法
	 */
	public void setLoadCompleteListener(WebViewLoadCompleteListener listener){
		completeListener = listener;
	}

	/**
	 * 初始化webview加载中的提示progressbar
	 */
	private void initProgressBar() {
		//添加progressBar
		mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		// 设置宽度自适应，高度为8px
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);
		mProgressBar.setLayoutParams(layoutParams);
		// 设置进度条的背景样式
		Drawable drawable = ContextCompat.getDrawable(context, R.drawable.web_progress_bar_states);
		mProgressBar.setProgressDrawable(drawable);
		addView(mProgressBar);
	}

	/**
	 * 更多设置可参考（http://blog.csdn.net/u014045181/article/details/53308537）
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initSettings() {
		// 初始化设置
		WebSettings mSettings = this.getSettings();
		//开启javascript
		mSettings.setJavaScriptEnabled(true);
		//开启DOM（HTML5 提供的一种标准的接口，主要将键值对存储在本地，在页面加载完毕后可以通过 JavaScript 来操作这些数据。）
		mSettings.setDomStorageEnabled(true);
		//设置字符编码
		mSettings.setDefaultTextEncodingName("utf-8");
		//设置web页面
		//设置支持文件流
		mSettings.setAllowFileAccess(true);
		// 支持缩放
		mSettings.setSupportZoom(true);
		// 支持缩放
		mSettings.setBuiltInZoomControls(true);
		// 不显示webview缩放按钮
		mSettings.setDisplayZoomControls(false);
		// 调整到适合webview大小
		mSettings.setUseWideViewPort(true);
		// 调整到适合webview大小
		mSettings.setLoadWithOverviewMode(true);
		//提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
		mSettings.setBlockNetworkImage(true);
		//开启缓存机制
		mSettings.setAppCacheEnabled(true);
		mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		//1.首先在WebView初始化时添加如下代码
		if (Build.VERSION.SDK_INT >= 19) {
			// 对系统API在19以上的版本作了兼容。因为4.4以上系统在onPageFinished时再恢复图片加载时,如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载
			mSettings.setLoadsImagesAutomatically(true);
		} else {
			mSettings.setLoadsImagesAutomatically(false);
		}
		// 支持内容重新布局
		mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		// 支持通过js打开新窗口
		mSettings.setJavaScriptCanOpenWindowsAutomatically(false);
		// 添加js支持接口
		addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		//重写一下。有的时候可能会出现问题
		setWebViewClient(new MyWebViewClient());
		// 设置webChromeClient的监听
		setWebChromeClient(new WebChromeClient());
		// 支持下载
		setDownloadListener(new MyWebViewDownLoadListener());
		setOnLongClickListener(this);
	}

	/**
	 * 支持下载
	 */
	private class MyWebViewDownLoadListener implements DownloadListener {
		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
			// 打开外部浏览器进行下载操作
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
	}

	/**
	 * 创建WebChromeClient 继承WebChromeClie
	 */
	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == MagicConst.NUMBER_100) {
				// 进度到达100%
				mProgressBar.setVisibility(GONE);
			} else {
				// 加载中
				if (mProgressBar.getVisibility() == GONE) {
					mProgressBar.setVisibility(VISIBLE);
				}
				// 设置加载进度
				mProgressBar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

		/**
		 * 处理javascript中的alert
		 */
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			ToastUtils.showToast(message);
			result.cancel();
			return true;
		}

		/**
		 * 处理js中的confirm
		 */
		@Override
		public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
			return super.onJsConfirm(view, url, message, result);
		}

		/**
		 * 处理js中的prompt
		 */
		@Override
		public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			LogUtils.d(TAG, "onReceivedTitle:"+title);
			// 重写webview
			completeListener.onReceivedTitle(title);
		}
	}

	/**
	 * 重写onScrollChanged
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		mProgressBar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public boolean onLongClick(View v) {
		// 长按事件监听（注意：需要实现LongClickCallBack接口并传入对象）
		//获取所点击的内容
		final HitTestResult htr = getHitTestResult();
		//判断被点击的类型为图片
		if (htr.getType() == WebView.HitTestResult.IMAGE_TYPE || htr.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
			mCallBack.onLongClickCallBack(htr.getExtra());
		}
		return false;
	}

	private class MyWebViewClient extends WebViewClient {
		/**
		 * 这个方法在用户试图点开页面上的某个链接时被调用
		 * 方法返回true代表Application自己处理url；返回false代表Webview处理url
		 * 加载过程中 拦截加载的地址url
		 * @param request web的请求资源
		 */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
			String url = request.getUrl().toString();
			return !(url.startsWith("http:") || url.startsWith("https:")) && super.shouldOverrideUrlLoading(view, request);
		}

		/**
		 * 页面开始加载调用的方法
		 */
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		/**
		 * 页面加载完成回调的方法
		 */
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			LogUtils.d(TAG, "onPageFinished:"+url);
			// 关闭图片加载阻塞
			view.getSettings().setBlockNetworkImage(false);
			// 等待页面加载完毕以后，再执行加载页面图片的操作
			if(!view.getSettings().getLoadsImagesAutomatically()) {
				view.getSettings().setLoadsImagesAutomatically(true);
			}
			// 加载网页首图
			if(getUrl().startsWith(ConfigConst.WECHAT_LINK_URL)){
				// 微信域页面
				view.loadUrl("javascript:(" +
						"function(){" +
							"var images = document.getElementsByTagName('img');" +
							"for(var i = 0; i<images.length; i++){" +
								"var url = images[i].getAttribute('data-src');" +
								"if(url != '' && url != 'undefind' && url != 'null' && url != null){" +
									"window.local_obj.showImg(url);" +
									"break;" +
								"}" +
							"};" +

						"})()");
			} else {
				// 非微信域页面
				view.loadUrl("javascript:(" +
						"function(){" +
							"var images = document.getElementsByTagName('img');" +
							"for(var i = 0; i<images.length; i++){" +
								"var url = images[i].src;" +
								"if(url != '' && url != 'undefind'){" +
									"window.local_obj.showImg(url);" +
									"break;" +
								"}" +
							"};" +
						"})()");
			}

		}

		@Override
		public void onScaleChanged(WebView view, float oldScale, float newScale) {
			super.onScaleChanged(view, oldScale, newScale);
			requestFocus();
			requestFocusFromTouch();
		}

		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
			// 处理页面加载错误

		}
	}

	/**
	 * 长按事件回调接口，传递图片地址
	 */
	public interface LongClickCallBack {
		/**
		 * 用于传递图片地址
		 * @param imgUrl 图片地址
		 */
		void onLongClickCallBack(String imgUrl);
	}

	/**
	 * 页面加载完毕后的回调
	 */
	public interface WebViewLoadCompleteListener{
		/**
		 * 页面加载完毕回调
		 * @param url 页面url
		 */
		void loadComplete(String title, String url, String firstImgUrl);

		/**
		 * 获取title后回调
		 * @param title 页面title
		 */
		void onReceivedTitle(String title);
	}

	public final class InJavaScriptLocalObj {
		/**
		 * 通过js获取页面内容
		 * @param htmlContent 页面内容
		 */
		@JavascriptInterface
		public void showSource(String htmlContent) {
			LogUtils.d(TAG, "htmlContent:"+htmlContent);
		}

		/**
		 * 通过js获取页面图片
		 * @param imgurl 图片链接
		 */
		@JavascriptInterface
		public void showImg(final String imgurl) {
			LogUtils.d(TAG, "showImg:"+imgurl);
			// 页面加载完毕，进行回调
			if(completeListener != null){
				post(new Runnable() {
					@Override
					public void run() {
						completeListener.loadComplete(getTitle(), getUrl(), imgurl);
					}
				});
			}
		}
	}
}
