package com.netty.model;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ExceptionContent extends CommandContent {

    private String errMessage;

    public ExceptionContent(String errMessage) {
        this.errMessage = errMessage;
    }

    public static ExceptionContent ofByte(ByteBuf byteBuf, long len) {
        byte[] bytes = new byte[(int)len];
        byteBuf.readBytes(bytes);
        String msg = new String(bytes);
        return new ExceptionContent(msg);
    }

    @Override
    public byte[] toByte() {
        return null;
    }

    @Override
    public Byte getXOR() {
        return null;
    }
}
