package com.example.demo.packet;

import com.example.demo.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OnDutyCommandReq extends PacketContent {

    private long workerId;

    private String time;

    private byte type;

    private int len;

    private byte[] image;

    private byte XOR;

    public OnDutyCommandReq(long workerId, String time, byte type, int len, byte[] image, byte XOR) {
        this.workerId = workerId;
        this.time = time;
        this.type = type;
        this.len = len;
        this.image = image;
        this.XOR = XOR;
    }

    public static OnDutyCommandReq ofByte(ByteBuf byteBuf, int len) {
        // 验证校验和
        boolean flag = checkXOR(byteBuf, len);

        if (!flag) {
            return null;
        }

        byte[] temp = new byte[32];

        long workerId = byteBuf.readUnsignedIntLE();

        byteBuf.readBytes(temp, 0, 7);
        String time = PacketUtil.bcd2Str(temp);

        byte type = byteBuf.readByte();

        int length = (int) byteBuf.readUnsignedIntLE();

        byte[] image = new byte[length];

        byte XOR = byteBuf.readByte();

        return new OnDutyCommandReq(workerId, time, type, length, image, XOR);
    }

}
