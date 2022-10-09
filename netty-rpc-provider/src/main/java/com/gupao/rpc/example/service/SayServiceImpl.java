package com.gupao.rpc.example.service;

import com.gupao.rpc.example.ISayService;
import com.gupao.rpc.example.annotation.GpRemoteService;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@GpRemoteService
public class SayServiceImpl implements ISayService {


    @Override
    public String say() {
        return "Hello World";
    }
}
