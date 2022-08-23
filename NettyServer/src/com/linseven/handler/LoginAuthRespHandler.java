package com.linseven.handler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.linseven.cache.CacheCenter;
import com.linseven.model.Friend;
import com.linseven.model.GroupInfo;
import com.linseven.model.Header;
import com.linseven.model.LoginInfo;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import com.linseven.model.Session;
import com.linseven.model.UserInfo;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

  

   @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception
   {
       
	   	Message message = (Message) msg;
        //
        if (message.getHeader() != null&& message.getHeader().getType() == MessageType.LOGIN_REQ) 
        {
        		//
        	LoginInfo loginInfo = (LoginInfo) message.getBody();
        	Long userId = loginInfo.getUserId();
        	String password = loginInfo.getPassword();
        	List<GroupInfo> groups = new ArrayList<GroupInfo>();
        	//
        	UserInfo user = CacheCenter.getInstance().getUserByuserId(userId);      	
        	//
        	if(CacheCenter.getInstance().getOnlineUsersById(userId)==null)
        	{
        		CacheCenter.getInstance().addOnlineUser(user);
        	}
        	//
        	
        	Session session = CacheCenter.getInstance().getSession(ctx.channel().id().asLongText());
        	if(session==null)
        	{
        		session= new Session();
            	session.setCtx(ctx);
            	session.setSessionId(ctx.channel().id().asLongText());
            	session.setUserId(userId);
            	session.setType(MessageType.LOGIN_RESP);
            	CacheCenter.getInstance().addSession(session);
        	}
        	//
        	List<Message> messages = CacheCenter.getInstance().getMessageByDestUserId(userId);
        	if(messages!=null&&messages.size()>0)
        	{
        		System.out.println("unsend messages size:"+messages.size());
        		for(Message unsendMsg :messages)
        		{
        			
        			ChannelFuture  future = ctx.channel().writeAndFlush(unsendMsg);

        		}
        	}

        	Message respMsg =  buildResponse(user) ;
        	ctx.writeAndFlush(respMsg);
        	
        	
        	
        } 
        else 
        {
            ctx.fireChannelRead(msg);
        }
    }

    private Message buildResponse(Object result) 
    {
    	Message message = new Message();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP);
        header.setSendTime(new Date().getTime());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }
}