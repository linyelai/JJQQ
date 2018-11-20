package com.linseven.model;

public class Friend {

	private Long id;
	private Long userId;
	private Long friendId;
	private String name;
	private Long friendGroupId;
	public final Long getFriendId() {
		return friendId;
	}

	public final void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public final Long getUserId() {
		return userId;
	}
	
	public final void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}

	public final Long getFriendGroupId() {
		return friendGroupId;
	}

	public final void setFriendGroupId(Long friendGroupId) {
		this.friendGroupId = friendGroupId;
	}
	
	
}
