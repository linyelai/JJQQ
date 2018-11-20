package com.linseven.model;

public class Header
{
	private long length;
	private byte type;
	private long destUserId;
	private long sourceUserId;
	private Long sendTime;
	
	public final long getLength() {
		return length;
	}
	public final void setLength(long length) {
		this.length = length;
	}
	public final byte getType() {
		return type;
	}
	public final void setType(byte type) {
		this.type = type;
	}
	public final long getDestUserId() {
		return destUserId;
	}
	public final void setDestUserId(long destUserId) {
		this.destUserId = destUserId;
	}
	public final long getSourceUserId() {
		return sourceUserId;
	}
	public final void setSourceUserId(long sourceUserId) {
		this.sourceUserId = sourceUserId;
	}
	public final Long getSendTime() {
		return sendTime;
	}
	public final void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "Header [length=" + length + ", type=" + type + ", destUserId=" + destUserId + ", sourceUserId="
				+ sourceUserId + ", sendTime=" + sendTime + "]";
	}
	
	
}
