package com.gupao.rpc.example.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@AllArgsConstructor
@Data
public class Header implements Serializable {

    private short magic; //魔数 2字节
    private byte serialType; //序列化类型  1个字节
    private byte reqType; //消息类型  1个字节
    private long requestId; //请求id  8个字节
    private int length ;//消息体长度，4个字节

}
