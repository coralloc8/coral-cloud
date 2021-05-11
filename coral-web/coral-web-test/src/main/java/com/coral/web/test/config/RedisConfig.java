package com.coral.web.test.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author huss
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private ObjectMapper objectMapper;

    /** @formatter:off **/
	/**
    * 	methodName		root对象		当前被调用的方法名																        root.methodName
		method			root对象		当前被调用的方法																			root.method.name
		target			root对象		当前被调用的目标对象																		root.target
		targetClass		root对象		当前被调用的目标对象类																		root.targetClass
		args			root对象		当前被调用的方法的参数列表																	root.args[0]
		caches			root对象		当前方法调用使用的缓存列表（如@Cacheable(value={“cache1”, “cache2”})），则有两个cache	    root.caches[0].name
		Argument Name	执行上下文		当前被调用的方法的参数，如findById(Long id)，我们可以通过#id拿到参数user.id
		result			执行上下文		方法执行后的返回值（仅当方法执行之后的判断有效，如‘unless'，'com.coral.base.common.cache evict'的beforeInvocation=false）result
	 *	
	 *	@CacheEvict(value = "user", key = "#user.id", 
	 *		condition = "#root.target.canCache() and #root.caches[0].get(#user.id).get().username ne #user.username", beforeInvocation = true)
		public void conditionUpdate(User user)
	 *
	 */
	/** @formatter:on **/

    private Duration timeToLive = Duration.ofSeconds(60 * 60);

    /**
     * 在没有指定缓存Key的情况下，key生成策略
     * 
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName()).append(".");
                sb.append(method.getName()).append(".");
                for (Object obj : params) {
                    sb.append(obj.toString()).append(",");
                }
                return sb.substring(0, sb.length() - 1);
            }
        };
    }

    /**
     * 缓存管理器
     * 
     * @param connectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 关键点，spring cache的注解使用的序列化都从这来，没有这个配置的话使用的jdk自己的序列化，实际上不影响使用，只是打印出来不适合人眼识别
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
            .disableCachingNullValues().entryTtl(timeToLive);

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(connectionFactory).cacheDefaults(config).transactionAware();
        return builder.build();
    }

    /**
     * myRedisTemplate配置
     * 
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> myRedisTemplate(RedisConnectionFactory connectionFactory) {
        // 配置myRedisTemplate
        RedisTemplate<String, Object> myRedisTemplate = new RedisTemplate<>();
        myRedisTemplate.setConnectionFactory(connectionFactory);
        // key序列化
        myRedisTemplate.setKeySerializer(keySerializer());
        // value序列化
        myRedisTemplate.setValueSerializer(valueSerializer());
        // Hash key序列化
        myRedisTemplate.setHashKeySerializer(keySerializer());
        // Hash value序列化
        myRedisTemplate.setHashValueSerializer(valueSerializer());
        myRedisTemplate.afterPropertiesSet();
        return myRedisTemplate;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        // 设置序列化Jackson2JsonRedisSerializer
        // GenericJackson2JsonRedisSerializer 序列化时会带上当前类的class
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

}
