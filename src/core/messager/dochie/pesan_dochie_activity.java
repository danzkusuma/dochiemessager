package core.messager.dochie;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import core.messager.dochie.adapter.apesan_dochie;
import core.messager.dochie.bean.kirim_Email;
import core.messager.dochie.bean.pesan_dochie;
import core.messager.dochie.bean.user_dochie;
import core.messager.dochie.helper.DochieJASONHelper;
import core.messager.dochie.helper.DochieJavaMailHelper;
import core.messager.dochie.model.mpesan_dochie;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class pesan_dochie_activity extends Activity {

	private ListView pesanList;
	private apesan_dochie adapter_pesan;
	private mpesan_dochie modelPesan;
	private EditText txtPesan;
	private ImageButton btnImage;
	
	private DochieJASONHelper jsonString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesan_dochie);

		pesanList = (ListView) findViewById(R.id.pesandochie);
		txtPesan = (EditText) findViewById(R.id.txtViewPesan);
		btnImage = (ImageButton) findViewById(R.id.btnSendButton);

		modelPesan = new mpesan_dochie(this);
		modelPesan.open();
		adapter_pesan = new apesan_dochie(pesan_dochie_activity.this,
				R.layout.pesan_list_dochie);
		pesanList.setAdapter(adapter_pesan);

		for (pesan_dochie entry : getNewsEntries()) {
			adapter_pesan.add(entry);
		}

		// jsonString = new DochieJASONHelper();
		// try {
		// JSONArray jsonArray = new
		// JSONArray(jsonString.readJASON(DochieJASONHelper.JSON_USERS));
		// for (int i = 0; i < jsonArray.length(); i++) {
		// JSONObject jsonObject = jsonArray.getJSONObject(i);
		// Log.i(pesan_dochie_activity.class.getName(),
		// jsonObject.getString("id"));
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	// adapter List
	private List<pesan_dochie> getNewsEntries() {
		List<pesan_dochie> entries = new ArrayList<pesan_dochie>();
		entries = modelPesan.getAllMessage(1);
		return entries;
	}

	public void kirimPesan(View v) {

		if (txtPesan.getText().toString().equals(null)
				|| txtPesan.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "Must be Enter Text",
					Toast.LENGTH_SHORT).show();
		} else {
			String ambilData = txtPesan.getText().toString();
			String currentDateTimeString = DateFormat.getDateTimeInstance()
					.format(new Date());
			adapter_pesan.add(modelPesan.createMessage(1, ambilData, null,
					currentDateTimeString, 1, 1, 0));

			new kirimEmail().execute("From Doche", ambilData,
					"johan@labitumm.org", "me@labitumm.org");

			pesanList.setSelection(adapter_pesan.getCount());
			txtPesan.setText("");
		}

	}

	private class kirimEmail extends AsyncTask<String, Integer, String> {
	
		@Override
		protected String doInBackground(String... params) {
			
			for (int i = 0; i < params.length; i++) {
				Log.i("info param "+i, params[i]);
			}
			
			try {
				DochieJavaMailHelper sender = new DochieJavaMailHelper("johan@labitumm.org", "password");
				sender.sendMail(params[0], params[1], params[2], params[3]);
				Log.e("Email", "process sending");
			} catch (Exception ex) {
				String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
				Log.e("sdcard-err2:",err,ex);
			}
			return null;
		}

	}
}
