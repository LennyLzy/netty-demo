package com.example.demo.utils;

import com.sun.xml.internal.ws.api.message.Packet;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

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


    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i <bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();

    }

    public static byte[] hexStr2Byte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
            System.out.print(b + ",");
        }
        return bf.array();
    }

}
