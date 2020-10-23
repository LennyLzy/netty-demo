package com.example.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class demo {
    public static void main(String[] args) throws Exception {
//        byte[] rawData = hexStr2Byte("E99D9EE69CACE7B3BBE7BB9FE9A1B9E79BAE");
//        System.out.println(new String(rawData, "UTF-8"));
//        byte[] rawData = hexStr2Byte("01410000000000000000000000014B031B00ED8B91D23143AFE845EAA4F7EFCF31323334353637383931323334353637313233343536373839313233343536374543314439343933344536393434454439303834424531313133433230395A48610001");
//        System.out.println();
//        Byte2MessageDecoder decoder = new Byte2MessageDecoder();
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer().writeBytes(rawData);
//        decoder.decode(null, byteBuf, new ArrayList<>());
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
