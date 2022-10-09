package com.gupao.rpc.example.spring.reference;

import com.gupao.rpc.example.annotation.GpRemoteReference;
import com.gupao.rpc.example.spring.service.SpringRpcProviderBean;
import io.netty.util.internal.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class SpringRpcReferencePostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {
    private ApplicationContext context;
    private ClassLoader classLoader;
    private RpcClientProperties rpcClientProperties;

    public SpringRpcReferencePostProcessor(RpcClientProperties rpcClientProperties) {
        this.rpcClientProperties = rpcClientProperties;
    }


    //保存发布的引用bean的信息
    private final Map<String, BeanDefinition> rpcRefBeanDefinition=new ConcurrentHashMap<>();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader=classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    //spring容器加载了bean的定义文件之后， 在bean实例化之前执行
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for(String beanDefinitionname:beanFactory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition=beanFactory.getBeanDefinition(beanDefinitionname);
            String beanClassName=beanDefinition.getBeanClassName();
            if(beanClassName!=null){
                Class<?> clazz= ClassUtils.resolveClassName(beanClassName,this.classLoader);
                ReflectionUtils.doWithFields(clazz,this::parseRpcReference);
            }
        }
        BeanDefinitionRegistry registry=(BeanDefinitionRegistry)beanFactory;
        this.rpcRefBeanDefinition.forEach((beanName,beanDefinition)->{
            if(context.containsBean(beanName)){
                log.warn("SpringContext already register bean {}",beanName);
                return;
            }
            registry.registerBeanDefinition(beanName,beanDefinition);
            log.info("registered RpcReferenceBean {} success",beanName);
        });
    }

    private void parseRpcReference(Field field){
        GpRemoteReference gpRemoteReference=AnnotationUtils.getAnnotation(field, GpRemoteReference.class);
        if(gpRemoteReference!=null){
            BeanDefinitionBuilder builder=BeanDefinitionBuilder.
                    genericBeanDefinition(SpringRpcReferenceBean.class);
            builder.setInitMethodName("init");
            builder.addPropertyValue("interfaceClass",field.getType());
            /*builder.addPropertyValue("serviceAddress",rpcClientProperties.getServiceAddress());
            builder.addPropertyValue("servicePort",rpcClientProperties.getServicePort());*/
            builder.addPropertyValue("registryAddress",rpcClientProperties.getRegistryAddress());
            builder.addPropertyValue("registryType",rpcClientProperties.getRegistryType());

            BeanDefinition beanDefinition=builder.getBeanDefinition();
            rpcRefBeanDefinition.put(field.getName(),beanDefinition);
        }
    }
}
