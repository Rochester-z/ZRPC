package com.gupao.rpc.example.serial;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class SerializerManager {

    private final static ConcurrentHashMap<Byte,ISerializer> serializer=new ConcurrentHashMap<>();

    static{
        ISerializer json=new JsonSerializer();
        ISerializer java=new JavaSerializer();
        serializer.put(json.getType(),json);
        serializer.put(java.getType(),java);
    }
    public static ISerializer getSerializer(byte key){
        ISerializer iSerializer=serializer.get(key);
        if(serializer==null){
            return new JavaSerializer();
        }
        return iSerializer;
    }
}
