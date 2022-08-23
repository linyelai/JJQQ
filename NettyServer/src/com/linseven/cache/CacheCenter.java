package com.linseven.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;

import java.util.Set;

import com.linseven.dao.FriendGroupMapper;
import com.linseven.dao.UserMapper;
import com.linseven.db.DBManager;
import com.linseven.model.Friend;
import com.linseven.model.FriendGroup;
import com.linseven.model.Message;
import com.linseven.model.Session;
import com.linseven.model.UserInfo;

public class CacheCenter 
{
	private static Map<Long,UserInfo> onlineUsers = new HashMap<Long,UserInfo>();
	private static Map<Long,List<Message>> unsendMessages = new HashMap<Long,List<Message>>();
	private static Map<String,Session> sessions = new HashMap<String,Session>();
	private static Map<Long,UserInfo> allUsers = new HashMap<Long,UserInfo>();
	private static CacheCenter instance = new CacheCenter();
	
	public static CacheCenter getInstance()
	{
		if(instance!=null)
		{
			instance = new CacheCenter();
			
		}
		return instance;
	}

	static{
		
		loadData();
		
	}
	
	
	public UserInfo getOnlineUsersById(Long userId)
	{
		return onlineUsers.get(userId);
	}
	
	private static void loadData() {
		// TODO Auto-generated method stub
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
		        	  allUsers.put(userId, user);
	       	  }
         }
		
	}
	public void addOnlineUser(UserInfo userInfo)
	{
		if(userInfo!=null)
		{
			Long userId = userInfo.getUserId();
			onlineUsers.put(userId, userInfo);
		}
		
	}
	public List<Message> getMessageByDestUserId(Long userId)
	{
		List<Message> unsendMsgs = new ArrayList<Message>();
		if(unsendMessages.get(userId)!=null){
			unsendMsgs.addAll(unsendMessages.get(userId));
		}
		unsendMessages.remove(userId);
		return unsendMsgs;
		
	}
	public void addUnsendMessage(Message message)
	{
		if(message!=null)
		{
			Long userId = message.getHeader().getDestUserId();
			List<Message> messages = unsendMessages.get(userId);
			if(messages==null)
			{
				messages = new ArrayList<Message>();
			}
			messages.add(message);
			unsendMessages.put(userId, messages);
		}
	}
	public Session getSession(String ctx)
	{
		return sessions.get(ctx);
	}
	public void addSession(Session session)
	{
		if(session!=null)
		{
			String ctx = session.getCtx().channel().id().asLongText();
			if(ctx!=null)
			{
				Session sessionTemp = sessions.get(ctx);
				if(sessionTemp==null)
				{
					sessions.put(ctx, session);
				}
				
			}
		}
	}
	
	public void removeSession(String ctxId)
	{
		if(ctxId!=null){
			Session session = getSession(ctxId);
			long userId = session.getUserId();
			List<Session> sessionList = getSessionByUserId(userId);
			boolean offline = true;
			for(Session sessionTemp:sessionList){
				String sessionId = sessionTemp.getSessionId();
				long userIdTemp = sessionTemp.getUserId();
				if(userIdTemp!=userId&&sessionId.equals(ctxId))
				{
					offline = false;
					break;
				}
				
			}
			if(offline){
				onlineUsers.remove(userId);
			}
			sessions.remove(ctxId);
		}
	}



	public List<Session> getSessionByUserId(long userId) {
		// TODO Auto-generated method stub
		List<Session> sessionList = new ArrayList<Session>();
		Set<Entry<String,Session>> sessionEntrySet = sessions.entrySet();
		Iterator<Entry<String, Session>> iterator =  sessionEntrySet.iterator();
		while(iterator.hasNext()){
			Entry<String, Session> s = iterator.next();
			Session sessionTemp = s.getValue();
			if(sessionTemp!=null){
				long userIdTemp = sessionTemp.getUserId();
				if(userIdTemp==userId){
					sessionList.add(sessionTemp);
				}
			}
		}
		return sessionList;
	}
	
	public UserInfo getUserByuserId(long userId){
		
		return allUsers.get(userId);
		
	}
	
}
