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

        Session session = new Session();
        session.setCtx(ctx);
        MessageSender.setSession(session);
        synchronized(MessageSender.sessionSignal)
		{
				MessageSender.sessionSignal.notifyAll();
			
		}

    }


	 @Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP) 
        {
            UserInfo user = (UserInfo) message.getBody();
            if (user==null)
            {
                ctx.close();
            } 
           else 
           {

        	   	MessageSender.currentUser = (UserInfo)message.getBody();
        	    MessageSender.isOnline = true;
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
		    	

		    	 heartBeat = ctx.executor().scheduleAtFixedRate(
		                    new LoginAuthReqHandler.HeartBeatTask(ctx), 0, 5000,
		                    TimeUnit.MILLISECONDS);
		            CacheCenter.getInstance().setCurrentUser(user);
		           
           }
        }
        else 
        {
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
	        		System.out.println("lose connected");
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
        System.out.println("lose connected");
    }

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		MessageSender.isConnected = false;
	}
    
}
