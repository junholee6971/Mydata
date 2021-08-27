package com.idispt.mydataPrj.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
	
	private byte[] msgArr;
	private int idx = 0;
	public NettyClientHandler(byte[] msgArr) {
		this.msgArr = msgArr;
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("채널 등록");
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("채널 연결이 종료됨.");
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("채널이 메시지 발송할 준비가 됨.");
		sendMsg(ctx, this.idx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		logger.info("메시지를 받는 메소드.");
		ByteBuf buf = (ByteBuf)msg;
		int n = buf.readableBytes();
		if( n > 0 ) {
			byte[] b = new byte[n];
			buf.readBytes(b);
			//수신메시지 출력
			String receiveMsg = new String( b );
			logger.info("수신된 메시지 >" + receiveMsg);
			
			//보낼 메시지가 없으면 연결 종료
			if(msgArr.length ==  ++this.idx) {
				channelClose(ctx);
			}else {
				sendMsg(ctx, this.idx);
			}
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		logger.info("메시지를 받는 동작이 끝나면 동작하는 메소드.");
	}
	
	private void sendMsg(ChannelHandlerContext ctx, int i) {
		byte[] msg = msgArr;
		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(msg);
		ctx.writeAndFlush( messageBuffer ); //메시지를 발송하고 flush처리
		logger.info("발송 메시지 >" + msg);
	}
	
	private void channelClose(ChannelHandlerContext ctx) {
		logger.error("채널 연결 종료");
		ctx.close();
	}
}