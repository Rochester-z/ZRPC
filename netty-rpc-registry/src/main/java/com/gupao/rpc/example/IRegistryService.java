package com.gupao.rpc.example;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public interface IRegistryService {

    /**
     * 服务注册功能
     * @param serviceInfo
     */
    void register(ServiceInfo serviceInfo) throws Exception;

    /**
     * 服务发现
     * @param serviceName
     * @return
     */
    ServiceInfo discovery(String serviceName) throws Exception;
}
