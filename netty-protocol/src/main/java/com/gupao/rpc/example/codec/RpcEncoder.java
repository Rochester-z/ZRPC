package com.gupao.rpc.example.codec;

import com.gupao.rpc.example.core.Header;
import com.gupao.rpc.example.core.RpcProtocol;
import com.gupao.rpc.example.serial.ISerializer;
import com.gupao.rpc.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("============begin RpcEncoder=========");
        Header header=msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getReqType());
        out.writeLong(header.getRequestId());
        ISerializer serializer=SerializerManager.getSerializer(header.getSerialType());
        byte[] data=serializer.serialize(msg.getContent());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
