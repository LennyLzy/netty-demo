package com.example.demo.handler;

import com.example.demo.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class Byte2MessageDecoder extends ByteToMessageDecoder {

    public final int MIN_LEN = 34;

    public static final byte MSG_HEADER_FLAG = 0x01;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >= MIN_LEN) {
            while (true) {
                byteBuf.markReaderIndex();  // 打个标记，用于在发现实际数据流长度小于头中的 len 时，回溯 readerIndex
                byte header = byteBuf.readByte();
                if (header == MSG_HEADER_FLAG) {
                    break;
                }
                // 否则回溯 readerIndex，并一个一个字节跳过
                byteBuf.resetReaderIndex();
                byteBuf.readByte();
                // 当跳过一个字节后，剩余字节数小于 MIN_LEN 时，可以直接return，等待下一次 decode 操作了
                if (byteBuf.readableBytes() < MIN_LEN) {
                    return;
                }
            }
            // 获取消息长度，并判断数据包是否已经按照协议到齐了，若没到齐，还原 readerIndex 并等待下一次 decode
            int len = (int) byteBuf.readUnsignedIntLE();
            if (byteBuf.readableBytes() < len + MIN_LEN - 5) {
                byteBuf.resetReaderIndex();
                return;
            }
            // 读取数据流
            byteBuf.resetReaderIndex();
            MyPacket packet = readPacketFromBytes(byteBuf);
//            System.out.println("接收到:" + packet.toString());
            list.add(packet);
        }
    }


    private MyPacket readPacketFromBytes(ByteBuf byteBuf) {
        // 读byte流获取包头
        byteBuf.readByte();
        int length = (int)byteBuf.readUnsignedIntLE();
        int partIndex = (int)byteBuf.readUnsignedIntLE();
        int partCount = (int)byteBuf.readUnsignedIntLE();
        byte version = byteBuf.readByte();
        int command = byteBuf.readUnsignedShortLE();
        byte[] sessionID = new byte[16];
        byteBuf.readBytes(sessionID);
        // 获取包体
        PacketContent packetContent;
        packetContent = getPacketContent(byteBuf, command, length);

        byte flag = byteBuf.readByte();
        byteBuf.readByte();

        // 构造包对象
        MyPacket packet = new MyPacket(length, partIndex, partCount, version, command, sessionID, packetContent, flag);
        return packet;
    }

    // 根据命令类型构造包内容对象
    private PacketContent getPacketContent(ByteBuf byteBuf, int command, int len) {
        PacketContent result = null;
        if (command == PacketContent.COMMAND_LOGIN) {   // 设备登录
            result = LoginCommandReq.ofByte(byteBuf, len);
        }
        if (command == PacketContent.COMMAND_HEART_BEAT) {  // 设备心跳
            System.out.println("心跳检测：" + System.currentTimeMillis());
        }
        if (command == PacketContent.COMMAND_PERSON_INFO) { // 人员特征信息
            result = PersonInfoCommandReq.ofByte(byteBuf, len);
        }
        if (command == PacketContent.COMMAND_ON_DUTY) { // 人员考勤
            result = OnDutyCommandReq.ofByte(byteBuf,len);
        }
        return result;
    }
}
