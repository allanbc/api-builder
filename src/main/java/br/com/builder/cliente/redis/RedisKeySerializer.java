package br.com.builder.cliente.redis;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeySerializer extends StringRedisSerializer {

  @Getter
  private final String prefixKey;

  public RedisKeySerializer(@Value("${redis.root-key}") String prefixKey) {
    this.prefixKey = prefixKey + ":";
  }

  @Override
  public byte[] serialize(String value) {
    return StringUtils.isBlank(value) ? null : super.serialize(this.prefixKey + value);
  }

  @Override
  public String deserialize(byte[] bytes) {
    final String value = super.deserialize(bytes);
    return StringUtils.isBlank(value) ? null : value.replaceAll(this.prefixKey, "");
  }

}
