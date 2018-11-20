package com.linseven.model;

import io.netty.channel.ChannelHandlerContext;

public class Session 
{
	private String sessionId;
	private ChannelHandlerContext ctx;
	private long userId;
	private byte type;
	public final String getSessionId() {
		return sessionId;
	}
	public final void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public final ChannelHandlerContext getCtx() {
		return ctx;
	}
	public final void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public final long getUserId() {
		return userId;
	}
	public final void setUserId(long userId) {
		this.userId = userId;
	}
	public final byte getType() {
		return type;
	}
	public final void setType(byte type) {
		this.type = type;
	}
	
}
