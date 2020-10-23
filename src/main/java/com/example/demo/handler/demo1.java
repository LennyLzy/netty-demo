package com.example.demo.handler;

import com.example.demo.utils.PacketUtil;

public class demo1 {
    public static void main(String[] args) {
        byte[] bytes = new byte[]{31,32,33,34,35,36,37,38,39,31,32,33,34,35,36,37,
                31,32,33,34,35,36,37,38,39,31,32,33,34,35,36,37,
                41,44,46,44,39,35,41,34,42,33,36,33,34,42,35,38,
                42,30,43,46,33,42,38,43,36,37,42,31,38,41,32,39};
        PacketUtil.getXOR(bytes);
    }
}
