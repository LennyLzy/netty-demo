package com.netty.handler;

import com.netty.model.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class AttendancePacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 读取可读长度是否大于考勤数据包长度
        if (byteBuf.readableBytes() >= AttendancePacket.MIN_LENGTH) {
            while (true) {
                // 打个标记，用于在发现实际数据流长度小于头中的 len 时，回溯 readerIndex
                byteBuf.markReaderIndex();
                byte header = byteBuf.readByte();
                // 识别到包头标记位时退出循环
                if (header == AttendancePacket.MSG_HEADER_FLAG) {
                    break;
                }
                // 否则回溯 readerIndex，并一个一个字节地读，直到读到包头标记位
                byteBuf.resetReaderIndex();
                byteBuf.readByte();
                // 当跳过一个字节后，剩余字节数小于 MIN_LEN 时，可以直接return，等待下一次 decode 操作了
                if (byteBuf.readableBytes() < AttendancePacket.MIN_LENGTH) {
                    return;
                }
            }
            // 获取包消息内容体长度
            int len = (int) byteBuf.readUnsignedIntLE();
            // 减掉头标记位1位和内容长度4位，共5位
            // 判断数据包是否已经按照协议到齐了，若没到齐，还原 readerIndex 并等待下一次 decode
            if (byteBuf.readableBytes() < len + AttendancePacket.MIN_LENGTH - 5) {
                byteBuf.resetReaderIndex();
                return;
            }
            // 包已按照协议长度完整接收，读取数据流解析为对象
            byteBuf.resetReaderIndex();
            AttendancePacket packet = decodeAttendancePacket(byteBuf);
            System.out.println("接收到:" + packet.toString());
            list.add(packet);
        }
    }

    // 解析字节流为对象
    private AttendancePacket decodeAttendancePacket(ByteBuf byteBuf) {
        // 根据协议解析流
        // 读1位包头标记位
        byteBuf.readByte();
        // 读4位内容体长度
        long length = byteBuf.readUnsignedIntLE();
        // 读4位分包索引
        long partIndex = byteBuf.readUnsignedIntLE();
        // 读4位分包数
        long partCount = byteBuf.readUnsignedIntLE();
        // 读1位版本号
        byte version = byteBuf.readByte();
        // 读2位命令号
        int command = byteBuf.readUnsignedShortLE();
        // 读16位sessionID
        byte[] sessionID = new byte[16];
        byteBuf.readBytes(sessionID);
        // 读取包内容
        CommandContent content = null;
        try {
            content = decodeCommandContent(byteBuf, command, length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte flag = byteBuf.readByte();
        byteBuf.readByte();
        return new AttendancePacket(length,command,sessionID,content,flag);
    }

    private CommandContent decodeCommandContent(ByteBuf byteBuf, int command, long length) throws UnsupportedEncodingException {
        if (command == LoginRequest.COMMAND_ID) {
            return LoginRequest.ofByte(byteBuf, length);
        }
        if (command == HeartBeatRequest.COMMAND_ID) {
            return null;
        }
        if (command == PersonInfoResponse.COMMAND_ID) {
            return PersonInfoResponse.ofByte(byteBuf, length);
        }
        if (command == UploadAttendanceRequest.COMMAND_ID) {
            return UploadAttendanceRequest.ofByte(byteBuf, length);
        }
        return null;
    }

}
