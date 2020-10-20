package com.example.demo.utils;

import com.sun.xml.internal.ws.api.message.Packet;
import io.netty.buffer.ByteBuf;

public class PacketUtil {
    public static ByteBuf encode(Packet packet) {return null;}

    public static Packet decode(ByteBuf byteBuf) {return null;}

    public static byte getXOR(byte[] bytes){
        byte temp = bytes[0];
        for (int i = 1; i < bytes.length; i++) {

            temp ^= bytes[i];
        }
        return temp;
    }
}
