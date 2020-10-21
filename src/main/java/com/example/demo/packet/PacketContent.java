package com.example.demo.packet;

import com.example.demo.utils.PacketUtil;
import io.netty.buffer.ByteBuf;

public abstract class PacketContent {

    public static final int COMMAND_LOGIN = 843;

    public static final int COMMAND_HEART_BEAT = 65535;

    public static final int COMMAND_PERSON_INFO = 845;

    public static final int COMMAND_ON_DUTY = 848;


    protected static boolean checkXOR(ByteBuf byteBuf, int len){
        // 验证校验和
        byte rawXOR = byteBuf.getByte(MyPacket.HEADER_LENGTH + len - 1);
        byte[] contentByte = new byte[len - 1];
        byteBuf.getBytes(MyPacket.HEADER_LENGTH, contentByte);

        if (PacketUtil.getXOR(contentByte) != rawXOR) {
            byteBuf.skipBytes(len + 2);
            return false;
        }
        return true;
    }

}
