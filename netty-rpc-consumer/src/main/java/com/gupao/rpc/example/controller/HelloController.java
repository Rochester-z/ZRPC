package com.gupao.rpc.example.controller;

import com.gupao.rpc.example.ISayService;
import com.gupao.rpc.example.IUserService;
import com.gupao.rpc.example.annotation.GpRemoteReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@RestController
public class HelloController {

    @GpRemoteReference
    IUserService userService; //

    @GpRemoteReference
    ISayService service;

    @GetMapping("/say")
    public String say(){
        return userService.saveUser("Mic");
    }

    @GetMapping("/hi")
    public String hello(){
        return service.say();
    }
}
