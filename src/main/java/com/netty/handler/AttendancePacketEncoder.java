package com.netty.handler;

import com.netty.model.AttendancePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class AttendancePacketEncoder extends MessageToByteEncoder<AttendancePacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AttendancePacket attendancePacket, ByteBuf byteBuf) throws Exception {

    }
}
