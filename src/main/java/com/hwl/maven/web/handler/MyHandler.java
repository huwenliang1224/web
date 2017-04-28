package com.hwl.maven.web.handler;

import com.hwl.maven.web.bean.HttpManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.net.InetAddress;

/**
 * Created by huwenl on 2017/4/28.
 */
public class MyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        HttpManager.sendContent(ctx, addr.getHostName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            HttpManager.sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
