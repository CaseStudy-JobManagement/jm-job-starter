package com.optile.cs;

import com.optile.cs.model.EventMessage;
import com.optile.cs.model.StatusMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Queue;
import java.util.HashMap;

@Configuration
public class AppConfig {
    @Bean(name = "status")
    public Queue statusQueue() {
        return new ActiveMQQueue("job-status");
    }

    @Bean(name = "event")
    public Queue eventQueue() {
        return new ActiveMQQueue("job-event");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter() {{
            setTargetType(MessageType.TEXT);
            setTypeIdMappings(new HashMap() {{
                put(StatusMessage.class.getSimpleName(), StatusMessage.class);
                put(EventMessage.class.getSimpleName(), EventMessage.class);
            }});
            setTypeIdPropertyName("_type");
        }};
    }
}
