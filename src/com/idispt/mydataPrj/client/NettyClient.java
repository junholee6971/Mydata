package com.idispt.mydataPrj.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * NettyClient
 * @author psw
 */
public class NettyClient {
	private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
	
	private Bootstrap bs = new Bootstrap();
	private SocketAddress addr_;
	private byte[] msgArr;
	
	public NettyClient(SocketAddress addr, byte[] msgArr) {
		this.addr_ = addr;
		this.msgArr = msgArr;
	}
	
	public NettyClient(String host, int port, byte[] msgArr) {
		this(new InetSocketAddress(host, port), msgArr);
	}
	
	//실제로 동작시킬 메소드 Bootstrap 연결 옵션 설정 및 연결 처리
	public void run() {
		if(this.addr_ == null) {
			logger.error("주소 정보가 없습니다.");
		}else if(this.msgArr == null || this.msgArr.length == 0) {
			logger.error("보낼 메시지가 없습니다.");
		}
		
		bs.group(new NioEventLoopGroup(3))
		.channel(NioSocketChannel.class)
		.option(ChannelOption.SO_KEEPALIVE, true)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("clientHandler", new NettyClientHandler(msgArr));
			}
		});
		
		ChannelFuture f = bs.connect(addr_);
		f.channel();
		logger.info(addr_ + " connect()");
	}
}