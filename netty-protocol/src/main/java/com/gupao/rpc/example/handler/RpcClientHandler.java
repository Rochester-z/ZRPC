package com.gupao.rpc.example.handler;

import com.gupao.rpc.example.core.RequestHolder;
import com.gupao.rpc.example.core.RpcFuture;
import com.gupao.rpc.example.core.RpcProtocol;
import com.gupao.rpc.example.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("receive Rpc Server Result");
        long requestId=msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> future= RequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getContent()) ; //返回结果
    }
}
