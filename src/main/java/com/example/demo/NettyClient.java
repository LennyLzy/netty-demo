package com.example.demo;

import com.example.demo.utils.PacketUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NettyClient {

    private final String host;

    private final int port;

    private ChannelFuture cf;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast();
                        }
                    });
            ChannelFuture f = b.connect().sync();
            cf = f;
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public void sendMsg(Object msg) {
        this.cf.channel().writeAndFlush(msg);
    }

    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1", 8911);
        client.start();
        byte[] rawData = PacketUtil.hexStr2Byte("01410000000000000000000000014B031B00ED8B91D23143AFE845EAA4F7EFCF31323334353637383931323334353637313233343536373839313233343536374543314439343933344536393434454439303834424531313133433230395A48610001");
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer().writeBytes(rawData);
        client.sendMsg(byteBuf);
    }

}
