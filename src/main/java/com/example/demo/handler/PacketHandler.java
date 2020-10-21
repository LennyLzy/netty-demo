package com.example.demo.handler;

import com.example.demo.packet.MyPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PacketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        MyPacket packet = (MyPacket) msg;
        System.out.println("接收到:" + packet.toString());
    }

}
