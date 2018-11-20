package com.linseven.handler;

import java.util.Date;

import com.linseven.model.Header;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter
{

	 @Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        Message message = (Message) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEART_BEAT_MSG) 
        {
            System.out.println("Receive client heart beat message : ---> "+ message);
            
            Message heartBeat = buildHeatBeat();
           // System.out.println("Send heart beat response message to client : ---> "+ heartBeat);
            ctx.writeAndFlush(heartBeat);
        } 
        else
        {
            ctx.fireChannelRead(msg);
        }
    }

    private Message buildHeatBeat() 
    {
        Message message = new Message();
        Header header = new Header();
        header.setType(MessageType.HEART_BEAT_RESP_MSG);
        header.setSendTime(new Date().getTime());
        message.setHeader(header);
        message.setBody("pong");
        return message;
    }
}