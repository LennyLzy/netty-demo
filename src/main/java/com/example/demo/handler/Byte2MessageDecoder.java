package com.example.demo.handler;

import com.example.demo.packet.MyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static org.apache.coyote.http11.Constants.A;

public class Byte2MessageDecoder extends ByteToMessageDecoder {

    public final int MIN_LEN = 68;

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
            int len = byteBuf.readInt();
            if (byteBuf.readableBytes() < len + MIN_LEN) {
                byteBuf.resetReaderIndex();
                return;
            }
            // 读取数据流
            byte[] bytes = new byte[len + MIN_LEN];
            byteBuf.readBytes(bytes);
            MyPacket packet = readPacketFromBytes(bytes, len);
        }
    }


    private MyPacket readPacketFromBytes(byte[] bytes, int len) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(len + MIN_LEN);
        byteBuf.writeBytes(bytes);
        byte header = byteBuf.readByte();
        int length = byteBuf.readIntLE();
        int partInedx = byteBuf.readIntLE();
        int partCount = byteBuf.readIntLE();
        byte version = byteBuf.readByte();
        short command = byteBuf.readShortLE();
        
        MyPacket packet = new MyPacket();
        return packet;
    }
}
