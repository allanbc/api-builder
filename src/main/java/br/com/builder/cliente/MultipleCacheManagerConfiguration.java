package br.com.builder.cliente;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.github.benmanes.caffeine.cache.Caffeine;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;

@Configuration
public class MultipleCacheManagerConfiguration extends CachingConfigurerSupport {

  private final Integer caffeineDefaultTtl;
  private final Integer defaultMaxSize;
  private final MeterRegistry meterRegistry;

  public MultipleCacheManagerConfiguration(
    @Value("${caffeine.ttl.default}") Integer caffeineDefaultTtl,
    @Value("${caffeine.maximumSize.default}") Integer defaultMaxSize,
    MeterRegistry meterRegistry) {
    this.caffeineDefaultTtl = caffeineDefaultTtl;
    this.defaultMaxSize = defaultMaxSize;
    this.meterRegistry = meterRegistry;
  }

  @Bean
  @Override
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
      .maximumSize(this.defaultMaxSize)
      .expireAfterWrite(Duration.ofMinutes(this.caffeineDefaultTtl));
    cacheManager.setCaffeine(caffeine);
    CaffeineCacheMetrics.monitor(meterRegistry, caffeine.build(), "api-token-service");
    return cacheManager;
  }


  @Bean
  public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                        RedisCacheConfiguration redisCacheDefault) {
    final Map<String, RedisCacheConfiguration> cacheNamesConfigurationMap = new HashMap<>();

    return RedisCacheManager.builder(redisConnectionFactory)
      .cacheDefaults(redisCacheDefault)
      .withInitialCacheConfigurations(cacheNamesConfigurationMap)
      .build();
  }

}

