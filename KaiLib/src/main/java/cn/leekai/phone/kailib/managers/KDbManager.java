package cn.leekai.phone.kailib.managers;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;

import cn.leekai.phone.kailib.managers.databases.CollectDbHelper;
import cn.leekai.phone.kailib.managers.databases.UserDbHelper;

/**
 * description：数据库管理
 * created on：2018/1/18 17:53
 * @author likai
 */

public class KDbManager {
	/**
	 * 用户库
	 */
	private SQLiteDatabase mUserDb;
	/**
	 * 收集库
	 */
	private SQLiteDatabase mCollectDb;

	private KDbManager(){}

	private static class SingleHolder{
		private final static KDbManager INSTANCE = new KDbManager();
	}

	/**
	 * 获取单例
	 */
	public static KDbManager getInstance(){
		return SingleHolder.INSTANCE;
	}

	/**
	 * 获取用户数据库
	 * @return 要获取的数据库
	 */
	public SQLiteDatabase getUserDb(){
		if(mUserDb == null){
			mUserDb = new UserDbHelper().getWritableDatabase();
		}
		return mUserDb;
	}

	/**
	 * 获取收集数据库
	 * @return 要获取的数据库
	 */
	public SQLiteDatabase getCollectDb(){
		if(mCollectDb == null) {
			mCollectDb = new CollectDbHelper().getWritableDatabase();
		}
		return mCollectDb;
	}

	public void createTable(Class entityClass){

		// 测试获取某个类的所有字段
		Field[] allFields = entityClass.getDeclaredFields();

		if(allFields.length > 0){
			for (Field mField : allFields){
				String fieldName = mField.getName();
				Object filedType = mField.getType();

			}
		}



	}
}
