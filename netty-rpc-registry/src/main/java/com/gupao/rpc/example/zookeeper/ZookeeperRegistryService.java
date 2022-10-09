package com.gupao.rpc.example.zookeeper;

import com.gupao.rpc.example.IRegistryService;
import com.gupao.rpc.example.ServiceInfo;
import com.gupao.rpc.example.loadbalance.ILoadBalance;
import com.gupao.rpc.example.loadbalance.RandomLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.List;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class ZookeeperRegistryService implements IRegistryService {

    private static final String REGISTRY_PATH="/registry";
    //curator中提供的服务注册与发现的封装
    private final ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private ILoadBalance<ServiceInstance<ServiceInfo>> loadBalance;

    public ZookeeperRegistryService(String registryAddress) throws Exception {
        CuratorFramework client= CuratorFrameworkFactory
                .newClient(registryAddress,new ExponentialBackoffRetry(1000,3));
        client.start();
        JsonInstanceSerializer<ServiceInfo> serializer=new JsonInstanceSerializer<>(ServiceInfo.class);
        this.serviceDiscovery= ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .client(client)
                .serializer(serializer)
                .basePath(REGISTRY_PATH)
                .build();
        this.serviceDiscovery.start();
        this.loadBalance=new RandomLoadBalance();
    }

    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {
        log.info("begin registry servicinfo to Zookeeper server");
        ServiceInstance<ServiceInfo> serviceInstance=ServiceInstance.<ServiceInfo>builder()
                .name(serviceInfo.getServiceName())
                .address(serviceInfo.getServiceAddress())
                .port(serviceInfo.getServicePort())
                .payload(serviceInfo)
                .build();
        this.serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        log.info("begin discovery servicinfo from Zookeeper server");
        Collection<ServiceInstance<ServiceInfo>> serviceInstances=
                this.serviceDiscovery.queryForInstances(serviceName);
        //动态路由
        ServiceInstance<ServiceInfo> serviceInstance=this.loadBalance.select((List<ServiceInstance<ServiceInfo>>)serviceInstances);
        if(serviceInstance!=null){
            return serviceInstance.getPayload();
        }
        return null;
    }
}
