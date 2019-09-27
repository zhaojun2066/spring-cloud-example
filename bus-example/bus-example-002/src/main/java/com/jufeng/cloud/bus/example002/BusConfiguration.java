package com.jufeng.cloud.bus.example002;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-27 14:39
 **/

@Slf4j
@Configuration
public class BusConfiguration {

    @EventListener
    public void onRefreshRemoteApplicationEvent(RefreshRemoteApplicationEvent event){
        log.info("===> Source : {}, originService : {}, destinationService : {} ",
                event.getSource(), event.getOriginService(), event.getDestinationService());
    }
}
