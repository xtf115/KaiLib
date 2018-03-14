package cn.leekai.phone.kaidroid.datas.entity.databases;

import java.io.Serializable;

/**
 * description：看一看封装的信息
 * created on：2018/1/26 09:56
 * @author likai
 */

public class CollectInfoEntity implements Serializable {
	/**
	 * rowId
	 */
	public int id;
	/**
	 * 类型(1：链接，2：文字，3：图片)
	 */
	public int type = 2;
	/**
	 * 标题
	 */
	public String title = "";
	/**
	 * 自定义标题
	 */
	public String reTitle = "";
	/**
	 * 链接
	 */
	public String url = "";
	/**
	 * 图片链接
	 */
	public String imgUrl = "";
	/**
	 * 内容
	 */
	public String content = "";
	/**
	 * 时间戳
	 */
	public long createTime;

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setReTitle(String reTitle) {
		this.reTitle = reTitle;
	}

	public String getReTitle(){
		return reTitle;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getCreateTime(){
		return createTime;
	}
}
