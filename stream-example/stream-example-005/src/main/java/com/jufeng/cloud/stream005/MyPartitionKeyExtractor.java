package com.jufeng.cloud.stream005;

import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * @program: spring-cloud-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-09-26 09:19
 **/
public class MyPartitionKeyExtractor  implements PartitionKeyExtractorStrategy, PartitionSelectorStrategy {

    // 重新获取key
    @Override
    public Object extractKey(Message<?> message) {
        return message.getHeaders(); // headers 作为key
    }

    // 重新进行分片 获取route index
    @Override
    public int selectPartition(Object key, int partitionCount) {
        return ((Map<String, Integer>) key).get("router"); // header 中的router key 对应的vaue 为index
    }
}
