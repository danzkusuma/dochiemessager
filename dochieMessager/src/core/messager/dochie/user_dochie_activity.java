package core.messager.dochie;

import java.util.ArrayList;
import java.util.List;
import core.messager.dochie.adapter.auser_dochie;
import core.messager.dochie.bean.user_dochie;
import core.messager.dochie.model.muser_dochie;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class user_dochie_activity extends Activity {
	private muser_dochie musers;
	private ListView list_users;
	private TextView txtHeader;
	private TextView txtSecondHeader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_dochie);
		musers = new muser_dochie(this);
		musers.open();
		
		//musers.createMessage("Widiya","yaya@yaho.com","1234567890","1234567890",1);
		
		list_users = (ListView) findViewById(R.id.list_users);
		auser_dochie adapter_users = new auser_dochie(this, R.layout.user_dochie_list);
		list_users.setAdapter(adapter_users);
		
		for (user_dochie entry : getNewsEntries()) {
			adapter_users.add(entry);
		}
		
		txtHeader = (TextView) findViewById(R.id.textHeader);
		txtSecondHeader = (TextView) findViewById(R.id.txtsecondheader);
		
		txtHeader.setText("Dochie Messager");
		txtSecondHeader.setText("Contact");
		
		musers.close();
	}
		//adapter List	
		private List<user_dochie> getNewsEntries() {
			List<user_dochie> entries = new ArrayList<user_dochie>();
			entries = musers.getAllMessage();
			return entries;
		}
		
		public void kirimPesan(View v){
			
		}
}
