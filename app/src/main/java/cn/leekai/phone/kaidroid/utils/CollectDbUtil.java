package cn.leekai.phone.kaidroid.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.leekai.phone.kaidroid.datas.entity.databases.CollectInfoEntity;
import cn.leekai.phone.kailib.managers.KDbManager;
import cn.leekai.phone.kailib.managers.databases.CollectDbHelper;
import cn.leekai.phone.kailib.utils.LogUtils;
import cn.leekai.phone.kailib.utils.StringUtils;

/**
 * description：数据库操作集合
 * created on：2018/1/25 16:32
 * @author likai
 */
public class CollectDbUtil {

	/**
	 * 添加一条收集信息
	 * @param collectInfo 待添加的收集信息
	 */
	public static boolean addOneCollectInfo(CollectInfoEntity collectInfo) {
		SQLiteDatabase db = KDbManager.getInstance().getCollectDb();
		// 如果是链接、检查该链接是否已经收藏过
		if(collectInfo.type == 1){
			// 链接类型
			if(StringUtils.isBlank(collectInfo.url) || StringUtils.isBlank(collectInfo.title)){
				return false;
			}
			/*
			 *参数table:表名称
			 *参数columns:列名称数组
			 *参数selection:条件字句，相当于where
			 *参数selectionArgs:条件字句，参数数组
			 *参数groupBy:分组列
			 *参数having:分组条件
			 *参数orderBy:排序列
			 *参数limit:分页查询限制
			 *参数Cursor:返回值，相当于结果集ResultSet
			 */
			Cursor cursor = db.query(CollectDbHelper.COLLECT_TABLE_NAME, new String[]{"id"}, "url=?", new String[]{collectInfo.url}, null, null, null);
			int count = cursor.getCount();
			cursor.close();
			if(count > 0) {
				return false;
			}
		} else if (collectInfo.type == 2){
			// 内容类型
			if(StringUtils.isBlank(collectInfo.content)){
				return false;
			}
		} else if (collectInfo.type == 3) {
			// 图片类型
			if(StringUtils.isBlank(collectInfo.imgUrl)){
				return false;
			}
		}
		if(collectInfo.createTime == 0) {
			collectInfo.createTime = System.currentTimeMillis();
		}

		// 创建一条新纪录数据
		ContentValues contentValues = new ContentValues();

		// 通过反射机制进行取值操作
		Field[] allFields = collectInfo.getClass().getDeclaredFields();
		for (Field mField : allFields){
			try {
				// 字段名（数据库表）
				String name = mField.getName();
				// 获取拼接的get方法的名字（getXxx）
				String tmpName = name.substring(0, 1).toUpperCase() + name.substring(1);
				// 获取类方法
				Method method = collectInfo.getClass().getMethod("get" + tmpName);
				// 通过类方法取值
				Object value = method.invoke(collectInfo);
				// 添加一项入库内容
				if(!"id".equals(name)){
					contentValuesPut(contentValues, name, value);
				}
			} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				LogUtils.e(e);
			}
		}
		/*
		 * 参数1  表名称，
		 * 参数2  空列的默认值
		 * 参数3  ContentValues类型的一个封装了列名称和列值的Map；
		 */
		long rowId = db.insert(CollectDbHelper.COLLECT_TABLE_NAME, null, contentValues);

		return rowId > 0;
	}

	/**
	 * 获取收集列表数据
	 * @param page 页码
	 * @param pageSize 每页加载数量
	 * @return 数据集合
	 */
	public static ArrayList<CollectInfoEntity> getCollectInfoList(int page, int pageSize) {
		SQLiteDatabase db = KDbManager.getInstance().getCollectDb();
		page = page <= 0 ? 0 : page;
		int start = (page - 1) * pageSize;
		String sql = "select * from " + CollectDbHelper.COLLECT_TABLE_NAME + " order by createTime desc limit " + start + "," + pageSize;
		Cursor cursor = db.rawQuery(sql, null);
		ArrayList<CollectInfoEntity> allInfos = new ArrayList<>();
		while (cursor.moveToNext()) {
			CollectInfoEntity entity = new CollectInfoEntity();
			entity.id = cursor.getInt(cursor.getColumnIndex("id"));
			entity.url = cursor.getString(cursor.getColumnIndex("url"));
			entity.title = cursor.getString(cursor.getColumnIndex("title"));
			entity.content = cursor.getString(cursor.getColumnIndex("content"));
			entity.createTime = cursor.getLong(cursor.getColumnIndex("createTime"));
			entity.type = cursor.getInt(cursor.getColumnIndex("type"));
			entity.reTitle = cursor.getString(cursor.getColumnIndex("reTitle"));
			entity.imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			allInfos.add(entity);
		}
		cursor.close();
		return allInfos;
	}

	/**
	 * 获取数据库单条数据
	 */
	public static CollectInfoEntity getOneCollectItem(int rowId){
		CollectInfoEntity entity = new CollectInfoEntity();

		if(rowId > 0){
			String sql = "select * from " + CollectDbHelper.COLLECT_TABLE_NAME + " where id = ?";

			Map<String, Object> item = queryItemMap(sql, new String[]{rowId+""});

			for (Object obj : item.entrySet()) {
				Map.Entry entry = (Map.Entry) obj;
				String key = entry.getKey().toString();
				Object val = entry.getValue();
				try {
					// 获取拼接的set方法的名字（setXxx）
					String tmpName = key.substring(0, 1).toUpperCase() + key.substring(1);
					// 获取类方法
					Method method = entity.getClass().getMethod("set" + tmpName);
					// 通过类方法取值
					method.invoke(entity, val);
				} catch (Exception e) {
					LogUtils.e(e);
				}
			}
		}

		return entity;
	}

	/**
	 * 统一对ContentValues处理
	 * @param contentValues 待处理的数据集合
	 * @param key 字段名
	 * @param value 字段值
	 */
	private static void contentValuesPut(ContentValues contentValues, String key, Object value) {
		if (value == null) {
			contentValues.put(key, "");
		} else {
			String className = value.getClass().getName();
			if ("java.lang.String".equals(className)) {
				contentValues.put(key, value.toString());
			} else if ("java.lang.Integer".equals(className)) {
				contentValues.put(key, Integer.valueOf(value.toString()));
			} else if ("java.lang.Float".equals(className)) {
				contentValues.put(key, Float.valueOf(value.toString()));
			} else if ("java.lang.Double".equals(className)) {
				contentValues.put(key, Double.valueOf(value.toString()));
			} else if ("java.lang.Boolean".equals(className)) {
				contentValues.put(key, Boolean.valueOf(value.toString()));
			} else if ("java.lang.Long".equals(className)) {
				contentValues.put(key, Long.valueOf(value.toString()));
			} else if ("java.lang.Short".equals(className)) {
				contentValues.put(key, Short.valueOf(value.toString()));
			}
		}
	}

	/**
	 * 根据数组的列和值进行insert
	 * @param tableName 表名
	 * @param columns 字段名集合
	 * @param values 字段值集合
	 */
	private static boolean insert(String tableName, String[] columns, Object[] values) {
		ContentValues contentValues = new ContentValues();
		for (int rows = 0; rows < columns.length; ++rows) {
			contentValuesPut(contentValues, columns[rows], values[rows]);
		}
		long rowId = KDbManager.getInstance().getCollectDb().insert(tableName, null, contentValues);
		return rowId != -1;
	}

	/**
	 * 根据map来进行insert
	 * @param tableName 数据表名
	 * @param columnValues 待如表的Map数据集合
	 */
	private static boolean insert(String tableName, Map<String, Object> columnValues) {
		ContentValues contentValues = new ContentValues();
		for (String key : columnValues.keySet()) {
			contentValuesPut(contentValues, key, columnValues.get(key));
		}

		long rowId = KDbManager.getInstance().getCollectDb().insert(tableName, null, contentValues);
		return rowId != -1;
	}

	/**
	 * 统一对数组where条件进行拼接
	 * @param whereColumns where条件“表字段”数组集合
	 */
	private static String initWhereSqlFromArray(String[] whereColumns) {
		StringBuilder whereStr = new StringBuilder();
		for (int i = 0; i < whereColumns.length; ++i) {
			whereStr.append(whereColumns[i]).append(" = ? ");
			if (i < whereColumns.length - 1) {
				whereStr.append(" and ");
			}
		}
		return whereStr.toString();
	}

	/**
	 * 统一对map的where条件和值进行处理
	 * @param whereParams where条件的map<字段名，字段值>集合
	 */
	private static Map<String, Object> initWhereSqlFromMap(Map<String, String> whereParams) {
		Set set = whereParams.keySet();
		String[] temp = new String[whereParams.size()];
		int i = 0;
		Iterator iterator = set.iterator();
		StringBuffer whereStr = new StringBuffer();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			whereStr.append(key).append(" = ? ");
			temp[i] = whereParams.get(key);
			if (i < set.size() - 1) {
				whereStr.append(" and ");
			}
			i++;
		}
		HashMap<String, Object> result = new HashMap<>();
		result.put("whereSql", whereStr);
		result.put("whereSqlParam", temp);
		return result;
	}

	/**
	 * 根据数组条件来update
	 * @param tableName 表名
	 * @param columns 字段数组
	 * @param values 字段值数组
	 * @param whereColumns where数组
	 * @param whereArgs where值数组
	 */
	private static boolean update(String tableName, String[] columns, Object[] values, String[] whereColumns, String[] whereArgs) {
		ContentValues contentValues = new ContentValues();
		for (int i = 0; i < columns.length; ++i) {
			contentValuesPut(contentValues, columns[i], values[i]);
		}
		String whereClause = initWhereSqlFromArray(whereColumns);
		int rowNumber = KDbManager.getInstance().getCollectDb().update(tableName, contentValues, whereClause, whereArgs);
		return rowNumber > 0;
	}

	/**
	 * 根据map值来进行update
	 * @param tableName 数据表名
	 * @param columnValues 更新字段Map集合
	 * @param whereParam whereMap集合
	 */
	private static boolean update(String tableName, Map<String, Object> columnValues, Map<String, String> whereParam) {
		ContentValues contentValues = new ContentValues();
		Iterator iterator = columnValues.keySet().iterator();

		String columns;
		while (iterator.hasNext()) {
			columns = (String) iterator.next();
			contentValuesPut(contentValues, columns, columnValues.get(columns));
		}

		Map map = initWhereSqlFromMap(whereParam);
		int rowNumber = KDbManager.getInstance().getCollectDb().update(tableName, contentValues, (String) map.get("whereSql"), (String[]) map.get("whereSqlParam"));
		return rowNumber > 0;
	}

	/**
	 * 根据数组条件进行delete
	 * @param tableName 表名
	 * @param whereColumns where字段集合
	 * @param whereParam where值集合
	 */
	private static boolean delete(String tableName, String[] whereColumns, String[] whereParam) {
		String whereStr = initWhereSqlFromArray(whereColumns);
		int rowNumber = KDbManager.getInstance().getCollectDb().delete(tableName, whereStr, whereParam);
		return rowNumber > 0;
	}


	/**
	 * 根据map来进行delete
	 * @param tableName 表名
	 * @param whereParams whereMap<字段,值>集合
	 */
	private static boolean delete(String tableName, Map<String, String> whereParams) {
		Map map = initWhereSqlFromMap(whereParams);
		int rowNumber = KDbManager.getInstance().getCollectDb().delete(tableName, map.get("whereSql").toString(), (String[]) map.get("whereSqlParam"));
		return rowNumber > 0;
	}


	/**
	 * 查询返回List
	 * @param sql sql语句
	 * @param params 参数值数组
	 */
	private static ArrayList<HashMap<String, Object>> queryListMap(String sql, String[] params) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		Cursor cursor = KDbManager.getInstance().getCollectDb().rawQuery(sql, params);
		int columnCount = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			HashMap<String, Object> item = new HashMap<>();
			for (int i = 0; i < columnCount; ++i) {
				int type = cursor.getType(i);
				switch (type) {
					case 0:
						item.put(cursor.getColumnName(i), null);
						break;
					case 1:
						item.put(cursor.getColumnName(i), cursor.getInt(i));
						break;
					case 2:
						item.put(cursor.getColumnName(i), cursor.getFloat(i));
						break;
					case 3:
						item.put(cursor.getColumnName(i), cursor.getString(i));
						break;
					default:
						break;
				}
			}
			list.add(item);
		}
		cursor.close();
		return list;
	}

	/**
	 * 查询单条数据返回map
	 * @param sql sql语句
	 * @param params 参数值数组
	 */
	private static Map queryItemMap(String sql, String[] params) {
		Cursor cursor = KDbManager.getInstance().getCollectDb().rawQuery(sql, params);
		HashMap<String, Object> map = new HashMap<>();
		if (cursor.moveToNext()) {
			for (int i = 0; i < cursor.getColumnCount(); ++i) {
				int type = cursor.getType(i);
				switch (type) {
					case 0:
						map.put(cursor.getColumnName(i), null);
						break;
					case 1:
						map.put(cursor.getColumnName(i), cursor.getInt(i));
						break;
					case 2:
						map.put(cursor.getColumnName(i), cursor.getFloat(i));
						break;
					case 3:
						map.put(cursor.getColumnName(i), cursor.getString(i));
						break;
					default:
						break;
				}
			}
		}
		cursor.close();
		return map;
	}
}
