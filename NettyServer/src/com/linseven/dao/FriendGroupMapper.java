package com.linseven.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linseven.model.FriendGroup;

public interface FriendGroupMapper {

	public List<FriendGroup> findFriendGroupByUserId(@Param(value="userId")Long userId);
}
