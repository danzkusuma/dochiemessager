package core.messager.dochie;

import java.util.ArrayList;
import java.util.List;

import core.messager.dochie.adapter.auser_add_group_dochie;
import core.messager.dochie.bean.user_dochie;
import core.messager.dochie.model.mgroup_user_dochie;
import core.messager.dochie.model.muser_dochie;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class add_user_group_dochie_activity extends Activity {
	private muser_dochie musers;
	private ListView list_users;
	private TextView txtHeader;
	private TextView txtSecondHeader;
	private auser_add_group_dochie adapter_users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group_user_dochie);
		musers = new muser_dochie(this);
		musers.open();

		/*
		 * musers.createMessage("Sudar", "sudar@gmail.com","123456","654321",1);
		 * musers.createMessage("Ani", "ani@gmail.com","5678765","5678765",1);
		 * musers.createMessage("Juna", "juna@gmail.com","09890","09890",1);
		 * musers.createMessage("Heri", "heri@gmail.com","123212","123212",1);
		 */

		list_users = (ListView) findViewById(R.id.list_users);
		adapter_users = new auser_add_group_dochie(this,
				R.layout.user_dochie_list_chekbox);
		list_users.setAdapter(adapter_users);

		for (user_dochie entry : getNewsEntries()) {
			adapter_users.add(entry);
		}

		txtHeader = (TextView) findViewById(R.id.textHeader);
		txtSecondHeader = (TextView) findViewById(R.id.txtsecondheader);

		txtHeader.setText("Dochie Messager");
		txtSecondHeader.setText("Contact");

	}

	// adapter List
	private List<user_dochie> getNewsEntries() {
		List<user_dochie> entries = new ArrayList<user_dochie>();
		entries = musers.getAllMessage();
		return entries;
	}

	public void onClickDone(View v) {
		Bundle extras = getIntent().getExtras();
		String idGrb = extras.getString("param1");
		String namagroub = extras.getString("param2");
		mgroup_user_dochie addGrb = new mgroup_user_dochie(this);
		addGrb.open();
		for (int i = 0; i < adapter_users.getCheked().length; i++) {
			Log.d("posisi " + i, adapter_users.getCheked()[i] + "");
			if (adapter_users.getCheked()[i] == true) {
				Log.i("iduser", adapter_users.getItem(i).get_idUsr() + " dan "+idGrb);
				addGrb.creatUserGroup(adapter_users.getItem(i).get_idUsr(),Long.parseLong(idGrb));
				
			}
		}
		Intent i = new Intent(add_user_group_dochie_activity.this
				.getApplicationContext(),
				user_group_dochie_activity.class);
		i.putExtra("param1", idGrb);
		i.putExtra("param2", namagroub);
		startActivity(i);
		finish();
	}

	@Override
	protected void onResume() {
		musers.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		musers.close();
		super.onPause();
	}
}
