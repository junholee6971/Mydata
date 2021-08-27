package com.idispt.mydataPrj.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idispt.mydataPrj.BranchProc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class MydataServerHandler extends ChannelInboundHandlerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(MydataServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //String host = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
        //int port = ((InetSocketAddress)ctx.channel().remoteAddress()).getPort();
        //logger.debug(String.format("host:%s", host));
        
        ByteBuf in = (ByteBuf)msg;
        String strMsg = in.toString(CharsetUtil.UTF_8);
        
        logger.info("Server received : " + strMsg);
        BranchProc proc = new BranchProc();
        byte[] rtn = proc.branchDetermin(strMsg);
           
        if (rtn != null) {
            logger.info(new String(rtn));
            ByteBuf buf = Unpooled.copiedBuffer(rtn);
            ctx.write(buf);
        }
        //ctx.write(in); // 받은 메시지를 발신자에게로 Echo 시킨다.
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("Read Complete");

        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // 대기중인 메시지를 플러시하고 채널을 닫음
                .addListener(ChannelFutureListener.CLOSE);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();    // 채널 닫기
    }
}