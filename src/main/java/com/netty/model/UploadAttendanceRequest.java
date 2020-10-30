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

    public static final int COMMAND_ID = 848;

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

    public static CommandContent ofByte(ByteBuf byteBuf, long length) {
        if (length > 0) {
            boolean flag = checkXOR(byteBuf, length);
            if (!flag) {
                byteBuf.skipBytes((int) length + 2);
                return null;
            }
        }
        return null;
    }

    @Override
    public byte[] toByte() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeLongLE(this.workerId);
        byteBuf.writerIndex(byteBuf.writerIndex() - 4);
        byteBuf.writeBytes(ByteUtils.StrToBCDBytes(this.time));
        byteBuf.writeByte(this.type);
        byteBuf.writeLongLE(this.imageLength);
        byteBuf.writerIndex(byteBuf.writerIndex() - 4);
        byteBuf.writeBytes(this.image);
        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(result);
        return result;
    }

    @Override
    public Byte getXOR() {
        return this.XOR;
    }
}
