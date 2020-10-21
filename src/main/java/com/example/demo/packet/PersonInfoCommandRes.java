package com.example.demo.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PersonInfoCommandRes {

    private long workerId;

    private String name;

    private String identityNo;

    private byte nation;

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

}
