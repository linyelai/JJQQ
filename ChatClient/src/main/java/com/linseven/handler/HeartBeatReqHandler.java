package com.linseven.handler;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.linseven.cache.CacheCenter;
import com.linseven.model.Friend;
import com.linseven.model.GroupInfo;
import com.linseven.model.Header;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import com.linseven.model.UserInfo;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;

public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

    private volatile ScheduledFuture heartBeat;

    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception 
    {
        Message message = (Message) msg;
        //
        //
        if (message.getHeader() != null&& message.getHeader().getType() == MessageType.HEART_BEAT_RESP_MSG) 
        {
            //
        	synchronized(LoginAuthReqHandler.timeoutCount){
        	LoginAuthReqHandler.timeoutCount--;
        	}
            System.out.println("Client receive server heart beat message : ---> "+ message);
        } 
        else 
        {
            //
            ctx.fireChannelRead(message);
        }
    }

   

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        if (heartBeat != null) 
        {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}