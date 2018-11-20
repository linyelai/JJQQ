package com.linseven.model;

import java.io.Serializable;

public class LoginInfo implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4864166291820499955L;
	private long userId;
	private String password;
	public final long getUserId() 
	{
		return userId;
	}
	public final void setUserId(long userId) 
	{
		this.userId = userId;
	}
	public final String getPassword() 
	{
		return password;
	}
	public final void setPassword(String password) 
	{
		this.password = password;
	}
}
