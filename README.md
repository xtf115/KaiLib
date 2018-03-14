## KaiLib

### KaiLib是我个人在闲暇之余搭建的一套可用于快速android项目开发的集成开发环境的library module，只要在您自己的android项目根目录的settings.gradle中引入该model即可，项目还在不断的优化中，功能也在不断的完善，如有任何建议和意见可以发送邮件至：xtf115@163.com

KaiLib已经集成了`数据库管理`、`事件管理`、`定位管理`、`网络请求`、`推送`、`路由管理`、`Utils工具库`；只要项目引入该library后，即可进行快速的项目开发；

####数据库管理
...
####事件管理
...
####定位管理
...
####网络请求
...
####推送
...
####路由管理
...
####Utils工具库：
```
AppUtis
ChannelUtils
CommonUtils
DataUtils
DateUtils
DialogUtis
FileUtils
GsonUtils
LogUtils
NetUtils
NotifyUtils
PermissionUtils
PhoneUtils
ShareUtils
StringUtils
ThreadPoolUtils
ToastUtils
```
### 使用方法
#### settings.gradle
```
...
include ':KaiLib'
```
#### 主项目的application中进行KaiLib的配置：
```
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		if(AppUtils.getAppUid(this) > 0){
			// 初始化kaiLib
			KaiLibConfig kConfig = new KaiLibConfig();
			// 设置日志开启状态
			kConfig.logEnable = BuildConfig.LOG_DEBUG;
			// 设置推送处理类
			kConfig.pushMessageReceiver = new MyPushMessageReceiver();
			// 启动kaiLib
			KaiCore.start(this, kConfig);

			// 初始化网路配置
			initNetManager();

			// 启动定位
			KLocationManager.getInstance().start();

			// 初始化推送
			KPushManager.getInstance().start();

			// 初始化分享
			MobSDK.init(this);

			// 测试入口
			if(kConfig.logEnable){
				TestUtil.main();
			}
		}
	}

	/**
	 * 配置网络请求
	 */
	private void initNetManager(){
		// 设置全局请求参数
		HashMap<String, String> params = new HashMap<>();
		// 设备类型
		params.put(ParamConst.DEVICE_TYPE, "1");
		// 版本号
		params.put(ParamConst.VER, AppUtils.getVersionName());
		// 渠道号
		params.put(ParamConst.CHANNEL, ChannelUtils.getChannel(this));
		// 设备ID
		params.put(ParamConst.DEVICE_ID, AppUtils.installation());
		// 设备系统版本
		params.put(ParamConst.OS_VER, Build.VERSION.RELEASE);
		// 推送id
		params.put(ParamConst.PUSH_CHANNELID, KaiCore.getConfig().getBaiduChannelId());
		// 时区
		params.put(ParamConst.GMT, DateUtils.getTimeZoneName());
		// 手机内存
		params.put(ParamConst.MEM, DataUtils.formatFileSize(AppUtils.getTotalMemorySize(), true));
		// 配置网络请求公共参数
		KNetManager.getInstance().setCommonParams(params);
	}
}
```