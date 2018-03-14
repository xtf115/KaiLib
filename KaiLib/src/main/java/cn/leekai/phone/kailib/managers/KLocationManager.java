package cn.leekai.phone.kailib.managers;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.HashMap;

import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.datas.consts.ParamConst;
import cn.leekai.phone.kailib.utils.LogUtils;
import cn.leekai.phone.kailib.utils.cache.SharedCacheUtils;

/**
 * description：位置管理
 *
 * created on：2018/1/18 17:51
 * @author likai
 */

public class KLocationManager {
	private final static String TAG = "K_LOCATION";
	private volatile static KLocationManager mInstance = null;

	/**
	 * 百度地图定位相关
	 */
	private LocationClient mLocationClient = null;
	/**
	 * 定位监听
	 */
	private BDLocationListener myListener = new MyLocationListener();

	private KLocationManager() {}

	public synchronized static KLocationManager getInstance() {
		if (mInstance == null) {
			synchronized (KLocationManager.class) {
				if (mInstance == null) {
					mInstance = new KLocationManager();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 开启定位
	 */
	public void start() {
		LogUtils.d(TAG, "启动定位动能------");
		// ②百度定位的相关组件的初始化
		mLocationClient = new LocationClient(KaiCore.getContext());
		// 注册监听函数
		mLocationClient.registerLocationListener(myListener);
		// 设置定位参数
		LocationClientOption option = new LocationClientOption();
		// 设置定位模式:LocationMode.Hight_Accuracy是高精度定位模式，gps，网络混合定位，默认返回高精度
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		// 返回的定位结果是百度经纬度,默认值gcj02
		option.setCoorType("bd09ll");
		// 设置发起定位请求的间隔时间为5000ms
		option.setScanSpan(5000);
		// 返回的定位结果包含地址信息
		option.setIsNeedAddress(true);
		// 返回的定位结果包含手机机头的方向
		option.setNeedDeviceDirect(true);
		//可选，默认false,设置是否使用gps
		option.setOpenGps(true);
		mLocationClient.setLocOption(option);
		//开始执行定位
		mLocationClient.start();
	}

	/**
	 * 定位监听器，用于接收定位返回的参数
	 *
	 * 错误码对照表:
	 * http://lbsyun.baidu.com/index.php?title=androidsdk/guide/addition-func/errorcode
	 * http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/addition-func/error-code
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			LogUtils.d(TAG, "获取定位结果------");
			if (location == null) {
				LogUtils.d(TAG, "获取定位结果------异常");
				return;
			}

			// 获取error code：
			int locType = location.getLocType();
			String lng = location.getLongitude() + "";
			String lat = location.getLatitude() + "";

			boolean isSuccess = (locType == BDLocation.TypeGpsLocation || locType == BDLocation.TypeNetWorkLocation) && !"4.9E-324".equals(lng) && !"4.9E-324".equals(lat);
			if (isSuccess) {
				// 定位成功
				LogUtils.d(TAG, "获取定位结果------定位成功-lng:" + lng + "---lat:" + lat);

				// 保存定位信息
				HashMap<String, String> locParame = new HashMap<>(2);
				locParame.put(ParamConst.LONGITUDE, lng);
				locParame.put(ParamConst.LATITUDE, lat);
				SharedCacheUtils.saveStringMap(locParame);

				// 关闭定位
				if (mLocationClient != null) {
					LogUtils.d(TAG, "关闭定位------");
					mLocationClient.stop();
				}
			} else {
				// 定位失败
				LogUtils.d(TAG, "获取定位结果------定位失败(请检查是否正确配置apiKey):" + locType + "--lng:"+lng+"|lat:"+lat);
			}
		}

	}
}
