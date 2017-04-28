package com.hwl.maven.web;

import com.hwl.maven.web.bean.StartUpManager;
import com.hwl.maven.web.bean.StartUpParam;
import com.hwl.maven.web.handler.MyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by huwenl on 2017/4/28.
 */
public class Application {
    public static void main(String[] args) {
        try {
            StartUpManager.initStartupParam();
            startapp();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void startapp() throws Exception {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        Class channelClass = null;
        if (Epoll.isAvailable()) {
            bossGroup = new EpollEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
            workerGroup = new EpollEventLoopGroup(10);
            channelClass = EpollServerSocketChannel.class;
            System.out.println("use Epoll");
        } else {
            bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
            workerGroup = new NioEventLoopGroup(10);
            channelClass = NioServerSocketChannel.class;
            System.out.println("use Nio");
        }

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.channel(channelClass);
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.group(bossGroup, workerGroup).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    p.addLast(new HttpServerCodec());
                    p.addLast(new HttpObjectAggregator(1024));
                    p.addLast(new MyHandler());
                }
            });
            Channel ch = b.bind(StartUpParam.port).sync().channel();
            System.out.println("system start up");
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
