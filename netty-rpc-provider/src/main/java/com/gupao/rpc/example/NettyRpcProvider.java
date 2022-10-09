package com.gupao.rpc.example;

import com.gupao.rpc.example.protocol.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@ComponentScan(basePackages = {"com.gupao.rpc.example.spring.service","com.gupao.rpc.example.service"})
@SpringBootApplication
public class NettyRpcProvider {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProvider.class,args);
    }
}
