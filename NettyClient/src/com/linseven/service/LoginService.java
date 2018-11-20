package com.linseven.service;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.linseven.cache.CacheCenter;
import com.linseven.constant.NettyConstant;
import com.linseven.factory.MessageFactory;
import com.linseven.handler.ChatMsgHandler;
import com.linseven.handler.HeartBeatReqHandler;
import com.linseven.handler.LoginAuthReqHandler;
import com.linseven.handler.MessageDecoder;
import com.linseven.handler.MessageEncoder;
import com.linseven.model.Message;
import com.linseven.model.UserInfo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LoginService {
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	//private final static long PORT = ;
	//根据用户名登录
	public void login(long userId,String password)
	{
		//是否已经与服务器连接，如果未连接，那么首先要连接服务器。
		boolean isConnected = CacheCenter.isConnected();
		if(!isConnected){
			try
			{
				connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//从缓存获得当前在线账号信息
		UserInfo currentUser = CacheCenter.getCurrentUser();
		if(currentUser!=null){
			
			long currentUserId = currentUser.getUserId();
			if(currentUserId!=userId){
				//发送登录信息
				Message msg = MessageFactory.buildLoginMsg(userId, password);
				CacheCenter.getInstance().putMessage(msg);
				
			}
		}
		else{
			
			Message msg = MessageFactory.buildLoginMsg(userId, password);
			CacheCenter.getInstance().putMessage(msg);
			
		}
		//登录账号是否为在线账号
		
	}

	public void connect(int port,String host)throws Exception
	{
		Thread connectThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bootstrap boot = new Bootstrap();
				boot.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY,true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// TODO Auto-generated method stub
						ch.pipeline().addLast(new MessageDecoder(1024*1024, 4, 4));
						ch.pipeline().addLast("MessageEncoder", new MessageEncoder());
						//ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(5000));
						ch.pipeline().addLast("LoginAuthReqHandler",new LoginAuthReqHandler() );
						ch.pipeline().addLast("HeartBeatReqHandler",new HeartBeatReqHandler());
						ch.pipeline().addLast("chatMsgHandler",new ChatMsgHandler());
					}
					
				});
				try 
				{
					ChannelFuture future = boot.connect(new InetSocketAddress(host, port)).sync();
					if(future.isSuccess())
					{
						CacheCenter.setConnected(true);
					}
					future.channel().closeFuture().sync();
				} catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			}});
		connectThread.start();
		
	}
	
}
