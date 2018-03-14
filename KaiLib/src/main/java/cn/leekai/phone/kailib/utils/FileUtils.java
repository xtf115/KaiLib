package cn.leekai.phone.kailib.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.leekai.phone.kailib.KaiCore;

/**
 * 文件操作类
 * @author likai
 */

public class FileUtils {
	private FileUtils() {
	}

	/**
	 * 创建sd卡根目录
	 */
	public static void creatRootFile() {
		File destDir = new File(getSDPath() + "/.sjjy");
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}

	/**
	 * 获取sd卡根目录
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		// 判断sd卡是否存在
		if (sdCardExist) {
			// 获取跟目录
			sdDir = Environment.getExternalStorageDirectory();
		}
		return sdDir.toString();
	}

	/**
	 * 保存图片
	 */
	public static void saveFile(Bitmap bm, String fileName) throws IOException {
		// getSDPath()
		String path = getSDPath() + "/.sjjy/" + fileName;
		File dirFile = new File(path.substring(0, path.lastIndexOf("/")));
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	/**
	 * 往SD卡写入文件的方法
	 */
	public static boolean savaFileToSD(String fileName, byte[] bytes) throws Exception {
		//如果手机已插入sd卡,且app具有读写sd卡的权限
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/xfbroker";
			File dir1 = new File(filePath);
			if (!dir1.exists()) {
				dir1.mkdirs();
			}
			fileName = filePath + "/" + fileName;
			//这里就不要用openFileOutput了,那个是往手机内存中写数据的
			FileOutputStream output = new FileOutputStream(fileName);
			//将bytes写入到输出流中
			output.write(bytes);
			//关闭输出流
			output.close();
			// 最后通知图库更新
			KaiCore.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(fileName))));
			return true;
		} else {
			return false;
		}
	}
}
