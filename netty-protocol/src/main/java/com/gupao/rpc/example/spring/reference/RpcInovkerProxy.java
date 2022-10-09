package com.gupao.rpc.example.spring.reference;

import com.gupao.rpc.example.IRegistryService;
import com.gupao.rpc.example.constants.ReqType;
import com.gupao.rpc.example.constants.RpcConstant;
import com.gupao.rpc.example.constants.SerialType;
import com.gupao.rpc.example.core.*;
import com.gupao.rpc.example.protocol.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RpcInovkerProxy implements InvocationHandler {
   /* private String host;
    private int port;
*/
    IRegistryService registryService;
    public RpcInovkerProxy(IRegistryService registryService) {
       /* this.host = host;
        this.port = port;*/
        this.registryService=registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");
        RpcProtocol<RpcRequest> reqProtocol=new RpcProtocol<>();
        long requestId=RequestHolder.REQUEST_ID.incrementAndGet();
        Header header=new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.code(), 
                ReqType.REQUEST.code(),requestId,0);
        reqProtocol.setHeader(header);
        RpcRequest request=new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        reqProtocol.setContent(request);

        NettyClient nettyClient=new NettyClient();
        RpcFuture<RpcResponse> future=new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId,future);
        nettyClient.sendRequest(reqProtocol,registryService);
        return future.getPromise().get().getData();
    }
}
