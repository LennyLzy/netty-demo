package com.netty.model;

import com.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GetPersonInfoRequest extends CommandContent {

    public static final int COMMAND_ID = 845;

    public static final long CONTENT_LENGTH = 51;

    private String deviceCode;

    private String identityNo;

    private byte XOR;

    public GetPersonInfoRequest(String deviceCode, String identityNo) {
        this.deviceCode = deviceCode;
        this.identityNo = identityNo;
        this.XOR = ByteUtils.getXOR((this.deviceCode + this.identityNo).getBytes());
    }

    public static AttendancePacket<GetPersonInfoRequest> packet(String deviceCode, String identityNo, byte[] sessionID) {
        GetPersonInfoRequest content = new GetPersonInfoRequest(deviceCode, identityNo);
        return new AttendancePacket(51, 845, sessionID, content, (byte) 0);
    }

    @Override
    public byte[] toByte() {
        return (this.deviceCode + this.identityNo).getBytes();
    }

    @Override
    public Byte getXOR() {
        return this.XOR;
    }
}
