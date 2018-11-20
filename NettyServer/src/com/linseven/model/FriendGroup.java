package com.linseven.model;

import java.io.Serializable;

public class FriendGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1019578542429118975L;
	private String groupName;
	private Long groupId;
	private Long userId;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
