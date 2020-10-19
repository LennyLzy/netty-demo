package com.example.demo.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MyPacket {

    private byte header;

    private int length;

    private int partIndex;

    private int partCount;

    private byte version;

    private short command;

    private byte[] sessionID;

    private byte[] content;

    private byte flag;

    private byte tail;

}
