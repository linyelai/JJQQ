package com.linseven.model;

import java.util.List;

public class GroupInfo
{
	private long groupId;
	private List<UserInfo> members;
	public final long getGroupId() 
	{
		return groupId;
	}
	public final void setGroupId(long groupId)
	{
		this.groupId = groupId;
	}
	public final List<UserInfo> getMembers() 
	{
		return members;
	}
	public final void setMembers(List<UserInfo> members)
{
		this.members = members;
	}
	
}
