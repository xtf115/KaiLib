package cn.leekai.phone.kailib.datas.entity.events;

/**
 * description：eventbus事件传递entity
 * created on：2018/1/18 17:56
 * @author likai
 */
public class EventEntity<T> {
	/**
	 * 事件标识id
	 */
	private int id;

	/**
	 * 事件传递内容
	 */
	private T data;

	public EventEntity(int id) {
		this.id = id;
	}

	public EventEntity(int id, T data) {
		this.id = id;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public T getData() {
		return data;
	}
}
