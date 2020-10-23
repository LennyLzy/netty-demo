package com.example.demo.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MyPacket<T extends PacketContent> {

    public static final int HEADER_LENGTH = 32;

    public static final int MIN_LENGTH = 34;

    private long length;

    private long partIndex;

    private long partCount;

    private byte version;

    private int command;

    private byte[] sessionID;

    private T content;

    private byte flag;


    public MyPacket(long length, long partIndex, long partCount, byte version, int command, byte[] sessionID, T content, byte flag) {
        this.length = length;
        this.partIndex = partIndex;
        this.partCount = partCount;
        this.version = version;
        this.command = command;
        this.sessionID = sessionID;
        this.content = content;
        this.flag = flag;
    }


}
