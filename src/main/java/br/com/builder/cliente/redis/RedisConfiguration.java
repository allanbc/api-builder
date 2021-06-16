package br.com.builder.cliente.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisConfiguration {

  private final Integer defaultTtl;
  private final RedisKeySerializer redisKeySerializer;

  public RedisConfiguration(@Value("${redis.ttl.default}") Integer defaultTtl,
                            RedisKeySerializer redisKeySerializer) {
    this.defaultTtl = defaultTtl;
    this.redisKeySerializer = redisKeySerializer;
  }

  @Bean
  public RedisCacheConfiguration redisCacheDefault() {
    return RedisCacheConfiguration
      .defaultCacheConfig()
      .entryTtl(Duration.ofMinutes(this.defaultTtl))
      .serializeValuesWith(
        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
      .prefixCacheNameWith(redisKeySerializer.getPrefixKey())
      .disableCachingNullValues();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                     ObjectMapper objectMapper) {

    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
      Object.class);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.setKeySerializer(redisKeySerializer);
    template.afterPropertiesSet();
    return template;
  }

}

