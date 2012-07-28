package core.messager.dochie;

import java.util.ArrayList;
import java.util.List;

import core.messager.dochie.adapter.agroup_users_dochie;
import core.messager.dochie.bean.group_user_dochie;
import core.messager.dochie.model.mgroup_user_dochie;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class user_group_dochie_activity extends Activity {
	private mgroup_user_dochie mgroup;
	private ListView list_users;
	private TextView txtHeader;
	private TextView txtSecondHeader;
	private ImageButton btnLeftFirst;
	private ImageButton btnLeftSecond;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_dochie);
		
		mgroup = new mgroup_user_dochie(this);
		mgroup.open();
		
		list_users = (ListView) findViewById(R.id.list_users);
		agroup_users_dochie adapter_users = new agroup_users_dochie(this,
				R.layout.user_dochie_list);
		list_users.setAdapter(adapter_users);

		for (group_user_dochie entry : getNewsEntries()) {
			adapter_users.add(entry);
		}
		txtHeader = (TextView) findViewById(R.id.textHeader);
		txtSecondHeader = (TextView) findViewById(R.id.txtsecondheader);
		btnLeftFirst = (ImageButton) findViewById(R.id.btnLeft1);
		btnLeftSecond = (ImageButton) findViewById(R.id.btnLeft2);
		
		btnLeftFirst.setImageResource(R.drawable.social_group);
		
		txtHeader.setText("Dochie Messager");
		Bundle extras = getIntent().getExtras();
		txtSecondHeader.setText(extras.getString("param2"));
		
	}

	// adapter List
	private List<group_user_dochie> getNewsEntries() {
		List<group_user_dochie> entries = new ArrayList<group_user_dochie>();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String bar = extras.getString("param1");
			Log.v("param1", bar);
			entries = mgroup.getAllUsersGroup(bar);
		}

		return entries;
	}
	
	public void tekanLeft1(View v){
		Bundle extras = getIntent().getExtras();
		Intent i = new Intent(user_group_dochie_activity.this.getApplicationContext()
				,add_user_group_dochie_activity.class);
		i.putExtra("param1",extras.getString("param1"));
		i.putExtra("param2",extras.getString("param2"));
		startActivity(i);
		finish();
		
	}
	
	public void tekanLeft2(View v){
		
	}
	@Override
	protected void onResume() {
		mgroup.open();
		super.onResume();
	}
	@Override
	protected void onPause() {
		mgroup.close();
		super.onPause();
	}
}
