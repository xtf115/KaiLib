package cn.leekai.phone.kaidroid.datas;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.leekai.phone.kaidroid.R;

/**
 * description：标题栏控件集合
 * created on：2018/1/22 15:05
 * @author likai
 */

public class CommonTitleBar {
	/**
	 * 所在页面的activity
	 */
	private Activity mActivity;
	/**
	 * 标题栏
	 */
	public RelativeLayout titleBar;
	/**
	 * 左侧返回键
	 */
	public ImageButton backButton;
	/**
	 * 带返回键的标题
	 */
	public TextView backTitle;
	/**
	 * 不带返回键的标题
	 */
	public TextView homeTitle;
	/**
	 * 右侧图片按钮
	 */
	public ImageButton rightImgButton;
	/**
	 * 右侧文字按钮
	 */
	public TextView rightTxtButton;

	public CommonTitleBar(Activity mActivity){
		this.mActivity = mActivity;
	}

	/**
	 * 设置不带返回键的标题显示内容
	 * @param titleText 标题内容
	 */
	public void setTitle(String titleText){
		// 显示无返回键的标题
		homeTitle.setText(titleText);
		homeTitle.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置带返回键的标题显示内容
	 * @param titleText 标题内容
	 */
	public void setBackTitle(String titleText){
		setTitle(R.drawable.icon_back, titleText, null);
	}

	/**
	 * 设置带返回键的标题显示内容
	 * @param titleText 标题内容
	 * @param listener 点击事件
	 */
	public void setBackTitle(String titleText, View.OnClickListener listener){
		setTitle(R.drawable.icon_back, titleText, listener);
	}

	/**
	 * 设置返回按钮
	 * @param titleText 标题内容
	 */
	public void setCloseTitle(String titleText){
		setTitle(R.drawable.icon_close, titleText, null);
	}

	/**
	 * 设置返回按钮带有自定义事件
	 * @param titleText 标题内容
	 * @param listener 点击事件
	 */
	public void setCloseTitle(String titleText, View.OnClickListener listener){
		setTitle(R.drawable.icon_close, titleText, listener);
	}

	/**
	 * 设置返回标题
	 * @param resourceId 图片id
	 * @param titleText 标题内容
	 * @param listener 点击事件
	 */
	private void setTitle(int resourceId, String titleText, final View.OnClickListener listener){
		// 设置按钮显示样式和点击事件
		backButton.setImageResource(resourceId);
		if(listener != null){
			backButton.setOnClickListener(listener);
		} else {
			backButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// 关闭页面
					if(mActivity != null && !mActivity.isFinishing()){
						mActivity.finish();
					}
				}
			});
		}
		backButton.setVisibility(View.VISIBLE);
		// 设置标题内容
		backTitle.setText(titleText);
		backTitle.setVisibility(View.VISIBLE);
		// 隐藏标题其他内容
		homeTitle.setVisibility(View.GONE);
	}

	/**
	 * 设置标题栏右侧按钮操作
	 * @param resourceId 图片id
	 * @param listener 点击事件处理
	 */
	public void setRightTitle(int resourceId, final View.OnClickListener listener){
		rightImgButton.setImageResource(resourceId);
		rightImgButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 处理点击事件
				if(listener != null){
					listener.onClick(v);
				}
			}
		});
		rightImgButton.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置标题栏右侧按钮操作
	 * @param titleText 文字内容
	 * @param listener 点击事件处理
	 */
	public void setRightTitle(String titleText, final View.OnClickListener listener){
		rightTxtButton.setText(titleText);
		rightTxtButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 处理点击事件
				if(listener != null){
					listener.onClick(v);
				}
			}
		});
		rightTxtButton.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏标题
	 */
	public void hide(){
		titleBar.setVisibility(View.GONE);


	}
}
