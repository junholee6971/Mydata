package com.idispt.mydataPrj;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idispt.mydataPrj.client.NettyClient;

public class AppClient {
    private static final Logger logger = LoggerFactory.getLogger(AppClient.class);
    
    public static void main( String[] args ){
        logger.info("======= Netty Client Test =======");
        String host = "127.0.0.1";
        int port = 8888;
        
       
//        byte[] msgArr = "IDIDPT210823164634000199999123434343434334434                                                                 \r".getBytes();
        //byte[] msgArr = "ILIDPT2108231646340001C8140257677                                                                             100    \r".getBytes();
        byte[] msgArr = "ILIDPT2108231646340001C8140257677                                                                             1005672\r".getBytes();
        //byte[] msgArr = "IBIDPT210823164634000199999123434343434334434                                                                 SJ085663180    202108\r".getBytes();
        
        
        new NettyClient(host, port, msgArr).run();

    }
}