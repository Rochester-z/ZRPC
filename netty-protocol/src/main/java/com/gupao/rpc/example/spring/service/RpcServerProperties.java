package com.gupao.rpc.example.spring.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
@ConfigurationProperties(prefix = "gp.rpc")
public class RpcServerProperties {

    private int servicePort;

    private String registryAddress;  //注册中心的地址

    private byte registryType; //注册中心的类型
}
