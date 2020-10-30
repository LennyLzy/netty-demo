package com.netty.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ByteUtils {

    /**
     * 计算校验和
     * @param bytes
     * @return
     */
    public static byte getXOR(byte[] bytes){
        byte temp = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            temp ^= bytes[i];
        }
        return temp;
    }

    /**
     * 字节数组以BCD解码为字符串
     * @param bytes
     * @return
     */
    public static String BCDBytesToString(byte[] bytes){
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i <bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * 字符串BCD编码为字节数组
     * @param s
     * @return
     */
    public static byte [] StrToBCDBytes(String s)
    {

        if(s.length () % 2 != 0)
        {
            s = "0" + s;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        char [] cs = s.toCharArray ();
        for (int i = 0;i < cs.length;i += 2)
        {
            int high = cs [i] - 48;
            int low = cs [i + 1] - 48;
            baos.write (high << 4 | low);
        }
        return baos.toByteArray ();
    }


    /**
     * 16进制字符串转字节数组
     * @param hex
     * @return
     */
    public static byte[] hexStrToByte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

    /**
     * 字节数组以ASCII码转字符串
     * @param bytes
     * @return
     */
    public static String byteToString(byte[] bytes){
        return new String(bytes);
    }

    /**
     * 字符串转ASCII码字节数组
     * @param str
     * @return
     */
    public static byte[] stringToByte(String str){
        return str.getBytes();
    }

    /**
     * 字节数组以UTF-8转为字符串
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String byteToUTF8(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes,"UTF-8");
    }

}
