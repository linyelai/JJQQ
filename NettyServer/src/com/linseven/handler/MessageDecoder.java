package com.linseven.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.linseven.model.Header;
import com.linseven.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageDecoder extends LengthFieldBasedFrameDecoder{

	MarshallingDecoder marshallingDecoder;
	public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException
	{
		 super(maxFrameLength, lengthFieldOffset, lengthFieldLength,-8,0);
		// TODO Auto-generated constructor stub
		 marshallingDecoder = new MarshallingDecoder();
		
	}
	protected Object decode(ChannelHandlerContext ctx,ByteBuf in)throws Exception
	{

        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) 
        {
            return null;
        }
        int pre = in.readerIndex();
        in.readerIndex(0);
        Message message = new Message();
        Header header = new Header();
        header.setLength(in.readLong());
        header.setType(in.readByte());
        header.setDestUserId(in.readLong());
        header.setSourceUserId(in.readLong());
        header.setSendTime(in.readLong());
      
        if (in.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(in));
        }
        in.readerIndex(pre);
        message.setHeader(header);
        return message;
		
	}
}
