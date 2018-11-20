package com.linseven.handler;

import java.util.List;

import com.linseven.cache.CacheCenter;
import com.linseven.model.Header;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import com.linseven.model.Session;

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
			    	System.out.println(message.getBody());
			    	long destUserId = header.getDestUserId();
			    	List<Session> sessionList = CacheCenter.getInstance().getSessionByUserId(destUserId);
			    	if(sessionList!=null&&sessionList.size()>0)
			    	{
			    		for(Session sessionTemp:sessionList)
			    		{
			    		  ChannelHandlerContext context =  sessionTemp.getCtx();
			    		  if(context!=null)
			    		  {
			    			  context.writeAndFlush(message);
			    		  }
			    		}
			    	}
			    	
			    	else{
			    		
			    		CacheCenter.getInstance().addUnsendMessage(message);
			    		//ctx.fireChannelRead(msg);
			    		//ctx.re
			    	}
			    	
			    }
			    
			 }
			 
		 }
		 
		 
		 
		 
	 }

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		//失去连接，将连接去掉
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		//异常，删除session
		CacheCenter.getInstance().removeSession(ctx.channel().id().asLongText());
		ctx.channel().close();
        ctx.fireExceptionCaught(cause);
	}
	 
}
