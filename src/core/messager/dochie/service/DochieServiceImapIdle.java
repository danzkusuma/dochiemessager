package core.messager.dochie.service;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

import com.sun.mail.imap.IMAPFolder;

import core.messager.dochie.R;
import core.messager.dochie.pesan_dochie_activity;
import core.messager.dochie.helper.DochieJavaMailHelper;
import core.messager.dochie.helper.DochieMailIdleHelper;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class DochieServiceImapIdle extends Service {
	DochieMailIdleHelper dcMailIdle;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		new bgprocesspush().execute(
				"imap",
				DochieJavaMailHelper.mailhost,
				"johan@labitumm.org",
				"password",
				"INBOX",
				"1000"
				);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private class bgprocesspush extends AsyncTask<String, String, String>{
		//protocol,host,username,password,folder,freq
		
		@Override
		protected String doInBackground(String... params) {
			
			try {
				Properties props_email = System.getProperties();
				Session sesi_email = Session.getInstance(props_email, null);
				Store store_email = sesi_email.getStore(params[0]);
				store_email.connect(params[1], params[2], params[3]);
				Folder folder_email = store_email.getFolder(params[4]);
				folder_email.open(Folder.READ_WRITE);

				folder_email.addMessageCountListener(new MessageCountAdapter() {
					public void messagesAdded(MessageCountEvent ev) {
						MediaPlayer player = new MediaPlayer();
						MediaPlayer.create(DochieServiceImapIdle.this,R.drawable.notify);
						player.setLooping(false);
						player.start();
						
						Message[] msgs = ev.getMessages();
						Log.i("info"+DochieMailIdleHelper.class, msgs.length + " new messages");
						
						String ns = Context.NOTIFICATION_SERVICE;
						NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
						int icon = R.drawable.mail_receive;
						CharSequence tickerText = "Dochie Messager";
						long when = System.currentTimeMillis();

						Notification notification = new Notification(icon, tickerText, when);
						Context context = getApplicationContext();
						CharSequence contentTitle = "Dochie Messager notification";
						CharSequence contentText = msgs.length + " new messages";
						Intent notificationIntent = new Intent(DochieServiceImapIdle.this, pesan_dochie_activity.class);
						PendingIntent contentIntent = PendingIntent.getActivity(DochieServiceImapIdle.this, 0, notificationIntent, 0);

						notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
						//private static final int HELLO_ID = 1;
						notification.number += 1; 
						mNotificationManager.notify(0, notification);
						
						
						 // Set looping
						
//						for (int i = 0; i < msgs.length; i++) {
//							try {
//								System.out.println("-----");
//								System.out.println("Message "
//										+ msgs[i].getMessageNumber() + ":");
//								msgs[i].writeTo(System.out);
//								
//								
//							} catch (IOException ioex) {
//								ioex.printStackTrace();
//							} catch (MessagingException mex) {
//								mex.printStackTrace();
//							}
//						}

					}
				});

				boolean supportsIdle = true;
				int freq = Integer.parseInt(params[5]);
				try {
					if (folder_email instanceof IMAPFolder) {
						IMAPFolder f = (IMAPFolder) folder_email;
						f.idle();
						supportsIdle = true;
					}
				} catch (FolderClosedException fex) {

					throw fex;

				} catch (MessagingException mex) {
					supportsIdle = false;
				}
				
					for(;;){
						if (supportsIdle && folder_email instanceof IMAPFolder) {
							IMAPFolder f = (IMAPFolder) folder_email;
							f.idle();
							System.out.println("IDLE done");
						} else {
							Thread.sleep(freq);
							folder_email.getMessageCount();
						}
					}
				

			} catch (Exception e) {
				Log.e("error", e.getMessage().toString());
				DochieServiceNetwork x =  new DochieServiceNetwork();
				x.onCreate();
			}
			return null;
		}
		
	}
}
