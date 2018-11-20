package com.linseven.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.linseven.model.Message;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class MessageEncoder extends MessageToMessageEncoder
{

	MarshallingEncoder marshallingEncoder;
	public MessageEncoder() throws IOException
	{
		 	 
	        this.marshallingEncoder= new MarshallingEncoder();
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, Object o, List out) throws Exception 
	{
		// TODO Auto-generated method stub
		Message msg = (Message) o;
		if(msg==null||msg.getHeader()==null)
		{
			throw new Exception("the encode message is null");
		}
		ByteBuf sendBuf = Unpooled.buffer();
		
		sendBuf.writeLong(msg.getHeader().getLength());
		sendBuf.writeByte(msg.getHeader().getType());
		sendBuf.writeLong(msg.getHeader().getDestUserId());
		sendBuf.writeLong(msg.getHeader().getSourceUserId());
		sendBuf.writeLong(msg.getHeader().getSendTime());
		if(msg.getBody()!=null)
		{
			marshallingEncoder.encode(msg.getBody(), sendBuf);
		}
		else
		{
			sendBuf.writeInt(0);
			
		}
		sendBuf.setInt(4, sendBuf.readableBytes());
		out.add(sendBuf);
		
	}

}
