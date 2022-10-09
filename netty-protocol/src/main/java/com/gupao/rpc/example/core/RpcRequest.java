package com.gupao.rpc.example.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class RpcRequest implements Serializable {

    private String className; //类名
    private String methodName; //请求目标方法
    private Object[] params;  //请求参数
    private Class<?>[] parameterTypes; //参数类型

}
