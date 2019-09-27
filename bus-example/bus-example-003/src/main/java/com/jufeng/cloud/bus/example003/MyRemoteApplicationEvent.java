package com.jufeng.cloud.bus.example003;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @program: spring-cloud-example
 * @description: 自定义事件
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-27 14:56
 **/
public class MyRemoteApplicationEvent extends RemoteApplicationEvent {
    public MyRemoteApplicationEvent() {
    }

    public MyRemoteApplicationEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }


}
