package com.linseven.model;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1511070082139455232L;
	private Long userId;
	private String username;
	private String password;
	private List<Friend> friends;
	//private List<GroupInfo> groups;
	private List<FriendGroup> friendGroups;
	public final Long getUserId() {
		return userId;
	}
	public final void setUserId(Long userId) {
		this.userId = userId;
	}
	public final String getUsername() {
		return username;
	}
	public final void setUsername(String username) {
		this.username = username;
	}

	public final List<Friend> getFriends() {
		return friends;
	}
	public final void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
//	public final List<GroupInfo> getGroups() {
//		return groups;
//	}
//	public final void setGroups(List<GroupInfo> groups) {
//		this.groups = groups;
//	}
	public final String getPassword() {
		return password;
	}
	public final void setPassword(String password) {
		this.password = password;
	}
	public final List<FriendGroup> getFriendGroups() {
		return friendGroups;
	}
	public final void setFriendGroups(List<FriendGroup> friendGroups) {
		this.friendGroups = friendGroups;
	}
	
	
	
	
}
