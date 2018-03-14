package cn.leekai.phone.kaidroid.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.EnumSet;
import java.util.Hashtable;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.CommonTitleBar;
import cn.leekai.phone.kaidroid.datas.ParamsConsts;
import cn.leekai.phone.kaidroid.datas.entity.DialogInfoEntity;
import cn.leekai.phone.kaidroid.datas.entity.databases.CollectInfoEntity;
import cn.leekai.phone.kaidroid.ui.base.CommonTitleActivity;
import cn.leekai.phone.kaidroid.ui.view.JyRGBLuminanceSource;
import cn.leekai.phone.kaidroid.ui.view.dialog.WebMenuDialog;
import cn.leekai.phone.kaidroid.utils.CollectDbUtil;
import cn.leekai.phone.kaidroid.utils.DialogUtil;
import cn.leekai.phone.kailib.datas.entity.ShareEntity;
import cn.leekai.phone.kailib.managers.KNetManager;
import cn.leekai.phone.kailib.ui.views.MyWebView;
import cn.leekai.phone.kailib.ui.views.MyWebView.LongClickCallBack;
import cn.leekai.phone.kailib.utils.LogUtils;
import cn.leekai.phone.kailib.utils.StringUtils;
import cn.leekai.phone.kailib.utils.ToastUtils;

/**
 * description：网页加载activity
 * created on：2018/1/22 09:18
 * @author likai
 */

public class WebActivity extends CommonTitleActivity implements LongClickCallBack {
	/**
	 * webview 控件
	 */
	private MyWebView webView;
	/**
	 * 放置webview的容器
	 */
	private FrameLayout webRootLayout;
	/**
	 * 当前页面的url链接
	 */
	private String pageUrl = "";
	/**
	 * 长按页面图片弹出的选择框中的list的adapter
	 */
	private ArrayAdapter<String> adapter;
	/**
	 * 二维码解析结果
	 */
	private Result result;
	/**
	 * 操作菜单操作内容
	 */
	private DialogInfoEntity dialogInfo = new DialogInfoEntity();

	@Override
	protected View setContentView() {
		return View.inflate(this, R.layout.activity_web_layout, null);
	}

	@Override
	protected void setTitleViews(CommonTitleBar commonTitleBar) {
		commonTitleBar.setCloseTitle(getString(R.string.app_name));
		commonTitleBar.setRightTitle(R.drawable.icon_menu, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 显示操作菜单
				DialogUtil.showBottomMenu(mContext, dialogInfo, new WebMenuDialog.CallBackListener() {
					@Override
					public void onCallBack(WebMenuDialog.CanDo tag) {
						switch (tag) {
							case REFRESH:
								// 刷新页面
								refresh();
								break;
							case SEARCH:
								// 搜索页面

								break;
							default:
								break;
						}
					}
				});
			}
		});
	}

	/**
	 * 刷新页面
	 */
	private void refresh() {
		if (webView != null) {
			webView.reload();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (getIntent().hasExtra(ParamsConsts.URL)) {
			pageUrl = getIntent().getStringExtra(ParamsConsts.URL);
			if (!StringUtils.isBlank(pageUrl) && webView != null) {
				dialogInfo.setUrl(pageUrl);
				webView.loadUrl(pageUrl);
			}
		}
	}

	@Override
	protected void initViews() {
		// 获取加载的URL地址
		if (getIntent().hasExtra(ParamsConsts.URL)) {
			pageUrl = getIntent().getStringExtra(ParamsConsts.URL);
			dialogInfo.setUrl(pageUrl);
		}
		// weblayout的rootview
		webRootLayout = findViewById(R.id.web_root_layout);
		// 初始化webview
		webView = new MyWebView(this, this);
		// 设置加载完毕的回调监听
		webView.setLoadCompleteListener(new MyWebView.WebViewLoadCompleteListener() {
			@Override
			public void loadComplete(String title, String url, String firstImgUrl) {
				// 设置数据
				dialogInfo.setTitle(title);
				dialogInfo.setUrl(url);
				dialogInfo.setImgUrl(firstImgUrl);
				//可分享的内容
				ShareEntity shareInfo = new ShareEntity();
				shareInfo.title = dialogInfo.getTitle();
				shareInfo.titleUrl = dialogInfo.getUrl();
				shareInfo.text = "我正在看["+dialogInfo.getTitle()+"],分享给你，一起看吧！";
				shareInfo.imageUrl = dialogInfo.getImgUrl();
				shareInfo.url = dialogInfo.getUrl();
				dialogInfo.setShareInfo(shareInfo);
				// 进行访问数据添加
				CollectInfoEntity entity = new CollectInfoEntity();
				entity.createTime = System.currentTimeMillis();
				entity.title = dialogInfo.getTitle();
				entity.url = dialogInfo.getUrl();
				entity.imgUrl = dialogInfo.getImgUrl();
				entity.type = 1;
				// 收集当前页面
				CollectDbUtil.addOneCollectInfo(entity);
			}

			@Override
			public void onReceivedTitle(String title) {
				// 设置页面title
				if (getTitleViews().backTitle != null) {
					getTitleViews().backTitle.setText(title);
				}
			}
		});
		// 加载页面
		webView.loadUrl(pageUrl);
		webView.setFocusable(true);
		webView.setFocusableInTouchMode(true);
		// 添加webview到页面
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		webRootLayout.addView(webView, lp);
	}

	@Override
	public void onLongClickCallBack(String imgUrl) {
		// 获取到图片地址后做相应的处理
		Message msg = new Message();
		msg.what = 0;
		msg.obj = imgUrl;
		handler.sendMessage(msg);
		// 检测出图片，弹出dialog
		showDialog(imgUrl);
	}

	/**
	 * 显示图片长按操作的Dialog
	 */
	private void showDialog(final String fileUrl) {
		adapter = new ArrayAdapter<>(this, R.layout.item_dialog_layout, R.id.item_dialog_info);
		adapter.add("保存到手机");
		adapter.add("收藏");

		AlertDialog mCustomDialog = new AlertDialog.Builder(this).setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String itemName = adapter.getItem(which);
				// 点击事件
				switch (itemName) {
					case "保存到手机":
						// 保存到手机相册
						KNetManager.getInstance().saveIMG(mContext, "img_" + System.currentTimeMillis() + ".jpg", fileUrl);
						dialog.dismiss();
						break;
					case "识别图中二维码":
						// 识别二维码
						if (result != null) {
							String webUrl = result.toString();
							if (!StringUtils.isBlank(webUrl)) {
								Intent webIntent = new Intent(mContext, WebActivity.class);
								webIntent.putExtra(ParamsConsts.URL, webUrl);
								startActivity(webIntent);
							}
						}
						dialog.dismiss();
						break;
					case "收藏":
						// 收藏
						CollectInfoEntity entity = new CollectInfoEntity();
						entity.imgUrl = fileUrl;
						entity.type = 3;
						boolean isCollect = CollectDbUtil.addOneCollectInfo(entity);
						if(isCollect) {
							ToastUtils.showToast("收藏成功");
						} else {
							ToastUtils.showToast("收藏失败");
						}
						break;
					default:
						break;
				}
			}
		}).create();
		mCustomDialog.show();
	}

	/**
	 * 是二维码时，才添加为识别二维码
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					// 检测二维码
					decodeImage(msg.obj.toString());
					break;
				case 1:
					// 检测二维码通过，显示二维码操作按钮
					adapter.add("识别图中二维码");
					adapter.notifyDataSetChanged();
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 判断是否为二维码
	 * @param sUrl 图片地址
	 */
	private void decodeImage(String sUrl) {
		//方法中设置asBitmap可以设置回调类型
		Glide.with(mContext).load(sUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
				result = handleQRCodeFormBitmap(resource);
				if (result != null) {
					handler.sendEmptyMessage(1);
				}
			}
		});
	}

	/**
	 * 检测获取图片内容
	 */
	public Result handleQRCodeFormBitmap(Bitmap bitmap) {
		MultiFormatReader mMultiFormatReader = new MultiFormatReader();
		Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
		hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		mMultiFormatReader.setHints(hints);

		JyRGBLuminanceSource source = new JyRGBLuminanceSource(bitmap);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader2 = new QRCodeReader();
		Result result = null;
		try {
			result = reader2.decode(binaryBitmap, hints);
		} catch (NotFoundException | ChecksumException | FormatException e) {
			LogUtils.e(e);
		}
		return result;
	}

	/**
	 * 返回操作的处理
	 */
	@Override
	public void onBackPressed() {
		if (webView != null && webView.canGoBack()) {
			webView.goBack();
		} else {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (webRootLayout != null && webView != null) {
			webRootLayout.removeView(webView);
			webRootLayout.removeAllViews();
			webView.loadUrl("");
			webView.destroy();
		}
	}
}
