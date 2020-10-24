package com.netty;

import com.example.demo.handler.TestClientHandler;
import com.example.demo.utils.PacketUtil;
import com.netty.handler.AttendancePacketDecoder;
import com.netty.handler.AttendancePacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
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
    private EventLoopGroup group;
    private Channel ch;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.group = new NioEventLoopGroup();
    }

    public NettyClient connect() throws InterruptedException {
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addFirst(new AttendancePacketDecoder())
                                    .addLast(new AttendancePacketEncoder());
                    }
                });
        ChannelFuture f = b.connect().sync();
        this.ch = f.channel();
        return this;
    }

    public ChannelFuture sendMsg(Object msg) throws InterruptedException {
        if (ch == null || !ch.isActive() || !ch.isOpen() || !ch.isRegistered()) {
            this.connect();
        }
        return ch.writeAndFlush(msg).sync();
    }

    public void close() throws InterruptedException {
        if (ch != null) {
            ch.closeFuture().sync();
        }
    }

    public void destroy() throws InterruptedException {
        this.group.shutdownGracefully().sync();
    }

    public static void main(String[] args) throws InterruptedException {
//        NettyClient client = new NettyClient("127.0.0.1", 8911);
        NettyClient client = new NettyClient("157.122.146.230", 9720);
        byte[] rawData = PacketUtil.hexStr2Byte("01410000000000000000000000014B031B00ED8B91D23143AFE845EAA4F7EFCF31323334353637383931323334353637313233343536373839313233343536374543314439343933344536393434454439303834424531313133433230395A48610001");
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer().writeBytes(rawData);
        ByteBuf byteBuf = Unpooled.buffer().writeBytes(rawData);
        ChannelFuture cf = client.connect().sendMsg(byteBuf);
        cf.channel().closeFuture().sync();
        client.destroy();
    }
}
