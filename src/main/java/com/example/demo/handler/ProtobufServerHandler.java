package com.example.demo.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        SubscribeReqProto.SubscribeReq req=createSubscribeReq();

//        MessageProto.Message message = (MessageProto.Message) msg;
//        if (ConnectionPool.getChannel(message.getId()) == null) {
//            ConnectionPool.putChannel(message.getId(), ctx);
//        }
//        System.err.println("server:" + message.getId());
//        ctx.writeAndFlush(message);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
