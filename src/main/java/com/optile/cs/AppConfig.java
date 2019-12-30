package com.optile.cs;

import com.optile.cs.model.EventMessage;
import com.optile.cs.model.StatusMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;

@Configuration
public class AppConfig {
    @Value("${spring.activemq.brokerUrl:tcp://localhost:61616}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory() {{
            setBrokerURL(brokerUrl);
        }};
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate() {{
            setConnectionFactory(connectionFactory());
            setMessageConverter(messageConverter());
        }};
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
