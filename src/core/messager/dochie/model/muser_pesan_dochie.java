package core.messager.dochie.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import core.messager.dochie.bean.group_pesan_dochie;
import core.messager.dochie.bean.user_pesan_dochie;
import core.messager.dochie.helper.DochieSQLiteHelper;

/*long _idUsr, String _isiPsn, String _filePsn,
			String _timePsn, int _isSend, int _isMe, long _idGrb,
			String _namaUsr, String _emailUsr, String _nohpUsr*/

public class muser_pesan_dochie {
	private SQLiteDatabase dbUserPesan;
	private DochieSQLiteHelper dbHelper;

	public muser_pesan_dochie(Context context) {
		dbHelper = new DochieSQLiteHelper(context);
	}

	public void open() throws SQLException {
		dbUserPesan = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	public List<user_pesan_dochie> getAllMessage() {
		List<user_pesan_dochie> pesans = new ArrayList<user_pesan_dochie>();

		String sql = "SELECT * " +
				"P."+DochieSQLiteHelper.COLOMN_IDPESAN+
				",P."+DochieSQLiteHelper.COLOMN_IDUSER+
				",P."+DochieSQLiteHelper.COLOMN_ISIPESAN+
				",P."+DochieSQLiteHelper.COLOMN_FILEPESAN+
				",P."+DochieSQLiteHelper.COLOMN_TIMEPESAN+
				",P."+DochieSQLiteHelper.COLOMN_ISSEND+
				",P."+DochieSQLiteHelper.COLOMN_ISME+
				",P."+DochieSQLiteHelper.COLOMN_IDGROUP+
				",U."+DochieSQLiteHelper.COLOMN_NAMAUSER+
				",U."+DochieSQLiteHelper.COLOMN_EMAILUSER+
				",U."+DochieSQLiteHelper.COLOMN_NOHPUSER+
				"FROM user U, pesan P WHERE U._idUsr = P._idUsr AND P._idGrb = 0";
		
		Cursor cursor = dbUserPesan.rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			user_pesan_dochie pesan = cursorToMessage(cursor);
			pesans.add(pesan);
			cursor.moveToNext();
		}
		cursor.close();
		return pesans;
	}
	public List<group_pesan_dochie> getAllMessageGrb(long _idGrb) {
		List<group_pesan_dochie> pesans = new ArrayList<group_pesan_dochie>();

		String sql = "SELECT * " +
				"P."+DochieSQLiteHelper.COLOMN_IDPESAN+
				",P."+DochieSQLiteHelper.COLOMN_IDUSER+
				",P."+DochieSQLiteHelper.COLOMN_ISIPESAN+
				",P."+DochieSQLiteHelper.COLOMN_FILEPESAN+
				",P."+DochieSQLiteHelper.COLOMN_TIMEPESAN+
				",P."+DochieSQLiteHelper.COLOMN_ISSEND+
				",P."+DochieSQLiteHelper.COLOMN_ISME+
				",P."+DochieSQLiteHelper.COLOMN_IDGROUP+
				",U."+DochieSQLiteHelper.COLOMN_NAMAUSER+
				",U."+DochieSQLiteHelper.COLOMN_EMAILUSER+
				",U."+DochieSQLiteHelper.COLOMN_NOHPUSER+
				",G."+DochieSQLiteHelper.COLOMN_NOHPUSER+
				"FROM user U, pesan P, group G WHERE U._idUsr = P._idUsr AND P._idGrb = G._idGrb AND P._idGrb="+_idGrb;
		
		Cursor cursor = dbUserPesan.rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			group_pesan_dochie pesan = cursorToMessageGrb(cursor);
			pesans.add(pesan);
			cursor.moveToNext();
		}
		cursor.close();
		return pesans;
	}
	
	/*long _idUsr, String _isiPsn, String _filePsn,
	String _timePsn, int _isSend, int _isMe, long _idGrb,
	String _namaUsr, String _emailUsr, String _nohpUsr*/
	
	private user_pesan_dochie cursorToMessage(Cursor cursor) {
		user_pesan_dochie _message = new user_pesan_dochie();
		_message.set_idPsn(cursor.getLong(0));
		_message.set_idUsr(cursor.getLong(1));
		_message.set_isiPsn(cursor.getString(2));
		_message.set_filePsn(cursor.getString(3));
		_message.set_timePsn(cursor.getString(4));
		_message.set_isSend(cursor.getInt(5));
		_message.set_isMe(cursor.getInt(6));
		_message.set_idGrb(cursor.getLong(7));
		_message.set_namaUsr(cursor.getString(8));
		_message.set_emailUsr(cursor.getString(9));
		_message.set_nohpUsr(cursor.getString(10));
		return _message;
	}
	
	private group_pesan_dochie cursorToMessageGrb(Cursor cursor) {
		group_pesan_dochie _message = new group_pesan_dochie();
		_message.set_idPsn(cursor.getLong(0));
		_message.set_idUsr(cursor.getLong(1));
		_message.set_isiPsn(cursor.getString(2));
		_message.set_filePsn(cursor.getString(3));
		_message.set_timePsn(cursor.getString(4));
		_message.set_isSend(cursor.getInt(5));
		_message.set_isMe(cursor.getInt(6));
		_message.set_idGrb(cursor.getLong(7));
		_message.set_namaUsr(cursor.getString(8));
		_message.set_emailUsr(cursor.getString(9));
		_message.set_nohpUsr(cursor.getString(10));
		_message.set_namaGrb(cursor.getString(11));
		
		return _message;
	}
}
