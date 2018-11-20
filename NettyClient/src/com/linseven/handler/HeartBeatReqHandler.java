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
        // 握手成功，主动发送心跳消息
        //HeartBeatReqHandler接收到之后对消息进行判断
        if (message.getHeader() != null&& message.getHeader().getType() == MessageType.HEART_BEAT_RESP_MSG) 
        {
            //接收服务端发送的心跳应答消息，并打印客户端接收和发送的心跳消息。
        	synchronized(LoginAuthReqHandler.timeoutCount){
        	LoginAuthReqHandler.timeoutCount--;
        	}
            System.out.println("Client receive server heart beat message : ---> "+ message);
        } 
        else 
        {
            //当握手成功之后，握手请求Handler会继续将握手成功消息向下透传
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