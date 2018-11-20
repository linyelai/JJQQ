package com.linseven.server;

import java.io.IOException;
import com.linseven.constant.NettyConstant;
import com.linseven.handler.ChatMsgHandler;
import com.linseven.handler.HeartBeatRespHandler;
import com.linseven.handler.LoginAuthRespHandler;
import com.linseven.handler.MessageDecoder;
import com.linseven.handler.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyServer {

    public void bind() throws Exception 
    {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer() 
                {
                    @Override
                    public void initChannel(Channel ch)throws IOException{
                        ch.pipeline().addLast(new MessageDecoder(1024 * 1024, 4, 4));
                        ch.pipeline().addLast(new MessageEncoder());
                       // ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                        ch.pipeline().addLast(new LoginAuthRespHandler());
                        ch.pipeline().addLast("HeartBeatHandler",new HeartBeatRespHandler());
                        ch.pipeline().addLast("ChatMsgHandler",new ChatMsgHandler());
                    }
                });

        // 绑定端口，同步等待成功
        b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
        System.out.println("Netty server start ok : " + (NettyConstant.REMOTEIP + " : " + NettyConstant.PORT));
    }

    public static void main(String[] args) throws Exception
    {
        new NettyServer().bind();
    }
}