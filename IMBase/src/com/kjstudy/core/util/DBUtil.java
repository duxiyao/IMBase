package com.kjstudy.core.util;

import java.util.List;



import org.myframe.MDB;

import com.imbase.MyApplication;
import com.kjstudy.core.io.FileAccessor;

public class DBUtil {

	public static MDB getDB() {
		return MDB.create(MyApplication.getInstance().getApplicationContext(),
				FileAccessor.DBPATH, FileAccessor.DBNAME, true);
	}

	public static void save(Object obj) {
		MDB.create(MyApplication.getInstance().getApplicationContext(),
				FileAccessor.DBPATH, FileAccessor.DBNAME, true).save(obj);
	}

	public static boolean saveBindId(Object obj) {
		return MDB.create(MyApplication.getInstance().getApplicationContext(),
				FileAccessor.DBPATH, FileAccessor.DBNAME, true).saveBindId(obj);
	}

	public static void update(Object obj) {
		MDB.create(MyApplication.getInstance().getApplicationContext(),
				FileAccessor.DBPATH, FileAccessor.DBNAME, true).update(obj);
	}

	public static void update(Object obj, String sqlWhere) {
		MDB.create(MyApplication.getInstance().getApplicationContext(),
				FileAccessor.DBPATH, FileAccessor.DBNAME, true).update(obj,
				sqlWhere);
	}

	public static <T> T findOne(Class<T> clazz, String sqlWhere) {
		try {
			return MDB
					.create(MyApplication.getInstance().getApplicationContext(),
							FileAccessor.DBPATH, FileAccessor.DBNAME, true)
					.findAllByWhere(clazz, sqlWhere).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> List<T> findAllByWhere(Class<T> clazz, String sqlWhere) {
		return MDB.create(MyApplication.getInstance().getApplicationContext(),
				FileAccessor.DBPATH, FileAccessor.DBNAME, true).findAllByWhere(
				clazz, sqlWhere);
	}
}
