package com.example.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                // 责任链模式，添加第一次连接的客户端处理逻辑
//                ch.pipeline().addLast(new FirstClientHandler());
            }
        });

        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
//        LoginPacket packet = new LoginPacket();
//        packet.setUserId(123l);
//        packet.setName("ARong");
//        packet.setPassword("123");
//        ByteBuf byteBuf = PacketUtil.encode(packet);
//        System.out.println("客户端正在发送包");
//        channel.writeAndFlush(byteBuf);
    }

}
