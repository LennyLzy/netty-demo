package com.netty.model;

import com.netty.utils.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UploadAttendanceRequest extends CommandContent {

    private long workerId;

    private String time;

    private byte type;

    private long imageLength;

    private byte[] image;

    private byte XOR;

    public UploadAttendanceRequest(long workerId, String time, long imageLength, byte[] image) {
        this.workerId = workerId;
        this.time = time;
        this.type = (byte) 6;   // 协议定义6为人脸识别模式
        this.imageLength = imageLength;
        this.image = image;
        this.XOR = ByteUtils.getXOR(this.toByte());
    }

    @Override
    public byte[] toByte() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeLongLE(this.workerId).writerIndex(-4);
        byteBuf.writeBytes(ByteUtils.StrToBCDBytes(this.time));
        byteBuf.writeByte(this.type);
        byteBuf.writeLongLE(this.imageLength).writerIndex(-4);
        byteBuf.writeBytes(this.image);
        return byteBuf.array();
    }
}
