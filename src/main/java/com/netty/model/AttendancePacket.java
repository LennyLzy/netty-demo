package com.netty.model;

public class AttendancePacket<T extends CommandContent> {

    public static final int HEADER_LENGTH = 32;

    public static final int MIN_LENGTH = 34;

    public static final byte MSG_HEADER_FLAG = 0x01;

    private long length;

    private long partIndex;

    private long partCount;

    private byte version;

    private int command;

    private byte[] sessionID;

    private T content;

    private byte flag;

    public AttendancePacket(long length, long partIndex, long partCount, byte version, int command, byte[] sessionID, T content, byte flag) {
        this.length = length;
        this.partIndex = partIndex;
        this.partCount = partCount;
        this.version = version;
        this.command = command;
        this.sessionID = sessionID;
        this.content = content;
        this.flag = flag;
    }

    public AttendancePacket(long length, int command, byte[] sessionID, T content, byte flag) {
        new AttendancePacket(length, 0, 0, (byte) 1, command, sessionID, content, flag);
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getPartIndex() {
        return partIndex;
    }

    public void setPartIndex(long partIndex) {
        this.partIndex = partIndex;
    }

    public long getPartCount() {
        return partCount;
    }

    public void setPartCount(long partCount) {
        this.partCount = partCount;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public byte[] getSessionID() {
        return sessionID;
    }

    public void setSessionID(byte[] sessionID) {
        this.sessionID = sessionID;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }
}
