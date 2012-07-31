package core.messager.dochie.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import core.messager.dochie.bean.pesan_dochie;
import core.messager.dochie.helper.DochieSQLiteHelper;

public class mpesan_dochie {
	private SQLiteDatabase dbPesan;
	private DochieSQLiteHelper dbHelper;
	private String allcolomns[] = {
			DochieSQLiteHelper.COLOMN_IDPESAN,
			DochieSQLiteHelper.COLOMN_IDUSER,
			DochieSQLiteHelper.COLOMN_ISIPESAN,
			DochieSQLiteHelper.COLOMN_FILEPESAN,
			DochieSQLiteHelper.COLOMN_TIMEPESAN,
			DochieSQLiteHelper.COLOMN_ISSEND,
			DochieSQLiteHelper.COLOMN_ISME
	};

	public mpesan_dochie(Context context) {
		dbHelper = new DochieSQLiteHelper(context);
	}

	public void open() throws SQLException {
		dbPesan = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public pesan_dochie createMessage(long _idUsr, String _isiPsn,
			String _filePsn, String _timePsn, int _isSend, int _isMe,long _idGrb) {
		ContentValues values = new ContentValues();
		values.put(DochieSQLiteHelper.COLOMN_IDUSER, _idUsr);
		values.put(DochieSQLiteHelper.COLOMN_ISIPESAN, _isiPsn);
		values.put(DochieSQLiteHelper.COLOMN_FILEPESAN, _filePsn);
		values.put(DochieSQLiteHelper.COLOMN_TIMEPESAN, _timePsn);
		values.put(DochieSQLiteHelper.COLOMN_ISSEND, _isSend);
		values.put(DochieSQLiteHelper.COLOMN_ISME, _isMe);
		values.put(DochieSQLiteHelper.COLOMN_IDGROUP, _idGrb);
		
		long insertId = dbPesan.insert(DochieSQLiteHelper.TABLE_PESAN, null,
				values);
		Cursor cursor = dbPesan.query(DochieSQLiteHelper.TABLE_PESAN,
				allcolomns, DochieSQLiteHelper.COLOMN_IDPESAN + " = "
						+ insertId, null, null, null, null);
		cursor.moveToFirst();
		pesan_dochie newGroub = cursorToMessage(cursor);
		cursor.close();
		return newGroub;
	}

	public int updateMessage(long _idPsn, long _idUsr, String _isiPsn,
			String _filePsn, String _timePsn, int _isSend, int _isMe,long _idGrb) {
		ContentValues values = new ContentValues();
		values.put(DochieSQLiteHelper.COLOMN_IDUSER, _idUsr);
		values.put(DochieSQLiteHelper.COLOMN_ISIPESAN, _isiPsn);
		values.put(DochieSQLiteHelper.COLOMN_FILEPESAN, _filePsn);
		values.put(DochieSQLiteHelper.COLOMN_TIMEPESAN, _timePsn);
		values.put(DochieSQLiteHelper.COLOMN_ISSEND, _isSend);
		values.put(DochieSQLiteHelper.COLOMN_ISME, _isMe);
		values.put(DochieSQLiteHelper.COLOMN_IDGROUP, _idGrb);
		
		int updateID = dbPesan.update(DochieSQLiteHelper.TABLE_PESAN, values,
				DochieSQLiteHelper.COLOMN_IDPESAN + " = " + _idPsn
				, null);
		return updateID;

	}

	public void deleteUsers(pesan_dochie mPesans) {
		long id = mPesans.get_idPsn();
		Log.v("delete_data:", "delete id: " + id);
		dbPesan.delete(DochieSQLiteHelper.TABLE_PESAN,
				DochieSQLiteHelper.COLOMN_IDPESAN + " = " + id, null);
	}

	public List<pesan_dochie> getAllMessage(long _idUsr) {
		List<pesan_dochie> pesans = new ArrayList<pesan_dochie>();
		String sql = "SELECT * FROM PESAN";
		Cursor cursor = dbPesan.query(DochieSQLiteHelper.TABLE_PESAN, allcolomns,
				DochieSQLiteHelper.COLOMN_IDGROUP+" = "+0 +" and "+
				DochieSQLiteHelper.COLOMN_IDUSER+" = "+_idUsr
				, null, null, null, null);
//		Cursor cursor = dbPesan.rawQuery(sql,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			pesan_dochie pesan = cursorToMessage(cursor);
			pesans.add(pesan);
			cursor.moveToNext();
		}
		cursor.close();
		return pesans;
	}
	public List<pesan_dochie> getAllMessageGroub(long _idGrb) {
		List<pesan_dochie> pesans = new ArrayList<pesan_dochie>();

		Cursor cursor = dbPesan.query(DochieSQLiteHelper.TABLE_PESAN, allcolomns,
				DochieSQLiteHelper.COLOMN_IDGROUP+" = "+_idGrb, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			pesan_dochie pesan = cursorToMessageGroup(cursor);
			pesans.add(pesan);
			cursor.moveToNext();
		}
		cursor.close();
		return pesans;
	}

	private pesan_dochie cursorToMessage(Cursor cursor) {
		pesan_dochie _message = new pesan_dochie();
		_message.set_idPsn(cursor.getLong(0));
		_message.set_idUser(cursor.getLong(1));
		_message.set_isiPsn(cursor.getString(2));
		_message.set_filePsn(cursor.getString(3));
		_message.set_timePsn(cursor.getString(4));
		_message.set_isSend(cursor.getInt(5));
		_message.set_isMe(cursor.getInt(6));
		return _message;
	}
	private pesan_dochie cursorToMessageGroup(Cursor cursor) {
		pesan_dochie _message = new pesan_dochie();
		_message.set_idPsn(cursor.getLong(0));
		_message.set_idUser(cursor.getLong(1));
		_message.set_isiPsn(cursor.getString(2));
		_message.set_filePsn(cursor.getString(3));
		_message.set_timePsn(cursor.getString(4));
		_message.set_isSend(cursor.getInt(5));
		_message.set_isMe(cursor.getInt(6));
		_message.set_idGrb(cursor.getLong(7));
		return _message;
	}
}
