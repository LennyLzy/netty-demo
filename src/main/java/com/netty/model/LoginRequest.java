package com.netty.model;

import com.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginRequest extends CommandContent {

    public static final int COMMAND_ID = 843;

    public static final long CONTENT_LENGTH = 65;

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

    public static CommandContent ofByte(ByteBuf byteBuf, long length) {
        if (length > 0) {
            boolean flag = checkXOR(byteBuf, length);
            if (!flag) {
                return null;
            }
            byte[] temp = new byte[32];

            byteBuf.readBytes(temp);
            String factoryCode = new String(temp);

            byteBuf.readBytes(temp);
            String deviceCode = new String(temp);

            byte XOR = byteBuf.readByte();

            return new LoginRequest(factoryCode, deviceCode);
        }
        return null;
    }


    @Override
    public byte[] toByte() {
        return (this.factoryCode + this.deviceCode).getBytes();
    }

    @Override
    public Byte getXOR() {
        return this.XOR;
    }
}
