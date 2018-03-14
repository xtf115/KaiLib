package cn.leekai.phone.kaidroid.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.leekai.phone.kaidroid.R;
import cn.leekai.phone.kaidroid.datas.entity.databases.CollectInfoEntity;
import cn.leekai.phone.kailib.managers.KNetManager;
import cn.leekai.phone.kailib.utils.DateUtils;
import cn.leekai.phone.kailib.utils.StringUtils;

/**
 * description：看一看列表页的adapter
 * created on：2018/1/26 09:54
 * @author likai
 */

public class WatchListAdapter extends BaseQuickAdapter<CollectInfoEntity, BaseViewHolder> {
	public WatchListAdapter(
			List<CollectInfoEntity> data) {
		super(R.layout.item_watch_list_layout, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, CollectInfoEntity item) {
		switch (item.type){
			case 1:
				// 链接
				helper.setVisible(R.id.item_watch_title, true);
				helper.setVisible(R.id.item_watch_img, false);
				// 标题
				helper.setText(R.id.item_watch_title, item.title)
						//时间
						.setText(R.id.item_watch_time, DateUtils.formatTime(item.createTime, DateUtils.TIME_FORMAT_9));
				// 图片
				ImageView iv = helper.getView(R.id.item_watch_webimg);
				if(!StringUtils.isBlank(item.imgUrl)){
					iv.setVisibility(View.VISIBLE);
					KNetManager.getInstance().loadImg(helper.getConvertView().getContext(), item.imgUrl, iv);
				} else {
					iv.setVisibility(View.GONE);
				}
				break;
			case 2:
				// 文字

				break;
			case 3:
				// 图片
				helper.setVisible(R.id.item_watch_webimg, false);
				helper.setVisible(R.id.item_watch_title, false);
				helper.setVisible(R.id.item_watch_img, true);
				// 设置图片
				ImageView ivImg = helper.getView(R.id.item_watch_img);
				KNetManager.getInstance().loadImg(helper.getConvertView().getContext(), item.imgUrl, ivImg);
				// 设置时间
				helper.setText(R.id.item_watch_time, DateUtils.formatTime(item.createTime, DateUtils.TIME_FORMAT_9));
				break;
			default:
				break;
		}
	}
}
