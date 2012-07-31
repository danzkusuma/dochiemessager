package core.messager.dochie;

import java.util.ArrayList;
import java.util.List;

import core.messager.dochie.adapter.agroup_dochie;
import core.messager.dochie.bean.group_dochie;
import core.messager.dochie.model.mgroup_dochie;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class group_dochie_activity extends Activity {
	private mgroup_dochie mgroups;
	private ListView list_groups;
	private TextView txtHeader;
	private TextView txtSecondHeader;
	private ImageButton btnLeft1;
	private ImageButton btnLeft2;
	private agroup_dochie adapter_groups;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_dochie);
		mgroups = new mgroup_dochie(this);
		mgroups.open();
		
		list_groups = (ListView) findViewById(R.id.list_goups);
		adapter_groups = new agroup_dochie(this,
				R.layout.group_dochie_list);
		list_groups.setAdapter(adapter_groups);
		
		for (group_dochie entry : getNewsEntries()) {
			adapter_groups.add(entry);
		}
		txtHeader = (TextView) findViewById(R.id.textHeader);
		txtSecondHeader = (TextView) findViewById(R.id.txtsecondheader);
		btnLeft1 = (ImageButton) findViewById(R.id.btnLeft1);
		btnLeft2 = (ImageButton) findViewById(R.id.btnLeft2);

		btnLeft2.setImageResource(R.drawable.social_add_group);
		txtHeader.setText("Dochie Messager");
		txtSecondHeader.setText("Groups");

		list_groups.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String idGroups = ((TextView) arg1.findViewById(R.id.vid_group))
						.getText().toString();
				String namaGroup = ((TextView) arg1
						.findViewById(R.id.vnama_group_dochie)).getText()
						.toString();
				Intent i = new Intent(group_dochie_activity.this
						.getApplicationContext(),
						user_group_dochie_activity.class);
				i.putExtra("param1", idGroups);
				i.putExtra("param2", namaGroup);
				startActivity(i);

			}
		});
		
		list_groups.setOnItemLongClickListener(	new OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						final String idGroups = ((TextView) arg1.findViewById(R.id.vid_group))
								.getText().toString();
//						final group_dochie g = null;
//						String namaGroup = ((TextView) arg1
//								.findViewById(R.id.vnama_group_dochie)).getText()
//								.toString();
						final int listposition = arg2;
						TextView a = (TextView)arg1.findViewById(R.id.vnama_group_dochie);
						final String dataforUpdate = a.getText().toString();
						final CharSequence[] items = {"Update Group", "Delete Group"};

						AlertDialog.Builder builder = new AlertDialog.Builder(group_dochie_activity.this);
						builder.setTitle("Pick a color");
						builder.setItems(items, new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog, int item) {
						         //Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
						         if (item==0) {
									final Dialog dialogUpdate = new Dialog(group_dochie_activity.this);
									dialogUpdate.setTitle("Update Group");
									dialogUpdate.setContentView(R.layout.add_grub_dialog);
									TextView dataUpdate = (TextView)dialogUpdate.findViewById(R.id.txtGroupAdd);
									dataUpdate.setText(dataforUpdate);
									
									
									//mgroups = new mgroup_dochie(group_dochie_activity.this);
									//mgroups.open();
									final Button tambahGroup = (Button)dialogUpdate.findViewById(R.id.btnTambahGroup);
									final EditText tambahNamaGroup = (EditText)dialogUpdate.findViewById(R.id.txtGroupAdd);
									tambahGroup.setOnClickListener(new OnClickListener() {
										public void onClick(View v) {
											adapter_groups.remove(adapter_groups.getItem(listposition));
											adapter_groups.notifyDataSetChanged();
											Log.v("Sukses", "sukses");
											adapter_groups.add(mgroups.updateGroup(idGroups,tambahNamaGroup.getText().toString()));
											adapter_groups.notifyDataSetChanged();
											dialogUpdate.dismiss();
										}
									});
									
									dialogUpdate.show();
									
								}else if (item==1) {
									mgroups.deleteUsers(new group_dochie(Long.parseLong(idGroups)));
									adapter_groups.remove(adapter_groups.getItem(listposition));
									adapter_groups.notifyDataSetChanged();
								}
						    }
						});
						AlertDialog alert = builder.create();
						alert.show();
						return false;
					}
		});
	}

	// adapter List
	private List<group_dochie> getNewsEntries() {
		List<group_dochie> entries = new ArrayList<group_dochie>();
		entries = mgroups.getAllMessage();
		return entries;
	}
//	private List<group_dochie> getNewsEntries2(String _namaGrb){
//		List<group_dochie> entries = new ArrayList<group_dochie>();
//		entries.add(new group_dochie(_namaGrb));
//		return entries;
//	}
	public void tekanLeft1(View v) {

	}

	public void tekanLeft2(View v) {
		final Dialog dialog = new Dialog(group_dochie_activity.this);
		dialog.setContentView(R.layout.add_grub_dialog);
		dialog.setCancelable(true);
		dialog.setTitle("Add Group");
		//mgroups = new mgroup_dochie(group_dochie_activity.this);
		//mgroups.open();
		final Button tambahGroup = (Button)dialog.findViewById(R.id.btnTambahGroup);
		final EditText tambahNamaGroup = (EditText)dialog.findViewById(R.id.txtGroupAdd);
		tambahGroup.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//mgroups.createMessage(tambahNamaGroup.getText().toString());
				adapter_groups.add(mgroups.createMessage(tambahNamaGroup.getText().toString()));
				adapter_groups.notifyDataSetChanged();
				dialog.dismiss();
				Log.v("Sukses", "sukses");			
			}
		});
		dialog.show();
		
		
	}
	
	@Override
	protected void onResume() {
		mgroups.open();
		super.onResume();
	}
	@Override
	protected void onPause() {
		mgroups.close();
		super.onPause();
	}
}
