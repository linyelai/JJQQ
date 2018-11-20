package com.linseven.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linseven.model.UserInfo;

public interface UserMapper {

	public UserInfo queryUser(UserInfo user);
	public UserInfo queryUserById(@Param("userId")Long userId);
	public List<UserInfo> findAllUser();
}
