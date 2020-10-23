package com.netty.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PersonInfoResponse  extends CommandContent {
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

    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
