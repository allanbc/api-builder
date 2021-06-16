package br.com.builder.cliente.redis;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class RedisService {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	public RedisService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
		this.redisTemplate = redisTemplate;
		this.objectMapper = objectMapper;
	}

	public <T> Optional<T> getCache(String key, Class<T> clazz) {

		T obj = objectMapper.convertValue(redisTemplate.opsForValue().get(key), clazz);
		if (!ObjectUtils.isEmpty(obj)) {
			log.info("Getting object with key {} from cache", key);
			return Optional.of(obj);
		}
		return Optional.empty();
	}

	public <T> void setCache(String key, T data, long ttl, TimeUnit timeUnit) {
		log.info("Saving new cache {} with ttl: {} {}", key, ttl, timeUnit.name());
		redisTemplate.opsForValue().set(key, data, ttl, timeUnit);
	}

	public <T> void setCache(String key, T data) {
		log.info("Saving new cache {} with ttl default", key);
		redisTemplate.opsForValue().set(key, data);
	}

	public void deleteCache(String key) {
		log.info("Deleting caching with key {}", key);
		redisTemplate.delete(key);
	}

	public void deleteCache(List<String> key) {
		log.info("Deleting caching with key list {}", key);
		redisTemplate.delete(key);
	}

	public void deleteCache(Set<String> key) {
		log.info("Deleting caching with key list {}", key);
		redisTemplate.delete(key);
	}

	public void renameCache(String oldKey, String newKey) {
		log.info("Rename redis key - old key: ''{}'', new key: ''{}'' ", oldKey, newKey);
		redisTemplate.rename(oldKey, newKey);
	}

	public <T> void setCacheIfNotExist(String key, T data, long ttl, TimeUnit timeUnit) {
		log.info("Saving new cache if it doesn't exist with key {}, ttl {} {}", key, ttl, timeUnit.name());
		redisTemplate.opsForValue().setIfAbsent(key, data, ttl, timeUnit);
	}

	public <T> void setCacheIfNotExist(String key, T data) {
		log.info("Saving new cache if it doesn't exist with key {}, ttl default", key);
		redisTemplate.opsForValue().setIfAbsent(key, data);
	}

	public Optional<Set<String>> getKeys(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		return ObjectUtils.isNotEmpty(keys) ? Optional.of(keys) : Optional.empty();
	}

}
