package com.netty.model;

import com.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;

public abstract class CommandContent {

    // 验证校验和
    protected static boolean checkXOR(ByteBuf byteBuf, long len){
        byte rawXOR = byteBuf.getByte((int)len - 1);
        byte[] contentByte = new byte[(int)len - 1];
        byteBuf.getBytes(0, contentByte);

        if (ByteUtils.getXOR(contentByte) != rawXOR) {
            return false;
        }
        return true;
    }

    public abstract byte[] toByte();

    public abstract Byte getXOR();
}
