package com.linseven.sender;

import java.util.Date;
import java.util.Scanner;

import com.linseven.cache.CacheCenter;
import com.linseven.model.Header;
import com.linseven.model.LoginInfo;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import com.linseven.model.UserInfo;
import com.linseven.session.Session;

import io.netty.channel.ChannelHandlerContext;

public class MessageSender implements Runnable
{
	private  static Session session ;
	private static  Long currentChatId;
	public static Boolean isOnline= false;
	private static long loginUserId;
	public static Object sessionSignal = new Object();
	public static boolean isConnected = false;
	public static UserInfo currentUser;
	public static Object signal1 = new Object();
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		Message msg =null;
		while(true)
		{
			 msg= CacheCenter.getInstance().pollMessage();
			 if(msg==null)
			 {
				try 
				{
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 else{
				 if(session==null)
				 {
					 synchronized(sessionSignal){
						 try 
						 {
							sessionSignal.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
				 }
				 session.SendMessage(msg);
			 }
		
		}
    	//exit �����������󣬷�����Դ�ͷ�

	}
	public static final Session getSession() {
		return session;
	}
	public static final void setSession(Session session) {
		MessageSender.session = session;
	}
	private Message buildLoginReq(Message msg) 
    {
        // ���ڲ���IP��������֤���ƣ���ˣ�����ҪЯ����Ϣ�壬��Ϣ��Ϊ�գ���Ϣ����Ϊ3������������Ϣ��
        //Message message = new Message();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ);
        header.setSendTime(new Date().getTime());
        msg.setHeader(header);
        return msg;
    }

}
