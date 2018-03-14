package cn.leekai.phone.kailib.datas.entity;

/**
 * description：第三方分享内容
 * created on：2018/2/1 11:45
 * @author likai
 */

public class ShareEntity {
	/**
	 * title标题，微信、QQ和QQ空间等平台使用
	 */
	public String title = "";
	/**
	 * text是分享文本，所有平台都需要这个字段
	 */
	public String text = "";
	/**
	 * titleUrl QQ和QQ空间跳转链接
	 */
	public String titleUrl = "";
	/**
	 * imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
	 */
	public String imagePath = "";
	/**
	 * iamgeUrl是图片地址
	 */
	public String imageUrl = "";
	/**
	 * url在微信、微博，Facebook等平台中使用
	 */
	public String url = "";
	/**
	 * comment是我对这条分享的评论，仅在人人网使用
	 */
	public String comment = "";

}
