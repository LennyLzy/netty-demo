package com.netty.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class HeartBeatRequest extends CommandContent {

    public static AttendancePacket<HeartBeatRequest> packet(String factoryCode, String deviceCode, byte[] sessionID) {
        return new AttendancePacket(0, 65535, sessionID, null, (byte) 0);
    }

    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
