package core.messager.dochie.adapter;

import core.messager.dochie.bean.user_dochie;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class auser_add_group_dochie extends ArrayAdapter<user_dochie> {
	private int textViewResourceId;
	boolean isvcheck[]=null;
	public auser_add_group_dochie(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.textViewResourceId = textViewResourceId;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = getWorkingView(convertView);
		if(isvcheck==null){
			isvcheck=new boolean[getCount()];
		}

		final ViewHolder viewHolder = getViewHolder(view);
		user_dochie entry = getItem(position);

		viewHolder.vnama_user_dochie.setText(entry.get_namaUsr());
		viewHolder.vnohp_user_dochie.setText(entry.get_nohpUsr());
		viewHolder.vid_users_dochie.setText(String.valueOf(entry.get_idUsr()));
		isvcheck[position] = false;
		view.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				viewHolder.isvcheck=!viewHolder.isvcheck;
				viewHolder.vcheCheckBox.setChecked(viewHolder.isvcheck);
				isvcheck[position]=viewHolder.isvcheck;
				
			}
		});
		return view;
		
	}
	private ViewHolder getViewHolder(View workingView) {
		Object tag = workingView.getTag();
		ViewHolder viewHolder = null;

		if (null == tag || !(tag instanceof ViewHolder)) {
			viewHolder = new ViewHolder();

			viewHolder.vnama_user_dochie = (TextView) workingView
					.findViewById(core.messager.dochie.R.id.vnama_user_dochie);
			viewHolder.vnohp_user_dochie = (TextView) workingView
					.findViewById(core.messager.dochie.R.id.vnohp_user_dochie);
			viewHolder.vid_users_dochie = (TextView) workingView
					.findViewById(core.messager.dochie.R.id.vid_users);
			viewHolder.vcheCheckBox = (CheckBox)workingView
					.findViewById(core.messager.dochie.R.id.vchekbox);
			workingView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) tag;
		}

		return viewHolder;
	}
	private View getWorkingView(View convertView) {
		View workingView = null;

		if (null == convertView) {
			Context context = getContext();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			workingView = inflater.inflate(textViewResourceId, null);
		} else {
			workingView = convertView;
		}

		return workingView;
	}
	class ViewHolder {
		public TextView vid_users_dochie;
		public TextView vnama_user_dochie;
		public TextView vnohp_user_dochie;
		public CheckBox vcheCheckBox;
		public boolean isvcheck=false;
	
	}
	public boolean[] getCheked(){
		return isvcheck;
	}
	

}
