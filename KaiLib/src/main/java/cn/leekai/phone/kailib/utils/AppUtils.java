package cn.leekai.phone.kailib.utils;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.UUID;

import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.utils.cache.SharedCacheUtils;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * app操作工具类
 *
 * @author likai
 *
 * Created by likai
 * 2018/1/18
 */

public class AppUtils {
	private AppUtils(){}

	/**
	 * 获取版本号
	 */
	public static int getVersionCode() {
		// 获取packagemanager的实例
		PackageManager packageManager = KaiCore.getContext().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(KaiCore.getContext().getPackageName(), 0);
			return packInfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			LogUtils.e(e);
		}
		return -1;
	}

	/**
	 * 获取版本名
	 */
	public static String getVersionName() {
		// 获取packagemanager的实例
		PackageManager packageManager = KaiCore.getContext().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(KaiCore.getContext().getPackageName(), 0);
			return packInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			LogUtils.e(e);
		}
		return "";
	}

	/**
	 * 获取系统总内存
	 *
	 * @return 总内存大单位为B。
	 */
	public static long getTotalMemorySize() {
		String dir = "/proc/meminfo";
		try {
			FileReader fr = new FileReader(dir);
			BufferedReader br = new BufferedReader(fr, 2048);
			String memoryLine = br.readLine();
			String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
			br.close();
			return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024L;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * UUID，该方法无需访问设备的资源，也跟设备类型无关。
	 * <p>
	 * 这种方式是通过在程序安装后第一次运行后生成一个ID实现的，但该方式跟设备唯一标识不一样，它会因为不同的应用程序而产生不同的ID，而不是设备唯一ID
	 * 。因此经常用来标识在某个应用中的唯一ID（即Installtion ID），或者跟踪应用的安装数量。很幸运的，Google Developer
	 * Blog提供了这样的一个框架：
	 */
	public synchronized static String installation() {
		String sID;
		File installation = new File(KaiCore.getContext().getFilesDir(), "INSTALLATION");
		try {
			if (!installation.exists()) {
				writeInstallationFile(installation);
			}
			sID = readInstallationFile(installation);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return sID;
	}

	private static String readInstallationFile(File installation) throws IOException {
		RandomAccessFile f = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	private static void writeInstallationFile(File installation) throws IOException {
		FileOutputStream out = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}



	/**
	 * 显示桌面图标角标数字，每调用一次，增加1
	 */
	public static void showShortcutBadger(){
		int badgeCount = SharedCacheUtils.get("app_launcher_number", 0);
		badgeCount += 1;
		SharedCacheUtils.save("app_launcher_number", badgeCount);
		ShortcutBadger.applyCount(KaiCore.getContext(), badgeCount);
	}

	/**
	 * 清除桌面图标角标数字
	 */
	public static void clearShortcutBadger(){
		SharedCacheUtils.save("app_launcher_number", 0);
		ShortcutBadger.applyCount(KaiCore.getContext(), 0);

		//清除所有推送内容
		NotificationManager notiManager = (NotificationManager) KaiCore.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		if(notiManager != null){
			notiManager.cancelAll();
		}
	}


	/**
	 * 获取APP主进程的进程ID
	 */
	public static int getAppUid(Context context) {
		// 当前进程id
		int pid = android.os.Process.myPid();

		// 遍历当前所有进程，从中过滤出主进程id
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if(am != null){
			List<ActivityManager.RunningAppProcessInfo> apps = am.getRunningAppProcesses();

			for (ActivityManager.RunningAppProcessInfo info : apps) {
				if (info.processName.equals(context.getPackageName()) && info.pid == pid) {
					return info.uid;
				}
			}
		}

		return -1;
	}
}
