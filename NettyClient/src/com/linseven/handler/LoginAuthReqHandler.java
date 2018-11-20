package com.linseven.handler;



import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.linseven.cache.CacheCenter;
import com.linseven.client.NettyClient;
import com.linseven.model.Friend;
import com.linseven.model.Header;
import com.linseven.model.LoginInfo;
import com.linseven.model.Message;
import com.linseven.model.MessageType;
import com.linseven.model.UserInfo;
import com.linseven.sender.MessageSender;
import com.linseven.session.Session;
import com.linseven.window.JJQQ;
import com.linseven.window.MainWindow;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

	private volatile ScheduledFuture heartBeat;
	public  static Integer timeoutCount=0;
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当客户端跟服务端TCP三次握手成功之后，由客户端构造握手请求消息发送给服务端
        //ctx.writeAndFlush(buildLoginReq());
        Session session = new Session();
        session.setCtx(ctx);
        MessageSender.setSession(session);
        synchronized(MessageSender.sessionSignal)
		{
				MessageSender.sessionSignal.notifyAll();
			
		}
        //MessageSender.sessionSignal.notifyAll();
        
    }

    // 握手请求发送之后，按照协议规范，服务端需要返回握手应答消息。
	 @Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        //对握手应答消息进行处理，首先判断消息是否是握手应答消息，
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP) 
        {
            UserInfo user = (UserInfo) message.getBody();
            if (user==null)
            {
                // 如果是握手应答消息，则对应答结果进行判断，如果非0，说明认证失败，关闭链路，重新发起连接。
                // 握手失败，关闭连接
                ctx.close();
            } 
           else 
           {
        	   /*synchronized(MessageSender.isOnline)
        	   {
        	    MessageSender.isOnline = true;
        	    MessageSender.currentUser = (UserInfo)message.getBody();
        	    MessageSender.isOnline.notifyAll();
        	   }*/
        	   	MessageSender.currentUser = (UserInfo)message.getBody();
        	    MessageSender.isOnline = true;
        	   /* UserInfo userinfo = (UserInfo)message.getBody();
		    	List<Friend> friends = userinfo.getFriends();
		    	if(friends!=null){
		    		System.out.println("friend list:");
		    		for(Friend friend:friends){
		    			String friendName= friend.getName();
		    			System.out.println(friendName);
		    		}
		    	}*/
		    	Platform.runLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(!MessageSender.isOnline)
						{
							try 
							{
								synchronized(MessageSender.isOnline)
								{
									MessageSender.isOnline.wait();
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(JJQQ.stage!=null)
						{
							MainWindow mainWindow = new MainWindow(MessageSender.currentUser);
							mainWindow.setVisible(true);
							mainWindow.addFriend(MessageSender.currentUser.getFriends());
							Scene scene = new Scene(mainWindow.getRoot());
							JJQQ.stage.setScene(scene);
						}
						
					}});
		    	
		    	//心跳包
		    	 heartBeat = ctx.executor().scheduleAtFixedRate(
		                    new LoginAuthReqHandler.HeartBeatTask(ctx), 0, 5000,
		                    TimeUnit.MILLISECONDS);
		            CacheCenter.getInstance().setCurrentUser(user);
		           
           }
        }
        else 
        {
            // 如果不是，直接透传给后面的ChannelHandler进行处理；
            ctx.fireChannelRead(msg);
        }
    }

    
	 private class HeartBeatTask implements Runnable 
	    {
	        private final ChannelHandlerContext ctx;

	        public HeartBeatTask(final ChannelHandlerContext ctx) {
	            this.ctx = ctx;
	        }

	        @Override
	        public void run()
	        {
	        	synchronized(timeoutCount){
	        	if(timeoutCount>5)
	        	{
	        		System.out.println("失去连接，请尝试重新连接");
	        		ctx.channel().close();
	        	}
	        	else
	        	{
		            Message heatBeat = buildHeatBeat();
		            ctx.writeAndFlush(heatBeat);
		            timeoutCount++;
	        	}
	            
	        	}
	        	
	        }

	        private Message buildHeatBeat() 
	        {
	            Message message = new Message();
	            Header header = new Header();
	            header.setType(MessageType.HEART_BEAT_MSG);
	            header.setSendTime(new Date().getTime());
	            message.setHeader(header);
	            message.setBody("ping");
	            return message;
	        }
	    }
	 
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.fireExceptionCaught(cause);
        System.out.println("失去连接了，请尝试重新连接");
    }

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		MessageSender.isConnected = false;
	}
    
}
