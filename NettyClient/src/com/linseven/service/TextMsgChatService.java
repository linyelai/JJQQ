package com.linseven.service;

import com.linseven.cache.CacheCenter;
import com.linseven.factory.MessageFactory;
import com.linseven.model.Message;

public class TextMsgChatService 
{
		public void chat(long dstUserId,String msgstr)
		{
			Message msg = MessageFactory.buildTextMsg(dstUserId, msgstr);
			CacheCenter.getInstance().putMessage(msg);
			
			
		}
}
