package com.linseven.handler;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import com.linseven.io.ChannelBufferByteInput;

import io.netty.buffer.ByteBuf;

public class MarshallingDecoder
{
	private Unmarshaller unmarshaller ;
	public MarshallingDecoder() throws IOException
	{
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        unmarshaller = marshallerFactory.createUnmarshaller(configuration);
	}
	protected Object decode(ByteBuf in)throws Exception
	{
		int ObjectSize = in.readInt();
		ByteBuf buf = in.slice(in.readerIndex(), ObjectSize);
		ByteInput input = new ChannelBufferByteInput(buf);
		try
		{
			unmarshaller.start(input);
			Object object  =unmarshaller.readObject();
			unmarshaller.finish();
			in.readerIndex(in.readerIndex()+ObjectSize);
			return object;
		}
		finally
		{
			unmarshaller.close();
		}
		
	}
}
