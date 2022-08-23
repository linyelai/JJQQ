package com.linseven.handler;

import java.util.Date;
import java.util.List;

import com.linseven.cache.CacheCenter;
import com.linseven.component.ChatDialog;
import com.linseven.model.Friend;
import com.linseven.model.GroupInfo;
import com.linseven.model.Header;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import com.linseven.model.UserInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChatMsgHandler extends ChannelInboundHandlerAdapter
{
	 @Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception 
	 {
		 Message message = (Message) msg;
		 if(message!=null)
		 {
			 Header header = message.getHeader();
			 
			 if(header!=null)
			 {
			    byte type=	 header.getType();
			    if(type==MessageType.TEXT_MSG)
			    {
			    	long sourceUserId = header.getSourceUserId();
			    	long time = header.getSendTime();
			    	String msgBody = (String)message.getBody();
			    	System.out.println("from:"+sourceUserId+"  time"+(new Date(time)));
			    	System.out.println("message:"+msgBody);
			    	ChatDialog dialog = CacheCenter.getInstance().getDialogByUserId(sourceUserId);
			    	dialog.reciveMsg(message);
			    	
			    }
			 
			 }
		 }
		 
		 
		 
	 }

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		System.out.println("lose connected");
	}
	 
}
