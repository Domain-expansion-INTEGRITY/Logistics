package com.domain_expansion.integrity.slack.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaErrorHandler implements CommonErrorHandler {

    // 컨슈머에서 에러 발생시 처리를 한다.
    @Override
    public boolean handleOne(
        Exception thrownException,
        ConsumerRecord<?, ?> record,
        Consumer<?, ?> consumer,
        MessageListenerContainer container) {

        log.error("Error log in kafka listener message: {}", record.value());
        return true;
    }

}