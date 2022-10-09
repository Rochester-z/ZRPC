package com.gupao.rpc.example.codec;

import com.gupao.rpc.example.constants.ReqType;
import com.gupao.rpc.example.constants.RpcConstant;
import com.gupao.rpc.example.core.Header;
import com.gupao.rpc.example.core.RpcProtocol;
import com.gupao.rpc.example.core.RpcRequest;
import com.gupao.rpc.example.core.RpcResponse;
import com.gupao.rpc.example.serial.ISerializer;
import com.gupao.rpc.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("========begin RpcDecoder==========");

        if(in.readableBytes()< RpcConstant.HEAD_TOTOAL_LEN){
            return;
        }
        in.markReaderIndex(); //标记读取开始索引
        short maci=in.readShort(); //读取2个字节的magic
        if(maci!=RpcConstant.MAGIC){
            throw new IllegalArgumentException("Illegal request parameter 'magic',"+maci);
        }

        byte serialType=in.readByte(); //读取一个字节的序列化类型
        byte reqType=in.readByte(); //读取一个字节的消息类型
        long requestId=in.readLong(); //读取请求id
        int dataLength=in.readInt(); //读取数据报文长度

        if(in.readableBytes()<dataLength){
            in.resetReaderIndex();
            return ;
        }
        //读取消息体的内容
        byte[] content=new byte[dataLength];
        in.readBytes(content);

        Header header=new Header(maci,serialType,reqType,requestId,dataLength);
        ISerializer serializer=SerializerManager.getSerializer(serialType);
        ReqType rt=ReqType.findByCode(reqType);
        switch (rt){
            case REQUEST:
                RpcRequest request=serializer.deserialize(content,RpcRequest.class);
                RpcProtocol<RpcRequest> reqProtocol=new RpcProtocol<>();
                reqProtocol.setHeader(header);
                reqProtocol.setContent(request);
                out.add(reqProtocol);
                break;
            case RESPONSE:
                RpcResponse response=serializer.deserialize(content, RpcResponse.class);
                RpcProtocol<RpcResponse> resProtocol=new RpcProtocol<>();
                resProtocol.setHeader(header);
                resProtocol.setContent(response);
                out.add(resProtocol);
                break;
            case HEARTBEAT:
                //TODO
                break;
            default:
                break;
        }

    }
}
