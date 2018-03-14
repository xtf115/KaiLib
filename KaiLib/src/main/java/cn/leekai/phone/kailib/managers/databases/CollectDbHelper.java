package cn.leekai.phone.kailib.managers.databases;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.leekai.phone.kailib.KaiCore;
import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：信息收集库
 * 1、将表名改为临时表（ALTER TABLE Subscription RENAME TO __temp__Subscription;  ）
 * 2、创建新表（ CREATE TABLE Subscription (OrderId VARCHAR(32) PRIMARY KEY ,UserName VARCHAR(32) NOT NULL ,ProductId VARCHAR(16) NOT NULL);）
 * 3、导入数据（INSERT INTO Subscription SELECT OrderId, “”, ProductId FROM __temp__Subscription;  ）【* 注意 双引号”” 是用来补充原来不存在的数据的】
 * 4、删除临时表（DROP TABLE __temp__Subscription; ）
 *
 * created on：2018/1/31 15:34
 * @author likai
 */

public class CollectDbHelper extends SQLiteOpenHelper {
	private final static String TAG = "K_DATABASE";
	/**
	 * 数据库名称
	 */
	private static final String DB_NAME = "kai_collect.db";
	/**
	 * 数据库版本
	 */
	private static final int DB_VERSION = 3;
	/**
	 * collect收集表
	 */
	public static final String COLLECT_TABLE_NAME = "collect";
	/**
	 * 创建collect表sql语句
	 * _id 自增id
	 * url 链接地址
	 * title 链接标题
	 * reTitle 自定义标题
	 * content 文字内容
	 * createTime 创建时间
	 * type 类型
	 */
	private final static String CREATE_COLLECT_TABLE = "CREATE TABLE IF NOT EXISTS " + COLLECT_TABLE_NAME + "(id INTEGER PRIMARY KEY, url TEXT, title TEXT, reTitle TEXT, imgUrl TEXT, content TEXT, createTime INTEGER, type INTEGER)";

	/**
	 * 更新数据库表需要使用以下步骤
	 * 1、修改原始表名字为临时表
	 */
	private final static String CREATE_TEMP_TABLE = "alter table xxx rename to _temp_xxxx";
	/**
	 * 2、创建新版本的表
	 */
	private final static String CREATE_TABLE = "create table xxx(xxx integer primarykey,xxx text);";
	/**
	 * 3、往新表中插入旧数据
	 */
	private final static String INSERT_DATA = "insert into xxx select *,'' from _temp_xxxx";
	/**
	 * 4、删除临时表
	 */
	private final static String DROP_TABLE = "drop table _temp_xxxx";

	/**
	 * 初始化数据库（创建）
	 */
	public CollectDbHelper() {
		super(KaiCore.getContext(), DB_NAME, null, DB_VERSION);
	}

	/**
	 * 该方法是当没有数据库存在才会执行
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//没有数据库打印日记
		LogUtils.d(TAG, "没有数据库,创建数据库");
		// 创建数据表
		db.execSQL(CREATE_COLLECT_TABLE);
	}

	/**
	 * 更新数据库(该方法是数据库存更新才会执行)
	 * @param db 数据库
	 * @param oldVersion 旧版本号
	 * @param newVersion 新版本号
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogUtils.d(TAG, "数据库更新！");
		switch (newVersion){
			case 2:
				//新版本增加字段
				db.execSQL("ALTER TABLE " + COLLECT_TABLE_NAME + " ADD COLUMN reTitle TEXT;");
				break;
			case 3:
				//新版本增加字段
				db.execSQL("ALTER TABLE " + COLLECT_TABLE_NAME + " ADD COLUMN imgUrl TEXT;");
				break;
			case -1:
				// 更新数据表，并导入旧数据至新表
				db.execSQL(CREATE_TEMP_TABLE);
				db.execSQL(CREATE_TABLE);
				db.execSQL(INSERT_DATA);
				db.execSQL(DROP_TABLE);
				break;
			default:
				break;
		}
	}
}
