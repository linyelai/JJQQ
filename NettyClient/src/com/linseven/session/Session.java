package com.linseven.session;

import com.linseven.model.Message;

import io.netty.channel.ChannelHandlerContext;

public class Session 
{
	private ChannelHandlerContext ctx;

	public final ChannelHandlerContext getCtx()
	{
		return ctx;
	}

	public final void setCtx(ChannelHandlerContext ctx) 
	{
		this.ctx = ctx;
	}
	public void SendMessage(Message msg)
	{
		ctx.writeAndFlush(msg);
	}
	
}
