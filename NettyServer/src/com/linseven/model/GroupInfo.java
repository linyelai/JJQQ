package com.linseven.model;

import java.io.Serializable;
import java.util.List;

public class GroupInfo implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8993226114523228109L;
	private long groupId;
	private List<UserInfo> members;
	public final long getGroupId() {
		return groupId;
	}
	public final void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public final List<UserInfo> getMembers() {
		return members;
	}
	public final void setMembers(List<UserInfo> members) {
		this.members = members;
	}
	
	
}
