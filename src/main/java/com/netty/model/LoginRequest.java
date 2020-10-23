package com.netty.model;

import com.netty.utils.ByteUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginRequest extends CommandContent {

    private String factoryCode;

    private String deviceCode;

    private byte XOR;

    public LoginRequest(String factoryCode, String deviceCode) {
        this.factoryCode = factoryCode;
        this.deviceCode = deviceCode;
        this.XOR = ByteUtils.getXOR((this.factoryCode + this.deviceCode).getBytes());
    }

    public static AttendancePacket<LoginRequest> packet(String factoryCode, String deviceCode, byte[] sessionID) {
        LoginRequest content = new LoginRequest(factoryCode, deviceCode);
        return new AttendancePacket(65, 843, sessionID, content, (byte) 0);
    }

    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
