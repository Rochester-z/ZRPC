package com.gupao.rpc.example.spring.reference;

import com.gupao.rpc.example.IRegistryService;
import com.gupao.rpc.example.RegistryFactory;
import com.gupao.rpc.example.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 * 工厂Bean
 **/
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;
 /*   private String serviceAddress;
    private int servicePort;*/
    private Class<?> interfaceClass;

    private String registryAddress;
    private byte registryType;


    @Override
    public Object getObject() throws Exception {
        return object;
    }

    public void init(){
        IRegistryService registryService= RegistryFactory.createRegistryService(this.registryAddress, RegistryType.findByCode(this.registryType));
        this.object=Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInovkerProxy(registryService));
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

   /* public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }*/

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRegistryType(byte registryType) {
        this.registryType = registryType;
    }
}
