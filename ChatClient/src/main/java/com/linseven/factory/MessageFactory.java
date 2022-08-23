package com.linseven.factory;

import java.util.Date;

import com.linseven.cache.CacheCenter;
import com.linseven.model.Header;
import com.linseven.model.LoginInfo;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import com.linseven.model.UserInfo;

public class MessageFactory {
	
	public static Message buildLoginMsg(long userId,String password)
	{
		Message msg = new Message();
		Header header = new Header();
		header.setSendTime(new Date().getTime());
		header.setType(MessageType.LOGIN_REQ);
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserId(userId);
		loginInfo.setPassword(password);
		msg.setHeader(header);
		msg.setBody(loginInfo);
		return msg;
	
	}
	
	public static Message buildTextMsg(long dstUserId,String msgStr){
		
		Message msg = new Message();
		Header header = new Header();
		header.setType(MessageType.TEXT_MSG);
		header.setSendTime(new Date().getTime());
		UserInfo currentUser = CacheCenter.getCurrentUser();
		if(currentUser!=null)
		{
			header.setSourceUserId(currentUser.getUserId());
			header.setDestUserId(dstUserId);
			
		}
		msg.setHeader(header);
		msg.setBody(msgStr);
		return msg;
	}

}
