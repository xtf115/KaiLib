package cn.leekai.phone.kailib.managers.databases;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：用户库
 * created on：2018/1/31 15:28
 * @author likai
 */

public class UserDbHelper extends SQLiteOpenHelper {
	private final static String TAG = "K_DATABASE";
	/**
	 * 数据库名称
	 */
	private static final String DB_NAME = "kai_user.db";
	/**
	 * 数据库版本
	 */
	private static final int DB_VERSION = 1;
	/**
	 * 用户表
	 */
	public static final String USER_TABLE_NAME = "user";
	/**
	 * 创建user表sql语句
	 */
	private final static String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(_uid INTEGER PRIMARY KEY, uid INT, name TEXT, sex INT)";

	/**
	 * 初始化数据库（创建）
	 *
	 */
	public UserDbHelper() {
		super(KaiCore.getContext(), DB_NAME, null, DB_VERSION);
	}

	/**
	 * 该方法是当没有数据库存在才会执行
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//没有数据库打印日记
		LogUtils.d(TAG,"没有数据库,创建数据库");
		// 创建数据表
		db.execSQL(CREATE_USER_TABLE);
	}

	/**
	 * 更新数据库(该方法是数据库存更新才会执行)
	 * @param db 数据库
	 * @param oldVersion 旧版本号
	 * @param newVersion 新版本号
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogUtils.d(TAG, "数据库更新了！");
	}
}
