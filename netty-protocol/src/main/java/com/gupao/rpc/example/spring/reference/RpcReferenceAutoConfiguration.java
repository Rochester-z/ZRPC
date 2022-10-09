package com.gupao.rpc.example.spring.reference;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Configuration
public class RpcReferenceAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

    @Bean
    public SpringRpcReferencePostProcessor postProcessor(){
        RpcClientProperties rc=new RpcClientProperties();
        rc.setRegistryAddress(this.environment.getProperty("gp.client.registryAddress"));
      /*  int port=Integer.parseInt();
        rc.setRegistryAddress(port);*/
        rc.setRegistryType(Byte.parseByte(this.environment.getProperty("gp.client.registryType")));
        return new SpringRpcReferencePostProcessor(rc);
    }
}
