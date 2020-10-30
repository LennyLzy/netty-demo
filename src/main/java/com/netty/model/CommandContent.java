package com.netty.model;

import com.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;

public abstract class CommandContent {

    // 验证校验和
    protected static boolean checkXOR(ByteBuf byteBuf, long len){
        byte rawXOR = byteBuf.getByte(AttendancePacket.HEADER_LENGTH + (int)len - 1);
        byte[] contentByte = new byte[(int)len - 1];
        byteBuf.getBytes(AttendancePacket.HEADER_LENGTH, contentByte);

        if (ByteUtils.getXOR(contentByte) != rawXOR) {
            return false;
        }
        return true;
    }

    public abstract byte[] toByte();

    public abstract Byte getXOR();
}
