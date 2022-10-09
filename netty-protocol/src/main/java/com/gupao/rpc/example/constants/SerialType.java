package com.gupao.rpc.example.constants;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/

public enum SerialType {

    JSON_SERIAL((byte)1),
    JAVA_SERIAL((byte)2);

    private byte code;

    SerialType(byte code){
        this.code=code;
    }

    public byte code(){
        return this.code;
    }
}
