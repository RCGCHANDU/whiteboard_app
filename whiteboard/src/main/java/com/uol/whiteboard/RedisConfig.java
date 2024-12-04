package com.uol.whiteboard;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.config.RedisListenerEndpointRegistrar;
import org.springframework.data.redis.listener.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.MessageListener;
import com.example.whiteboard.service.RedisSubscriber;

@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisTemplate<String, Object> redisTemplate, RedisSubscriber redisSubscriber) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        container.addMessageListener(redisSubscriber, new PatternTopic("whiteboard_channel"));
        return container;
    }
}
