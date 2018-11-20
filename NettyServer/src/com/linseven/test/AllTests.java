package com.linseven.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.linseven.dao.FriendGroupMapper;
import com.linseven.dao.UserMapper;
import com.linseven.db.DBManager;
import com.linseven.model.Friend;
import com.linseven.model.FriendGroup;
import com.linseven.model.UserInfo;



public class AllTests {

	 	@Test
	      public void testAdd() {
		 
	          SqlSession session = DBManager.getInstance().openSession();
	          UserMapper userMapper = session.getMapper(UserMapper.class);
	          List<UserInfo> users =  userMapper.findAllUser();
	          FriendGroupMapper friendGroupMapper = session.getMapper(FriendGroupMapper.class);
	          List<FriendGroup> friendGroupList =null;
	          if(users!=null)
	          {
	        	  
	        	  for(UserInfo user:users)
	        	  {
		        	  long userId = user.getUserId();  
		        	  friendGroupList = friendGroupMapper.findFriendGroupByUserId(userId);
		        	  user.setFriendGroups(friendGroupList);
	        	  }
	          }
	          //
	          
	        
	          for(FriendGroup friendGroup:friendGroupList){
	        
	        	  String friendGroupName = friendGroup.getGroupName();
	        	  if(friendGroupName!=null){
	        		  System.out.println(friendGroupName);
	        	  }
	          }
	          
	    }
	
}
