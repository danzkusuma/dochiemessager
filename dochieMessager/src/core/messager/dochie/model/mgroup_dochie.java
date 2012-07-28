package core.messager.dochie.model;

import java.util.ArrayList;
import java.util.List;

import core.messager.dochie.bean.group_dochie;
import core.messager.dochie.helper.DochieSQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class mgroup_dochie {
	private SQLiteDatabase dbGroup;
	private DochieSQLiteHelper dbHelper;
	private String allcolomns[] = { DochieSQLiteHelper.COLOMN_IDGROUP,
			DochieSQLiteHelper.COLOMN_NAMAGROUP };

	public mgroup_dochie(Context context) {
		dbHelper = new DochieSQLiteHelper(context);
	}

	public void open() throws SQLException {
		dbGroup = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public group_dochie createMessage(String _namaGrb) {
		ContentValues values = new ContentValues();

		values.put(DochieSQLiteHelper.COLOMN_NAMAGROUP, _namaGrb);

		long insertId = dbGroup.insert(DochieSQLiteHelper.TABLE_GROUP, null,
				values);
		Cursor cursor = dbGroup.query(DochieSQLiteHelper.TABLE_GROUP,
				allcolomns, DochieSQLiteHelper.COLOMN_IDGROUP + " = "
						+ insertId, null, null, null, null);
		
		cursor.moveToFirst();
		group_dochie newGroub = cursorToMessage(cursor);
		cursor.close();
		return newGroub;
	}

	public group_dochie updateGroup(String _idGrb,String _namaGrb){
		ContentValues values = new ContentValues();
		values.put(DochieSQLiteHelper.COLOMN_NAMAGROUP, _namaGrb);

		int updateID = dbGroup.update(DochieSQLiteHelper.TABLE_GROUP, values,
				DochieSQLiteHelper.COLOMN_IDGROUP + " = " + _idGrb, null);
		
		Cursor cursor = dbGroup.query(DochieSQLiteHelper.TABLE_GROUP,
				allcolomns, DochieSQLiteHelper.COLOMN_IDGROUP + " = "
						+ updateID, null, null, null, null);
		
		cursor.moveToFirst();
		group_dochie newGroub = cursorToMessage(cursor);
		cursor.close();
		return newGroub;
	}
	public int updateMessage(String _idGrb,String _namaGrb) {
		ContentValues values = new ContentValues();
		values.put(DochieSQLiteHelper.COLOMN_NAMAGROUP, _namaGrb);

		int updateID = dbGroup.update(DochieSQLiteHelper.TABLE_GROUP, values,
				DochieSQLiteHelper.COLOMN_IDGROUP + " = " + _idGrb, null);
		return updateID;

	}

	public void deleteUsers(group_dochie mGroups) {
		long id = mGroups.get_idGrb();
		Log.v("delete_data:", "delete id: " + id);
		dbGroup.delete(DochieSQLiteHelper.TABLE_GROUP,
				DochieSQLiteHelper.COLOMN_IDGROUP + " = " + id, null);
	}

	public List<group_dochie> getAllMessage() {
		List<group_dochie> groups = new ArrayList<group_dochie>();

		Cursor cursor = dbGroup.query(DochieSQLiteHelper.TABLE_GROUP, allcolomns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			group_dochie group = cursorToMessage(cursor);
			groups.add(group);
			cursor.moveToNext();
		}
		cursor.close();
		return groups;
	}

	private group_dochie cursorToMessage(Cursor cursor) {
		group_dochie _message = new group_dochie();
		_message.set_idGrb(cursor.getLong(0));
		_message.set_namaGrb(cursor.getString(1));
		return _message;
	}
}
