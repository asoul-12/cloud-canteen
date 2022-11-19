package my.asoul.chat_service.listener;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import my.asoul.chat_service.entity.protocol.Invocation;
import my.asoul.chat_service.entity.protocol.Message;
import my.asoul.chat_service.service.PrivateChatService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 4512
 * @date 2022/10/12 16:53
 */
@RocketMQMessageListener(topic = "test", consumerGroup = "test-consumer")
@Slf4j
@Component
public class MessageSaveListener implements RocketMQListener<String> {

    @Autowired
    private PrivateChatService privateChatService;

    @Override
    public void onMessage(String json) {
        Invocation invocation = JSONUtil.toBean(json, Invocation.class);
        Message message = invocation.getMessage().setId(IdUtil.getSnowflakeNextId());
        invocation.setMessage(message);
        privateChatService.saveChatRecord(invocation);
        log.error(json);
    }
}
