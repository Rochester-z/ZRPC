package com.gupao.rpc.example.spring.reference;

import lombok.Data;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class RpcClientProperties {
    private String serviceAddress="192.168.1.102";
    private int servicePort=20880;

    private byte registryType;
    private String registryAddress;
}
