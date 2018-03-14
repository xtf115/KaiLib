package cn.leekai.phone.kaidroid;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.hikyson.android.godeye.toolbox.crash.CrashFileProvider;
import cn.hikyson.android.godeye.toolbox.rxpermission.RxPermissions;
import cn.hikyson.godeye.core.GodEye;
import cn.hikyson.godeye.core.helper.PermissionRequest;
import cn.hikyson.godeye.core.internal.modules.battery.Battery;
import cn.hikyson.godeye.core.internal.modules.battery.BatteryContextImpl;
import cn.hikyson.godeye.core.internal.modules.cpu.Cpu;
import cn.hikyson.godeye.core.internal.modules.cpu.CpuContextImpl;
import cn.hikyson.godeye.core.internal.modules.crash.Crash;
import cn.hikyson.godeye.core.internal.modules.fps.Fps;
import cn.hikyson.godeye.core.internal.modules.fps.FpsContextImpl;
import cn.hikyson.godeye.core.internal.modules.leakdetector.LeakContextImpl2;
import cn.hikyson.godeye.core.internal.modules.leakdetector.LeakDetector;
import cn.hikyson.godeye.core.internal.modules.memory.Heap;
import cn.hikyson.godeye.core.internal.modules.memory.Pss;
import cn.hikyson.godeye.core.internal.modules.memory.PssContextImpl;
import cn.hikyson.godeye.core.internal.modules.memory.Ram;
import cn.hikyson.godeye.core.internal.modules.memory.RamContextImpl;
import cn.hikyson.godeye.core.internal.modules.pageload.Pageload;
import cn.hikyson.godeye.core.internal.modules.pageload.PageloadContextImpl;
import cn.hikyson.godeye.core.internal.modules.sm.Sm;
import cn.hikyson.godeye.core.internal.modules.sm.SmContextImpl;
import cn.hikyson.godeye.core.internal.modules.thread.ThreadContextImpl;
import cn.hikyson.godeye.core.internal.modules.thread.ThreadDump;
import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadLock;
import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadLockContextImpl;
import cn.hikyson.godeye.core.internal.modules.thread.deadlock.DeadlockDefaultThreadFilter;
import cn.hikyson.godeye.core.internal.modules.traffic.Traffic;
import cn.hikyson.godeye.core.internal.modules.traffic.TrafficContextImpl;
import cn.hikyson.godeye.monitor.GodEyeMonitor;
import cn.leekai.phone.kaidroid.receivers.MyPushMessageReceiver;
import cn.leekai.phone.kaidroid.utils.TestUtil;
import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.KaiLibConfig;
import cn.leekai.phone.kailib.datas.consts.ParamConst;
import cn.leekai.phone.kailib.managers.KLocationManager;
import cn.leekai.phone.kailib.managers.KNetManager;
import cn.leekai.phone.kailib.managers.KPushManager;
import cn.leekai.phone.kailib.utils.AppUtils;
import cn.leekai.phone.kailib.utils.ChannelUtils;
import cn.leekai.phone.kailib.utils.DataUtils;
import cn.leekai.phone.kailib.utils.DateUtils;

/**
 * description：自定义的application入口
 * created on：2018/1/18 18:23
 * @author likai
 */
public class KaiApplication extends Application {

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

			initGodEye();

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

	private void initGodEye(){
		GodEye.instance().install(Cpu.class, new CpuContextImpl())
				.install(Battery.class, new BatteryContextImpl(this))
				.install(Fps.class, new FpsContextImpl(this))
				.install(Heap.class, Long.valueOf(2000))
				.install(Pss.class, new PssContextImpl(this))
				.install(Ram.class, new RamContextImpl(this))
				.install(Sm.class, new SmContextImpl(this, 1000, 300, 800))
				.install(Traffic.class, new TrafficContextImpl())
				.install(Crash.class, new CrashFileProvider(this))
				.install(ThreadDump.class, new ThreadContextImpl())
				.install(DeadLock.class, new DeadLockContextImpl(GodEye.instance().getModule(ThreadDump.class).subject(), new DeadlockDefaultThreadFilter()))
				.install(Pageload.class, new PageloadContextImpl(this))
				.install(LeakDetector.class, new LeakContextImpl2(this, new PermissionRequest() {
					@Override
					public io.reactivex.Observable<Boolean> dispatchRequest(Activity activity, String... permissions) {
						return new RxPermissions(activity).request(permissions);
					}
				}));

		GodEyeMonitor.work(this);
	}
}
