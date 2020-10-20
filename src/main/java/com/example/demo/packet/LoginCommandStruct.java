package com.example.demo.packet;

import com.example.demo.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginCommandStruct extends PacketContent {

    private String factoryCode;

    private String deviceCode;

    private byte XOR;

    public LoginCommandStruct(byte[] factoryCodeByte, byte[] deviceCode, byte[] xor) {
        super();
        this.factoryCode = new String(factoryCodeByte);
        this.deviceCode = new String(deviceCode);
        this.XOR = xor[0];
    }

    public static LoginCommandStruct ofByte(ByteBuf byteBuf) {

        // 读取包
        byte[] factoryCodeByte = new byte[32];
        byteBuf.readBytes(factoryCodeByte);
        byte[] deviceCode = new byte[32];
        byteBuf.readBytes(deviceCode);
        byte[] xor = new byte[1];
        byteBuf.readBytes(xor);

        // 计算校验和
        byte[] temp = new byte[factoryCodeByte.length+deviceCode.length];
        System.arraycopy(factoryCodeByte, 0, temp, 0, factoryCodeByte.length);
        System.arraycopy(deviceCode, 0, temp, factoryCodeByte.length, deviceCode.length);

        // 校验不通过舍弃该包,跳过包体剩余的2字节
        if (PacketUtil.getXOR(temp) != xor[0]) {
            byteBuf.skipBytes(2);
            return null;
        }

        return new LoginCommandStruct(factoryCodeByte, deviceCode, xor);
    }

}
