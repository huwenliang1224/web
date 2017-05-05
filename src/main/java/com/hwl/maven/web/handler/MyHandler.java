package com.hwl.maven.web.handler;

import com.hwl.maven.web.Application;
import com.hwl.maven.web.bean.MysqlManager;
import com.hwl.maven.web.bean.StartUpManager;
import com.hwl.maven.web.log.InfoLog;
import com.hwl.maven.web.bean.HttpManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by huwenl on 2017/4/28.
 */
public class MyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public static Logger logger = LoggerFactory.getLogger(MyHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        HttpManager.sendContent(ctx, MysqlManager.lookup().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            InfoLog.info(Application.flg + ":" + cause.toString());
            HttpManager.sendContent(ctx, Application.flg + ":" + cause.toString());
        }
    }
}
