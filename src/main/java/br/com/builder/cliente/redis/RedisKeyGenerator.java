package br.com.builder.cliente.redis;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RedisKeyGenerator implements KeyGenerator {

  private final Gson gson;

  @Override
  public Object generate(Object target, Method method, Object... params) {
    return Hashing.murmur3_128().hashString(gson.toJson(params), StandardCharsets.UTF_8).toString();
  }
}
