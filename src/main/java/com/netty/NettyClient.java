package com.netty;

import com.netty.handler.AttendancePacketDecoder;
import com.netty.handler.AttendancePacketEncoder;
import com.netty.model.AttendancePacket;
import com.netty.model.HeartBeatRequest;
import com.netty.model.LoginRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Data
@EqualsAndHashCode
public class NettyClient {

    private final String host;
    private final int port;
    private EventLoopGroup group;
    private Channel ch;
    private ChannelPromise  promise;
    private AttendancePacket data;
    private ScheduledFuture heartBeatFuture;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
//        this.group = new NioEventLoopGroup();
    }

    public NettyClient connect() throws InterruptedException {
        if (this.group == null)
            this.group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("decoder", new AttendancePacketDecoder())
                                .addLast("encoder", new AttendancePacketEncoder())
                                .addLast(new SimpleChannelInboundHandler<AttendancePacket>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AttendancePacket attendancePacket) throws Exception {
                                        if (null != attendancePacket){
                                            data = attendancePacket;
                                            promise.setSuccess();
                                        }
                                    }
                                });
                    }
                });
        ChannelFuture f = b.connect().sync();
        this.ch = f.channel();
        return this;
    }

    public Object sendMsg(Object msg) throws InterruptedException {
        if (ch == null || !ch.isActive() || !ch.isOpen() || !ch.isRegistered()) {
            this.connect();
        }
        promise = ch.writeAndFlush(msg).channel().newPromise();
        promise.sync();
        return this.data;
    }

    public void startSendHeartBeat(String factoryCode, String deviceCode, byte[] sessionID) {
        ScheduledFuture future = this.ch.eventLoop().scheduleAtFixedRate(() -> {
            AttendancePacket packet = HeartBeatRequest.packet(factoryCode,deviceCode,sessionID);
            try {
                sendMsg(packet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 30, 30, TimeUnit.SECONDS);
        this.heartBeatFuture = future;
    }

    public void cancelSendHeartBeat() {
        this.heartBeatFuture.cancel(false);
    }

    public void close() throws InterruptedException {
        if (ch != null) {
            ch.closeFuture().sync();
        }
    }

    public void destroy() throws InterruptedException {
        this.group.shutdownGracefully().sync();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        NettyClient client = new NettyClient("127.0.0.1", 8911);
        NettyClient client = new NettyClient("157.122.146.230", 9720);
//        byte[] rawData = ByteUtils.hexStr2Byte("01410000000000000000000000014B031B00ED8B91D23143AFE845EAA4F7EFCF31323334353637383931323334353637313233343536373839313233343536374543314439343933344536393434454439303834424531313133433230395A48610001");
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer().writeBytes(rawData);
//        ByteBuf byteBuf = Unpooled.buffer().writeBytes(rawData);
        byte[] sessionID = {27, 0, -19, -117, -111, -46, 49, 67, -81, -24, 69, -22, -92, -9, -17, -49};
        LoginRequest request = new LoginRequest("I61lHvGy4IgURqk4HF2w3Dq7BYZafVVO", "I61lHvGy4IgURqk4HF2w3Dq7BYZafV66");
        AttendancePacket packet = new AttendancePacket(LoginRequest.CONTENT_LENGTH, LoginRequest.COMMAND_ID, sessionID, request, (byte) 0);
        client.connect();
        AttendancePacket response = (AttendancePacket)client.sendMsg(packet);
        System.out.println(response);
        client.destroy();
    }
}
