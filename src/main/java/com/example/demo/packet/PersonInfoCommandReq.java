package com.example.demo.packet;

import com.example.demo.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PersonInfoCommandReq extends PacketContent {

    private String deviceCode;

    private String identityNo;

    private byte XOR;

    public PersonInfoCommandReq(String deviceCode, String identityNo, byte XOR) {
        this.deviceCode = deviceCode;
        this.identityNo = identityNo;
        this.XOR = XOR;
    }


    public static PersonInfoCommandReq ofByte(ByteBuf byteBuf, int len) {
        // 验证校验和
        boolean flag = checkXOR(byteBuf,len);

        if (!flag){
            return null;
        }

        byte[] temp = new byte[32];

        byteBuf.readBytes(temp);
        String deviceCode = new String(temp);

        byteBuf.readBytes(temp, 0, 18);
        String identityNo = new String(temp);

        byte XOR = byteBuf.readByte();

        return new PersonInfoCommandReq(deviceCode, identityNo, XOR);
    }
}
