package com.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@Data
@EqualsAndHashCode
public class PersonInfoResponse extends CommandContent {

    public static final int COMMAND_ID = 845;

    private long workerId;

    private String name;

    private String identityNo;

    private byte nation;

    private String sex;

    private String address;

    private String birth;

    private String organization;

    private String periodOfValidity;

    private long collectedPhotoLen;

    private byte[] collectedPhoto;

    private long identityCardPhotoLen;

    private byte[] identityCardPhoto;

    private long infraredPhotoLen;

    private byte[] infraredPhoto;

    private byte XOR;

    public PersonInfoResponse(long workerId, String name, String identityNo, byte nation, String sex, String address, String birth, String organization, String periodOfValidity, long collectedPhotoLen, byte[] collectedPhoto, long identityCardPhotoLen, byte[] identityCardPhoto, long infraredPhotoLen, byte[] infraredPhoto, byte XOR) {
        this.workerId = workerId;
        this.name = name;
        this.identityNo = identityNo;
        this.nation = nation;
        this.sex = sex;
        this.address = address;
        this.birth = birth;
        this.organization = organization;
        this.periodOfValidity = periodOfValidity;
        this.collectedPhotoLen = collectedPhotoLen;
        this.collectedPhoto = collectedPhoto;
        this.identityCardPhotoLen = identityCardPhotoLen;
        this.identityCardPhoto = identityCardPhoto;
        this.infraredPhotoLen = infraredPhotoLen;
        this.infraredPhoto = infraredPhoto;
        this.XOR = XOR;
    }

    public static CommandContent ofByte(ByteBuf byteBuf, long length) throws UnsupportedEncodingException {
        if (length > 0) {
            boolean flag = checkXOR(byteBuf, length);
            if (!flag) {
                byteBuf.skipBytes((int) length + 2);
                return null;
            }
            long workerId = byteBuf.readUnsignedIntLE();
            String name = byteBuf.readBytes(30).toString(Charset.forName("UTF-8"));
            String identityNo = byteBuf.readBytes(18).toString(Charset.forName("UTF-8"));
            byte nation = byteBuf.readByte();
            String sex = byteBuf.readBytes(2).toString(Charset.forName("UTF-8"));
            String address = byteBuf.readBytes(140).toString(Charset.forName("UTF-8"));
            String birth = byteBuf.readBytes(16).toString(Charset.forName("UTF-8"));
            String organization = byteBuf.readBytes(60).toString(Charset.forName("UTF-8"));
            String periodOfValidity = byteBuf.readBytes(64).toString(Charset.forName("UTF-8"));
            long collectedPhotoLen = byteBuf.readUnsignedIntLE();
            byte[] collectedPhoto = new byte[(int) collectedPhotoLen];
            byteBuf.readBytes(collectedPhoto);
            long identityCardPhotoLen = byteBuf.readUnsignedIntLE();
            byte[] identityCardPhoto = new byte[(int) identityCardPhotoLen];
            byteBuf.readBytes(identityCardPhoto);
            long infraredPhotoLen = byteBuf.readUnsignedIntLE();
            byte[] infraredPhoto = new byte[(int) infraredPhotoLen];
            byteBuf.readBytes(infraredPhoto);
            byte XOR = byteBuf.readByte();

            return new PersonInfoResponse(workerId, name, identityNo, nation,
                    sex, address, birth, organization, periodOfValidity,
                    collectedPhotoLen, collectedPhoto, identityCardPhotoLen,
                    identityCardPhoto, infraredPhotoLen, infraredPhoto, XOR);
        }
        return null;
    }

    @Override
    public byte[] toByte() {
        return null;
    }

    @Override
    public Byte getXOR() {
        return this.XOR;
    }
}
