package com.example.demo.packet;

import com.example.demo.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginCommandReq extends PacketContent {

    private String factoryCode;

    private String deviceCode;

    private byte XOR;

    public LoginCommandReq(String factoryCode, String deviceCode, byte XOR) {
        this.factoryCode = factoryCode;
        this.deviceCode = deviceCode;
        this.XOR = XOR;
    }


    public static LoginCommandReq ofByte(ByteBuf byteBuf, int len) {
        // 验证校验和
        boolean flag = checkXOR(byteBuf,len);

        if (!flag){
            return null;
        }

        byte[] temp = new byte[32];

        byteBuf.readBytes(temp);
        String factoryCode = new String(temp);

        byteBuf.readBytes(temp);
        String deviceCode = new String(temp);

        byte XOR = byteBuf.readByte();

        return new LoginCommandReq(factoryCode, deviceCode, XOR);
    }

}
