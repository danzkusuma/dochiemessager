package core.messager.dochie.helper;

import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;


import com.sun.mail.imap.IMAPFolder;

import core.messager.dochie.service.DochieServiceNetwork;

import android.util.Log;

public class DochieMailIdleHelper {

	private boolean startIdle=true;
	private String folderIdle;
	private String protocolIdle;
	private String host;
	private String username;
	private String password;
	private int freq;

	public DochieMailIdleHelper() {
		super();
	}

	public DochieMailIdleHelper(String folderIdle, String protocolIdle,
			String host, String username, String password, int freq) {
		super();
		this.folderIdle = folderIdle;
		this.protocolIdle = protocolIdle;
		this.host = host;
		this.username = username;
		this.password = password;
		this.freq = freq;
		idleforMessage();
	}

	public boolean isStartIdle() {
		return startIdle;
	}

	public void setStartIdle(boolean startIdle) {
		this.startIdle = startIdle;
	}

	public void idleforMessage() {

		try {
			Properties props_email = System.getProperties();
			Session sesi_email = Session.getInstance(props_email, null);
			Store store_email = sesi_email.getStore(protocolIdle);
			store_email.connect(host, username, password);
			Folder folder_email = store_email.getFolder(folderIdle);
			folder_email.open(Folder.READ_WRITE);

			folder_email.addMessageCountListener(new MessageCountAdapter() {
				public void messagesAdded(MessageCountEvent ev) {
					Message[] msgs = ev.getMessages();
					Log.i("info"+DochieMailIdleHelper.class, msgs.length + " new messages");
					
//					for (int i = 0; i < msgs.length; i++) {
//						try {
//							System.out.println("-----");
//							System.out.println("Message "
//									+ msgs[i].getMessageNumber() + ":");
//							msgs[i].writeTo(System.out);
//							
//							
//						} catch (IOException ioex) {
//							ioex.printStackTrace();
//						} catch (MessagingException mex) {
//							mex.printStackTrace();
//						}
//					}

				}
			});

			boolean supportsIdle = true;
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
			
				while (isStartIdle()) {
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
			setStartIdle(false);
			Log.e("error", e.getMessage().toString());
			DochieServiceNetwork x =  new DochieServiceNetwork();
			x.onCreate();
		}
	}

}
