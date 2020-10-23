package com.netty.model;

import com.netty.utils.ByteUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GetPersonInfoRequest extends CommandContent {

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
        return new byte[0];
    }
}
