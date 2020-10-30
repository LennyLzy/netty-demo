package com.netty.handler;

import com.netty.model.AttendancePacket;
import com.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class AttendancePacketEncoder extends MessageToByteEncoder<AttendancePacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AttendancePacket attendancePacket, ByteBuf byteBuf) throws Exception {
        System.out.println(attendancePacket.toString());
        ByteBuf target = Unpooled.buffer((int) (AttendancePacket.MIN_LENGTH + attendancePacket.getLength()));
        target.writeByte(AttendancePacket.MSG_HEADER_FLAG);
        target.writeLongLE(attendancePacket.getLength());
        target.writerIndex(target.writerIndex() - 4);
        target.writeLongLE(attendancePacket.getPartIndex());
        target.writerIndex(target.writerIndex() - 4);
        target.writeLongLE(attendancePacket.getPartCount());
        target.writerIndex(target.writerIndex() - 4);
        target.writeByte(attendancePacket.getVersion());
        target.writeShortLE(attendancePacket.getCommand());
        target.writeBytes(attendancePacket.getSessionID());
        target.writeBytes(attendancePacket.getContent().toByte());
        target.writeByte(attendancePacket.getContent().getXOR());
        target.writeByte(attendancePacket.getFlag());
        target.writeByte(AttendancePacket.MSG_HEADER_FLAG);
        byte[] result = new byte[target.readableBytes()];
        target.readBytes(result);
        byteBuf.writeBytes(result);
    }

}
