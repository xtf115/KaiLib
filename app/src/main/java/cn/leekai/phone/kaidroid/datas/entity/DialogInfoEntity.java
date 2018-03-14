package cn.leekai.phone.kaidroid.datas.entity;

import cn.leekai.phone.kailib.datas.entity.ShareEntity;
import cn.leekai.phone.kailib.utils.StringUtils;

/**
 * description：弹窗中需要使用的信息集合
 * created on：2018/1/31 17:16
 * @author likai
 */

public class DialogInfoEntity {
	/**
	 * 链接地址
	 */
	private String url = "";
	/**
	 * 标题
	 */
	private  String title = "";
	/**
	 * 分享内容
	 */
	private ShareEntity shareInfo = new ShareEntity();
	/**
	 * 图片链接地址
	 */
	private String imgUrl = "";

	public void setTitle(String title){
		this.title = title;
		this.shareInfo.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setUrl(String url){
		this.url = url;
		this.shareInfo.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setImgUrl(String imgUrl){
		if(!StringUtils.isBlank(imgUrl)){
			this.imgUrl = imgUrl;
			this.shareInfo.imageUrl = imgUrl;
		}
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setShareInfo(ShareEntity entity){
		this.shareInfo = entity;
	}

	public ShareEntity getShareInfo(){
		return shareInfo;
	}
}
